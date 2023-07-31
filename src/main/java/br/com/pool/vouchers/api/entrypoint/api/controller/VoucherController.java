package br.com.pool.vouchers.api.entrypoint.api.controller;

import br.com.pool.vouchers.api.entrypoint.api.model.input.VoucherCreateInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Voucher")
public interface VoucherController {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    @Operation(
            tags = "Voucher",
            summary = "Adicionar um voucher para um cliente")
    ResponseEntity<?> createVoucher(final VoucherCreateInput voucherInput);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    @Operation(
            tags = "Voucher",
            summary = "Resgantando um voucher para um cliente")
    ResponseEntity<?> redeemVoucher(final String codeVoucher, final String email);

    @Operation(
            tags = "Voucher",
            summary = "Consultar um voucher por codeVoucher e E-mail")
    ResponseEntity<?> getVoucherCodeEmailActive(final String codeVoucher, final String email);

    @Operation(
            tags = "Voucher",
            summary = "Consultar ofertas especiais ativas para um e-mail")
    ResponseEntity<List<?>> getAllSpecialOffer(final String email);

}
