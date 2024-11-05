package dev.trendradar.app.common.http;

import org.springframework.http.HttpStatus;

class SuccessResponse<T> {
    public static class MetaResponse {
        private final int code;

        public MetaResponse(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    private final MetaResponse meta;
    private final T data;

    public SuccessResponse(HttpStatus code, T data) {
        this.meta = new MetaResponse(code.value());
        this.data = data;
    }

    // Getters for serialization
    public MetaResponse getMeta() {
        return meta;
    }

    public T getData() {
        return data;
    }
}
