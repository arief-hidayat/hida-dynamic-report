package com.hida.report

import com.hida.report.dynamic.DynamicColumn
import grails.util.GrailsNameUtils
import net.sf.dynamicreports.report.datasource.DRDataSource
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import org.springframework.context.MessageSource

/**
 * Created by hida on 26/3/2015.
 */
class JRDataSourceUtil {

    MessageSource messageSource
    Locale locale

    public static final JRDataSourceUtil SIMPLE_INSTANCE = new JRDataSourceUtil()

    DrTableDataSource populateTableDataSourceFromDomain(Class domainClass, List columns, List items) {
        return new DrTableDataSource(dataSource: createDataSource(items, columns), dynamicColumns: getDynamicColumns(domainClass, columns))
    }
    DrTableDataSource populateTableDataSourceFrom(Class nonDomainClass, List columns, List columnTypes, List items) {
        if(columns.size() != columns.size()) throw new RuntimeException("Size not match")
        return new DrTableDataSource(dataSource: createDataSource(items, columns), dynamicColumns: getDynamicColumns(nonDomainClass, columns, columnTypes))
    }

    DRDataSource createDataSource(List items, List columns) {
        int colSize = columns.size()
        DRDataSource dataSource = new DRDataSource(columns.toArray(new String[colSize]))
        for(def item : items) {
            List values = new ArrayList()
            for(String col : columns) {
                values.add(getColumnValue(item[col]))
            }
            dataSource.add(values.toArray(new Object[colSize]))
        }
        return dataSource
    }

    public List<DynamicColumn> getDynamicColumns(Class domainClass, List columns) {
        def d = new DefaultGrailsDomainClass(domainClass)
        List<DynamicColumn> list = []
        for(String col : columns) {
            GrailsDomainClassProperty prop = d.getPersistentProperty(col)
            String title = messageSource?.getMessage("column.${domainClass.simpleName}.${col}.label", null, locale) ?:
                    (messageSource?.getMessage("default.${col}.label", null, prop.naturalName, locale))
            DynamicColumn column = new DynamicColumn(title, col, getDrColumnType(prop.type))
            list.add column
        }
        return list
    }
    public List<DynamicColumn> getDynamicColumns(Class domainClass, List columns, List columnTypes) {
        List<DynamicColumn> list = []
        for(int i =0 ;i <columns.size(); i++) {
            String col = columns.get(i)
            String title = messageSource?.getMessage("column.${domainClass.simpleName}.${col}.label", null, locale) ?:
                    (messageSource?.getMessage("default.${col}.label", null, GrailsNameUtils.getNaturalName(col), locale))
            DynamicColumn column = new DynamicColumn(title, col, getDrColumnType(columnTypes.get(i)))
            list.add column
        }
        return list
    }

    private getDrColumnType(Class type) {
        if(type in [Date.class, LocalDate.class, LocalDateTime.class, LocalTime.class]) return Date.class.name
        return type.name
    }

    def getColumnValue(def value) {
        if(!value) return null
        if(value instanceof LocalDate) return value.toDate()
        if(value instanceof LocalDateTime) return value.toDate()
        if(value instanceof LocalTime) return (LocalDate.now().toLocalDateTime(value)).toDate()
        return value
    }
}


class DrTableDataSource {
    DRDataSource dataSource
    List<DynamicColumn> dynamicColumns
}