package com.handy.handybus.data.model;

public class BusSearchItem {
    private String documentId;
    private String name;
    private String routeId;

    public BusSearchItem() {
    }

    public BusSearchItem(String documentId, String name, String routeId) {
        this.documentId = documentId;
        this.name = name;
        this.routeId = routeId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }
}
