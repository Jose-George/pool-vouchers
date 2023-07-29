package br.com.pool.vouchers.api.core.usecase.voucher.create;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;
import br.com.pool.vouchers.api.core.gateway.VoucherGateway;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateVoucherUseCase {

    private final VoucherGateway voucherGateway;

    public DefaultCreateCategoryUseCase(VoucherGateway voucherGateway) {
        this.voucherGateway = Objects.requireNonNull(voucherGateway);
    }

    @Override
    public CreateVoucherOutput execute(final CreateVoucherCommand createVoucherCommand) {
        Voucher voucher = Voucher.newVoucher(createVoucherCommand.id(), createVoucherCommand.name(), createVoucherCommand.nameVoucher(),
                createVoucherCommand.email(), createVoucherCommand.fixedPercentageDiscount(), createVoucherCommand.expirationDate());

        return CreateVoucherOutput.from(voucherGateway.create(voucher));
    }
}
