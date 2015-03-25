package com.hida.report

import com.hida.report.dynamic.DynamicTableDesigner
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder
import spock.lang.Specification

/**
 * Created by hida on 26/3/2015.
 */
class DynamicTableReportSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        when:
        DynamicReportData data = new DynamicReportData()
        JasperReportBuilder report = DynamicTableDesigner.build("Test", data.dynamicReport, data.createDataSource());
        then:
        report != null
        report.show()
        report.toPdf(new FileOutputStream(new File("/dynamic-table-report.pdf")))
    }
}
