package br.com.pool.vouchers.api.entrypoint.api.controller.impl;

import br.com.pool.vouchers.api.core.usecase.voucher.create.CreateVoucherCommand;
import br.com.pool.vouchers.api.core.usecase.voucher.create.CreateVoucherUseCase;
import br.com.pool.vouchers.api.core.usecase.voucher.retrieve.get.GetVoucherEmailUseCase;
import br.com.pool.vouchers.api.core.usecase.voucher.retrieve.get.VoucherEmailCommand;
import br.com.pool.vouchers.api.core.usecase.voucher.retrieve.list.ListSpecialOfferUseCase;
import br.com.pool.vouchers.api.core.usecase.voucher.retrieve.list.VoucherSpecialOfferCommand;
import br.com.pool.vouchers.api.core.usecase.voucher.update.UpdateVoucherInactiveUseCase;
import br.com.pool.vouchers.api.core.usecase.voucher.update.VoucherInactiveCommand;
import br.com.pool.vouchers.api.entrypoint.api.controller.VoucherController;
import br.com.pool.vouchers.api.entrypoint.api.model.input.VoucherCreateInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/v1/vouchers")
public class VoucherControllerImpl implements VoucherController {

    private CreateVoucherUseCase createVoucherUseCase;
    private GetVoucherEmailUseCase getVoucherEmailUseCase;
    private ListSpecialOfferUseCase listSpecialOfferUseCase;
    private UpdateVoucherInactiveUseCase updateVoucherInactiveUseCase;

    public VoucherControllerImpl(final CreateVoucherUseCase createVoucherUseCase, final GetVoucherEmailUseCase getVoucherEmailUseCase,
                                 final ListSpecialOfferUseCase listSpecialOfferUseCase, final UpdateVoucherInactiveUseCase updateVoucherInactiveUseCase) {
        this.createVoucherUseCase = Objects.requireNonNull(createVoucherUseCase);
        this.getVoucherEmailUseCase = Objects.requireNonNull(getVoucherEmailUseCase);
        this.listSpecialOfferUseCase = listSpecialOfferUseCase;
        this.updateVoucherInactiveUseCase = updateVoucherInactiveUseCase;
    }

    @Override
    @PostMapping
    public ResponseEntity<?> createVoucher(@RequestBody final VoucherCreateInput voucherInput) {
        log.info("Criando voucher p/ usuário de e-mail {}", voucherInput.email());
        var voucherReturn = createVoucherUseCase.execute(CreateVoucherCommand.with(null, voucherInput.nameVoucher(), voucherInput.nameVoucher(), voucherInput.email(),
                voucherInput.percentagemDiscount(), voucherInput.expirationDate(), voucherInput.specialOffer()));
        return ResponseEntity.status(HttpStatus.CREATED).body(voucherReturn);
    }

    @Override
    @PutMapping("/redeem/{codeVoucher}/{email}")
    public ResponseEntity<?> redeemVoucher(@PathVariable final String codeVoucher, @PathVariable final String email) {
        log.info("Resgatando voucher p/ usuário de e-mail {}", email);
        var voucherReturn = updateVoucherInactiveUseCase.execute(VoucherInactiveCommand.with(codeVoucher, email));
        return ResponseEntity.status(HttpStatus.OK).body(voucherReturn);
    }

    @Override
    @GetMapping("/{codeVoucher}/{email}")
    public ResponseEntity<?> getVoucherCodeEmailActive(@PathVariable final String codeVoucher, @PathVariable final String email) {
        log.info("Consultando voucher p/ usuário de e-mail {}", email);
        var voucherReturn = getVoucherEmailUseCase.execute(VoucherEmailCommand.with(codeVoucher, email));
        return ResponseEntity.status(HttpStatus.OK).body(voucherReturn);
    }

    @Override
    @GetMapping("/specialoffer/{email}")
    public ResponseEntity<List<?>> getAllSpecialOffers(@PathVariable final String email) {
        return ResponseEntity.ok(listSpecialOfferUseCase.execute(VoucherSpecialOfferCommand.with(email)).stream()
                .toList());
    }
}
