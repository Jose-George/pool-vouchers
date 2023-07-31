package br.com.pool.vouchers.api.core.usecase.voucher.retrieve.list;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;
import br.com.pool.vouchers.api.core.exception.VoucherException;
import br.com.pool.vouchers.api.core.gateway.VoucherGateway;
import br.com.pool.vouchers.api.core.usecase.utils.VoucherExpiredUtils;
import org.springframework.http.HttpStatus;

import java.util.List;

public class DefaultListSpecialOfferUseCase extends ListSpecialOfferUseCase {

    private final VoucherGateway voucherGateway;

    public DefaultListSpecialOfferUseCase(VoucherGateway voucherGateway) {
        this.voucherGateway = voucherGateway;
    }

    @Override
    public List<VoucherSpecialOfferOutput> execute(VoucherSpecialOfferCommand command) {
        List<Voucher> vouchers = voucherGateway.findAllSpecialOffer(command.email())
                .orElseThrow(() -> new VoucherException("Voucher not found", HttpStatus.NOT_FOUND));

        List<Voucher> activeVouchers = filterActiveAndNonExpiredVouchers(vouchers);

        if (activeVouchers.isEmpty()) {
            throw new VoucherException("Voucher not found", HttpStatus.NOT_FOUND);
        }

        return convertToVoucherSpecialOfferOutputs(activeVouchers);
    }

    private List<Voucher> filterActiveAndNonExpiredVouchers(List<Voucher> vouchers) {
        return vouchers.stream()
                .filter(voucher -> voucher.isActive() && !VoucherExpiredUtils.isVoucherExpired(voucher))
                .toList();
    }

    private List<VoucherSpecialOfferOutput> convertToVoucherSpecialOfferOutputs(List<Voucher> vouchers) {
        return vouchers.stream()
                .map(VoucherSpecialOfferOutput::from)
                .toList();
    }
}

