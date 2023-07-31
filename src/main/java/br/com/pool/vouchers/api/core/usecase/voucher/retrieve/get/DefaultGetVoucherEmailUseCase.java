package br.com.pool.vouchers.api.core.usecase.voucher.retrieve.get;

import br.com.pool.vouchers.api.core.exception.VoucherException;
import br.com.pool.vouchers.api.core.gateway.VoucherGateway;
import br.com.pool.vouchers.api.core.usecase.utils.VoucherExpiredUtils;
import org.springframework.http.HttpStatus;

public class DefaultGetVoucherEmailUseCase extends GetVoucherEmailUseCase {

    private final VoucherGateway voucherGateway;

    public DefaultGetVoucherEmailUseCase(VoucherGateway voucherGateway) {
        this.voucherGateway = voucherGateway;
    }

    @Override
    public VoucherEmailOutput execute(VoucherEmailCommand command) {
        var voucher = voucherGateway.findByVoucherEmail(command.codeVoucher(), command.email()).
                orElseThrow(() -> new VoucherException("Voucher not found.", HttpStatus.NOT_FOUND));

        if (VoucherExpiredUtils.isVoucherExpired(voucher)) {
            throw new VoucherException("The voucher has expired.", HttpStatus.NOT_FOUND);
        }

        return VoucherEmailOutput.from(voucher);
    }
}
