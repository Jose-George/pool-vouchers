package br.com.pool.vouchers.api.entrypoint.api.controller;

import br.com.pool.vouchers.api.entrypoint.api.model.input.VoucherCreateInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Voucher")
public interface VoucherController {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voucher created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error occurred"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred"),
    })
    @Operation(
            tags = "Voucher",
            summary = "Add a new voucher for a customer",
            description = "Creates and adds a new voucher for the given customer."
    )
    ResponseEntity<?> createVoucher(
            @Parameter(description = "Voucher details", required = true)
            final VoucherCreateInput voucherInput);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voucher redeemed successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error occurred"),
            @ApiResponse(responseCode = "500", description = "An internal server error occurred"),
    })
    @Operation(
            tags = "Voucher",
            summary = "Redeem a voucher for a customer",
            description = "Redeems a voucher for the given customer by code and email."
    )
    ResponseEntity<?> redeemVoucher(
            @Parameter(description = "Voucher code", required = true)
            final String codeVoucher,
            @Parameter(description = "Customer's email", required = true)
            final String email);

    @Operation(
            tags = "Voucher",
            summary = "Get a voucher by code and email",
            description = "Retrieves the voucher details for the given code and email."
    )
    ResponseEntity<?> getVoucherCodeEmailActive(
            @Parameter(description = "Voucher code", required = true)
            final String codeVoucher,
            @Parameter(description = "Customer's email", required = true)
            final String email);

    @Operation(
            tags = "Voucher",
            summary = "Get all active special offers for an email",
            description = "Retrieves all active special offers for the given email."
    )
    ResponseEntity<List<?>> getAllSpecialOffers(
            @Parameter(description = "Customer's email", required = true)
            final String email);
}
