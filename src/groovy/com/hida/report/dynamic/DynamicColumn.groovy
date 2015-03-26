package com.hida.report.dynamic

import net.sf.dynamicreports.report.constant.HorizontalAlignment
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime

/**
 * Created by hida on 26/3/2015.
 */
class DynamicColumn {
    String name;
    String title;
    String type;
    String pattern;
    HorizontalAlignment horizontalAlignment;
    public DynamicColumn(String title, String name, String type) {
        this.name = name;
        this.type = type;
        this.title = title;
    }

    public static List<DynamicColumn> fromDomain(Class domainClass) {
        def d = new DefaultGrailsDomainClass(domainClass)
        List<DynamicColumn> list = []
        for(GrailsDomainClassProperty prop : d.persistentProperties) {
            DynamicColumn column = new DynamicColumn(prop.naturalName, prop.name, getDrColumnType(prop.type))
            list.add column
        }
        return list
    }

    private static getDrColumnType(Class type) {
        if(type in [Date.class, LocalDate.class, LocalDateTime.class, LocalTime.class]) return Date.class.name
        return type.name
    }
}
