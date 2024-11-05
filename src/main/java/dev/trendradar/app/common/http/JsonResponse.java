package dev.trendradar.app.common.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class JsonResponse<T> {
    private HttpStatus status;
    private T body;

    public JsonResponse() {

    }

    public JsonResponse<T> status(HttpStatus status) {
        this.status = status;
        return this;
    }

    public JsonResponse<T> body(T obj) {
        this.body = obj;
        return this;
    }

    public ResponseEntity<SuccessResponse<T>> success() {
        return ResponseEntity
                .status(status)
                .body(new SuccessResponse<>(status, body));
    }

    public ResponseEntity<ErrorResponse> error() {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(status, body.toString()));
    }
}
