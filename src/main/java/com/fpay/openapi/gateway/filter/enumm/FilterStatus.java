package com.fpay.openapi.gateway.filter.enumm;

public enum FilterStatus {
    SUCCESS(1), SUCCESS_AGGREGATE_REQUEST(2), SKIP(-1), ERROR(-2);
    private int status;

    FilterStatus(int status) {
        this.status = status;
    }
}
