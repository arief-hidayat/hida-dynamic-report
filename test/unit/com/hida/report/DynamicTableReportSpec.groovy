package com.hida.report

import com.hida.report.dynamic.DynamicTableDesigner
import com.hida.report.dynamic.DynamicTableReport
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

//    void "test something"() {
//        when:
//        DynamicReportData data = new DynamicReportData()
//        JasperReportBuilder report = DynamicTableDesigner.build("Test", data.dynamicReport, data.createDataSource());
//        then:
//        report != null
//        report.show()
//        report.toPdf(new FileOutputStream(new File("/dynamic-table-report.pdf")))
//    }
    void "test something2 "() {
        when:
        DrTableDataSource tableDataSource = JRDataSourceUtil.SIMPLE_INSTANCE.populateTableDataSourceFrom(StateSalesData.class,
        ["state", "item", "orderdate", "quantity", "unitprice"],
        [String.class, String.class, Date.class, Integer.class, BigDecimal.class], [
                new StateSalesData("New York", "DVD", toDate(2010, 1, 1), 5, new BigDecimal(30)),
                new StateSalesData("New York", "DVD", toDate(2010, 1, 3), 2, new BigDecimal(45)),
                new StateSalesData("New York", "DVD", toDate(2010, 1, 5), 4, new BigDecimal(36)),
                new StateSalesData("New York", "DVD", toDate(2010, 1, 18), 5, new BigDecimal(41)),
                new StateSalesData("New York", "Book", toDate(2010, 1, 8), 2, new BigDecimal(11)),
                new StateSalesData("New York", "Book", toDate(2010, 1, 15), 8, new BigDecimal(9)),
                new StateSalesData("New York", "Book", toDate(2010, 1, 21), 6, new BigDecimal(14)),
                new StateSalesData("New York", "Phone", toDate(2010, 1, 16), 1, new BigDecimal(200)),
                new StateSalesData("New York", "Phone", toDate(2010, 1, 22), 2, new BigDecimal(520)),

                new StateSalesData("Washington", "DVD", toDate(2010, 1, 8), 4, new BigDecimal(40)),
                new StateSalesData("Washington", "DVD", toDate(2010, 1, 16), 6, new BigDecimal(35)),
                new StateSalesData("Washington", "DVD", toDate(2010, 1, 23), 3, new BigDecimal(46)),
                new StateSalesData("Washington", "DVD", toDate(2010, 1, 28), 2, new BigDecimal(42)),
                new StateSalesData("Washington", "Book", toDate(2010, 1, 11), 3, new BigDecimal(12)),
                new StateSalesData("Washington", "Book", toDate(2010, 1, 17), 9, new BigDecimal(8)),
                new StateSalesData("Washington", "Book", toDate(2010, 1, 26), 4, new BigDecimal(14)),
                new StateSalesData("Washington", "Book", toDate(2010, 1, 29), 5, new BigDecimal(10)),
                new StateSalesData("Washington", "Phone", toDate(2010, 1, 12), 2, new BigDecimal(210)),
                new StateSalesData("Washington", "Phone", toDate(2010, 1, 29), 1, new BigDecimal(380)),

                new StateSalesData("Florida", "DVD", toDate(2010, 1, 1), 3, new BigDecimal(49)),
                new StateSalesData("Florida", "DVD", toDate(2010, 1, 8), 4, new BigDecimal(32)),
                new StateSalesData("Florida", "DVD", toDate(2010, 1, 17), 2, new BigDecimal(47)),
                new StateSalesData("Florida", "Book", toDate(2010, 1, 5), 4, new BigDecimal(11)),
                new StateSalesData("Florida", "Book", toDate(2010, 1, 16), 8, new BigDecimal(6)),
                new StateSalesData("Florida", "Book", toDate(2010, 1, 23), 6, new BigDecimal(16)),
                new StateSalesData("Florida", "Book", toDate(2010, 1, 28), 3, new BigDecimal(18)),
                new StateSalesData("Florida", "Phone", toDate(2010, 1, 12), 2, new BigDecimal(190)),
                new StateSalesData("Florida", "Phone", toDate(2010, 1, 18), 1, new BigDecimal(250)),
                new StateSalesData("Florida", "Phone", toDate(2010, 1, 26), 1, new BigDecimal(201))
        ])
        JasperReportBuilder report = DynamicTableDesigner.build("Test", DynamicReportData.getDynamicReport(tableDataSource.dynamicColumns), tableDataSource.dataSource);
        then:
        report != null
        report.show()
        report.toPdf(new FileOutputStream(new File("/dynamic-table-report2.pdf")))
    }

    private Date toDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }
}
