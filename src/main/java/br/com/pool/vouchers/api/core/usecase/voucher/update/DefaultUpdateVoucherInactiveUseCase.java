package br.com.pool.vouchers.api.core.usecase.voucher.update;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;
import br.com.pool.vouchers.api.core.exception.VoucherException;
import br.com.pool.vouchers.api.core.gateway.VoucherGateway;
import br.com.pool.vouchers.api.core.usecase.utils.VoucherExpiredUtils;
import br.com.pool.vouchers.api.core.usecase.voucher.retrieve.get.DefaultGetVoucherEmailUseCase;
import br.com.pool.vouchers.api.core.usecase.voucher.retrieve.get.VoucherEmailCommand;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DefaultUpdateVoucherInactiveUseCase extends UpdateVoucherInactiveUseCase{

    private final VoucherGateway voucherGateway;

    private DefaultGetVoucherEmailUseCase defaultGetVoucherEmailUseCase;

    public DefaultUpdateVoucherInactiveUseCase(VoucherGateway voucherGateway) {
        this.voucherGateway = voucherGateway;
    }

    @Override
    public VoucherInactiveOutput execute(VoucherInactiveCommand command) {
        Voucher voucher =  voucherGateway.findByVoucherEmail(command.codeVoucher(), command.email())
                .orElseThrow(() -> new VoucherException("Voucher not found", HttpStatus.NOT_FOUND));

        if (VoucherExpiredUtils.isVoucherExpired(voucher)) {
            throw new VoucherException("The voucher has expired.", HttpStatus.NOT_FOUND);
        }

        voucher.setActive(false);
        voucher.setUseDate(Instant.now());

        return VoucherInactiveOutput.from(voucherGateway.redeemVoucher(voucher).get());
    }

}
