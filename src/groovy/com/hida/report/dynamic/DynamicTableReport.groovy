package com.hida.report.dynamic

/**
 * Created by hida on 26/3/2015.
 */
class DynamicTableReport {

    String title;
    List<DynamicColumn> columns = [];
    List<String> groups = [];
    List<String> subtotals = [];
    boolean showPageNumber;

    public void addColumn(DynamicColumn column) {
        this.columns.add(column);
    }

    public void addGroup(String column) {
        this.groups.add(column);
    }

    public void addSubtotal(String column) {
        this.subtotals.add(column);
    }
}
