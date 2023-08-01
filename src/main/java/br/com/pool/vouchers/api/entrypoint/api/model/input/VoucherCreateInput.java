package br.com.pool.vouchers.api.entrypoint.api.model.input;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.Instant;

public record VoucherCreateInput(
    @Schema(description = "Nome do cliente", example = "João")
    String name,
    @Schema(description = "Nome do voucher", example = "COMPRAS X")
    String nameVoucher,
    @Schema(description = "E-mail do cliente", example = "joao@email.com")
    String email,
    @Schema(description = "Porcentagem de desconto %", example = "20")
    BigDecimal percentagemDiscount,
    @Schema(description = "Data de expiração", example = "15/03/2024")
    Instant expirationDate,
    @Schema(description = "Oferta Especial", example = "IPHONE 14 PRO MAX")
    String specialOffer
    ) {

}
