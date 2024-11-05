package dev.trendradar.app.common.http;

import org.springframework.http.HttpStatus;

class ErrorResponse {
    public record MetaResponse(int code, String message) {}

    private MetaResponse meta;

    public ErrorResponse(HttpStatus code, String message) {
        this.meta = new MetaResponse(code.value(), message);
    }

    public MetaResponse getMeta() {
        return meta;
    }

    public void setMeta(MetaResponse meta) {
        this.meta = meta;
    }
}
