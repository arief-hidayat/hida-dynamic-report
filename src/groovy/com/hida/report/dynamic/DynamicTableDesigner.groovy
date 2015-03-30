package com.hida.report.dynamic

import com.hida.report.Templates
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder
import net.sf.dynamicreports.report.builder.component.ComponentBuilder
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder
import net.sf.dynamicreports.report.builder.component.PageXofYBuilder
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder
import net.sf.dynamicreports.report.constant.HorizontalAlignment
import net.sf.dynamicreports.report.definition.datatype.DRIDataType
import net.sf.dynamicreports.report.exception.DRException

import static net.sf.dynamicreports.report.builder.DynamicReports.*

/**
 * Created by hida on 26/3/2015.
 */
class DynamicTableDesigner {

    public static JasperReportBuilder build(String titleName, DynamicTableReport dynamicReport, def dataSource, HorizontalListBuilder titleComponents = null) throws DRException {
        build(titleName, null, dynamicReport, dataSource, titleComponents)
    }

    public static JasperReportBuilder build(String titleName, String barcode, DynamicTableReport dynamicReport, def dataSource, HorizontalListBuilder titleComponents = null) throws DRException {
        JasperReportBuilder report = buildHeader(titleName, barcode, titleComponents);
        buildColumns(dynamicReport, report);
        report.setDataSource(dataSource);
        return report;
    }
    public static JasperReportBuilder buildHeader(String titleName, String barcode, HorizontalListBuilder titleComponents = null) throws DRException {
        JasperReportBuilder report = report().setTemplate(Templates.reportTemplate);
        if(!titleComponents) return report.title(Templates.createTitleComponent(titleName, barcode));
        report.title(cmp.verticalList(Templates.createTitleComponent(titleName, barcode), titleComponents, cmp.verticalGap(5)))

    }

    public static JasperReportBuilder buildColumns(DynamicTableReport dynamicReport, JasperReportBuilder report) throws DRException {
        List<DynamicColumn> columns = dynamicReport.getColumns();
        Map<String, TextColumnBuilder> drColumns = new HashMap<String, TextColumnBuilder>();
        for (DynamicColumn column : columns) {
            TextColumnBuilder drColumn = col.column(column.getTitle(), column.getName(), (DRIDataType) type.detectType(column.getType()));
            if (column.getPattern() != null) {
                drColumn.setPattern(column.getPattern());
            }
            if (column.getHorizontalAlignment() != null) {
                drColumn.setHorizontalAlignment(column.getHorizontalAlignment());
            }
            drColumns.put(column.getName(), drColumn);
            report.columns(drColumn);
        }

        for (String group : dynamicReport.getGroups()) {
            ColumnGroupBuilder group2 = grp.group(drColumns.get(group));
            report.groupBy(group2);

            for (String subtotal : dynamicReport.getSubtotals()) {
                report.subtotalsAtGroupFooter(group2, sbt.sum(drColumns.get(subtotal)));
            }
        }

        for (String subtotal : dynamicReport.getSubtotals()) {
            report.subtotalsAtSummary(sbt.sum(drColumns.get(subtotal)));
        }

        if (dynamicReport.getTitle() != null) {
            TextFieldBuilder<String> title = cmp.text(dynamicReport.getTitle())
                    .setStyle(Templates.bold12CenteredStyle)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER);
            report.addTitle(title);
        }
        if (dynamicReport.isShowPageNumber()) {
            PageXofYBuilder pageXofY = cmp.pageXofY()
                    .setStyle(Templates.boldCenteredStyle);
            report.addPageFooter(pageXofY);
        }
        report
    }

    public static List<TextColumnBuilder<?>> getTextColumnBuilders(List<DynamicColumn> columns) {
        List<TextColumnBuilder<?>> res = new ArrayList<>()
        for (DynamicColumn column : columns) {
            TextColumnBuilder drColumn = col.column(column.getTitle(), column.getName(), (DRIDataType) type.detectType(column.getType()));
            if (column.getPattern() != null) {
                drColumn.setPattern(column.getPattern());
            }
            if (column.getHorizontalAlignment() != null) {
                drColumn.setHorizontalAlignment(column.getHorizontalAlignment());
            }
            res.add(drColumn)
        }
        res
    }

}
