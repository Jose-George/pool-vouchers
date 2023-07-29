package br.com.pool.vouchers.api.core.domain.dto;

import java.time.Instant;

public record DiscountDTO(String percentageDiscount, Instant expirationDate) {
}
