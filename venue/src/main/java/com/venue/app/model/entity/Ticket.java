package com.venue.app.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "row_field", length = 10, nullable = false)
    private String rowField;

    @Column(name = "column_field", nullable = false)
    private Integer columnField;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "layout_id")
    private EventLayout eventLayout;

    @Column(name = "avaliable", nullable = false)
    private boolean available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public EventLayout getEventLayout() {
        return eventLayout;
    }

    public void setEventLayout(EventLayout eventLayout) {
        this.eventLayout = eventLayout;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}