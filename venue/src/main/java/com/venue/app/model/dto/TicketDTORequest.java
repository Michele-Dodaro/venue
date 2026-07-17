package com.venue.app.model.dto;

public class TicketDTORequest {
    private String rowField;
    private Integer columnField;
    private Long layoutId;
    private boolean available;


    public String getRowField() {
        return rowField;
    }

    public void setRowField(String rowField) {
        this.rowField = rowField;
    }

    public Integer getColumnField() {
        return columnField;
    }

    public void setColumnField(Integer columnField) {
        this.columnField = columnField;
    }

    public Long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Long layoutId) {
        this.layoutId = layoutId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}