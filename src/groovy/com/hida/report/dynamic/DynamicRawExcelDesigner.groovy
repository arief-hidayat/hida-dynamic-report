package com.hida.report.dynamic

import com.hida.report.Templates
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder
import net.sf.dynamicreports.jasper.builder.export.JasperXlsExporterBuilder
import net.sf.dynamicreports.jasper.constant.JasperProperty

import static net.sf.dynamicreports.report.builder.DynamicReports.*

/**
 * Created by hida on 30/3/2015.
 */
class DynamicRawExcelDesigner {

    static void exportToExcel(DynamicTableReport dynamicReport, def dataSource, def xlsExporterOut) {
        buildSimpleDynamicExcelReport(dynamicReport, dataSource).toXls(getDefaultXlsExporter(xlsExporterOut))
    }

    static JasperReportBuilder buildSimpleDynamicExcelReport(DynamicTableReport dynamicReport, def dataSource){
        JasperReportBuilder report = report()
                .setColumnTitleStyle(Templates.columnTitleStyle)
                .addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "2")
                .ignorePageWidth()
                .ignorePagination()
        DynamicTableDesigner.buildColumns(dynamicReport, report);
        report.setDataSource(dataSource)
        report
    }

    static JasperXlsExporterBuilder getDefaultXlsExporter(def xlsExporterOut) {
        export.xlsExporter(xlsExporterOut)
                .setDetectCellType(true)
                .setIgnorePageMargins(true)
                .setWhitePageBackground(false)
                .setRemoveEmptySpaceBetweenColumns(true);
    }
}
