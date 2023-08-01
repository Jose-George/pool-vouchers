package br.com.pool.vouchers.api.entrypoint.handler.model;

import lombok.Builder;

@Builder
public record ResponseError(String message) {

}
