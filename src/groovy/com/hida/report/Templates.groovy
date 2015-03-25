package com.hida.report

import grails.util.Holders
import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder
import net.sf.dynamicreports.report.builder.component.ComponentBuilder
import net.sf.dynamicreports.report.builder.component.ImageBuilder
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType
import net.sf.dynamicreports.report.builder.style.StyleBuilder
import net.sf.dynamicreports.report.builder.tableofcontents.TableOfContentsCustomizerBuilder
import net.sf.dynamicreports.report.constant.HorizontalAlignment
import net.sf.dynamicreports.report.constant.VerticalAlignment
import net.sf.dynamicreports.report.definition.ReportParameters

import java.awt.Color

import static net.sf.dynamicreports.report.builder.DynamicReports.*

/**
 * Created by hida on 26/3/2015.
 */
class Templates {

    private static final String COMPANY_NAME = Holders.config.dynamicReport?.companyName ?: ""
    private static final String COMPANY_LOGO_FILE_PATH = Holders.config.dynamicReport?.companyLogoPath ?: null

    public static final StyleBuilder appTitleStyle;
    public static final StyleBuilder alignRightStyle;
    public static final StyleBuilder rootStyle;
    public static final StyleBuilder boldStyle;
    public static final StyleBuilder italicStyle;
    public static final StyleBuilder boldCenteredStyle;
    public static final StyleBuilder bold12CenteredStyle;
    public static final StyleBuilder bold18CenteredStyle;
    public static final StyleBuilder bold22CenteredStyle;
    public static final StyleBuilder columnStyle;
    public static final StyleBuilder columnTitleStyle;
    public static final StyleBuilder groupStyle;
    public static final StyleBuilder subtotalStyle;

    public static final ReportTemplateBuilder reportTemplate;
    public static final CurrencyType currencyType;
    public static final ComponentBuilder<?, ?> dynamicReportsComponent;
    public static final ComponentBuilder<?, ?> footerComponent;

    static {
        rootStyle           = stl.style().setPadding(2);
        boldStyle           = stl.style(rootStyle).bold();
        italicStyle         = stl.style(rootStyle).italic();
        boldCenteredStyle   = stl.style(boldStyle)
                .setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
        bold12CenteredStyle = stl.style(boldCenteredStyle)
                .setFontSize(12);
        bold18CenteredStyle = stl.style(boldCenteredStyle)
                .setFontSize(18);
        appTitleStyle = stl.style(boldCenteredStyle)
                .setFontSize(18).setForegroundColor(Color.lightGray);
        bold22CenteredStyle = stl.style(boldCenteredStyle)
                .setFontSize(22);
        columnStyle         = stl.style(rootStyle).setVerticalAlignment(VerticalAlignment.MIDDLE);
        columnTitleStyle    = stl.style(columnStyle)
                .setBorder(stl.pen1Point())
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setBackgroundColor(Color.LIGHT_GRAY)
                .bold();
        groupStyle          = stl.style(boldStyle)
                .setHorizontalAlignment(HorizontalAlignment.LEFT);
        subtotalStyle       = stl.style(boldStyle)
                .setTopBorder(stl.pen1Point());

        alignRightStyle = stl.style(rootStyle).setAlignment(HorizontalAlignment.RIGHT, VerticalAlignment.TOP);

        StyleBuilder crosstabGroupStyle      = stl.style(columnTitleStyle);
        StyleBuilder crosstabGroupTotalStyle = stl.style(columnTitleStyle)
                .setBackgroundColor(new Color(170, 170, 170));
        StyleBuilder crosstabGrandTotalStyle = stl.style(columnTitleStyle)
                .setBackgroundColor(new Color(140, 140, 140));
        StyleBuilder crosstabCellStyle       = stl.style(columnStyle)
                .setBorder(stl.pen1Point());

        TableOfContentsCustomizerBuilder tableOfContentsCustomizer = tableOfContentsCustomizer()
                .setHeadingStyle(0, stl.style(rootStyle).bold());

        reportTemplate = template()
                .setLocale(Locale.ENGLISH)
                .setColumnStyle(columnStyle)
                .setColumnTitleStyle(columnTitleStyle)
                .setGroupStyle(groupStyle)
                .setGroupTitleStyle(groupStyle)
                .setSubtotalStyle(subtotalStyle)
                .highlightDetailEvenRows()
                .crosstabHighlightEvenRows()
                .setCrosstabGroupStyle(crosstabGroupStyle)
                .setCrosstabGroupTotalStyle(crosstabGroupTotalStyle)
                .setCrosstabGrandTotalStyle(crosstabGrandTotalStyle)
                .setCrosstabCellStyle(crosstabCellStyle)
                .setTableOfContentsCustomizer(tableOfContentsCustomizer);

        currencyType = new CurrencyType();

        ImageBuilder image = null
        if(COMPANY_LOGO_FILE_PATH) image = cmp.image(new FileInputStream(new File(COMPANY_LOGO_FILE_PATH)))
        else image = cmp.image(Templates.class.classLoader.getResourceAsStream("images/company_logo.png"))

        dynamicReportsComponent =
                cmp.verticalList(
                        image.setHeight(33).setHorizontalAlignment(HorizontalAlignment.CENTER),
                        cmp.text(COMPANY_NAME).setStyle(stl.style(boldCenteredStyle).setFontSize(15))).setFixedWidth(150);

        footerComponent = cmp.pageXofY()
                .setStyle(
                stl.style(boldCenteredStyle)
                        .setTopBorder(stl.pen1Point()));
    }


//    public static ComponentBuilder<?, ?> createTitleComponent(String label) {
//        return cmp.horizontalList()
//                .add(
//                dynamicReportsComponent,
//                cmp.text(label).setStyle(bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT))
//                .newRow()
//                .add(cmp.line())
//                .newRow()
//                .add(cmp.verticalGap(10));
//    }
    public static ComponentBuilder<?, ?> createTitleComponent(String label, String barcode = null) {
        ComponentBuilder<?, ?> rightComponent = barcode ? cmp.verticalList(
                cmp.text(label).setStyle(stl.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT).setFontSize(18).setRightPadding(15)),
                bcode.barbecue_code128(barcode).setDimension(125, 20).setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.RIGHT))
        ).setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.RIGHT)) :
                cmp.text(label).setStyle(bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT)
        return cmp.horizontalList()
                .add(
                dynamicReportsComponent,
                rightComponent
        )
                .newRow()
                .add(cmp.verticalGap(5))
                .newRow()
                .add(cmp.line())
                .newRow()
                .add(cmp.verticalGap(10));
    }

    public static CurrencyValueFormatter createCurrencyValueFormatter(String label) {
        return new CurrencyValueFormatter(label);
    }

    public static class CurrencyType extends BigDecimalType {
        private static final long serialVersionUID = 1L;

        @Override
        public String getPattern() {
            return Holders.config.dynamicReport?.currencyPattern ?: "\$ #,###.00";
        }
    }

    private static class CurrencyValueFormatter extends AbstractValueFormatter<String, Number> {
        private static final long serialVersionUID = 1L;

        private String label;

        public CurrencyValueFormatter(String label) {
            this.label = label;
        }

        @Override
        public String format(Number value, ReportParameters reportParameters) {
            return label + currencyType.valueToString(value, reportParameters.getLocale());
        }
    }
}
