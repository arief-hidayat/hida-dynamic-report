package com.hida.report.dynamic

import net.sf.dynamicreports.report.constant.HorizontalAlignment
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty

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
            DynamicColumn column = new DynamicColumn(prop.naturalName, prop.name, getTypeOfGrailsProp(prop))
            list.add column
        }
        return list
    }

    private static getTypeOfGrailsProp(GrailsDomainClassProperty prop) {
        if(prop.type.equals(String.class)) return "string"
        if(prop.type.equals(BigDecimal.class)) return "bigDecimal"
        if(prop.type.equals(Integer.class)) return "integer"
        if(prop.type in [Date.class]) return "data"
        throw new RuntimeException("Unsupported type")
    }
}
