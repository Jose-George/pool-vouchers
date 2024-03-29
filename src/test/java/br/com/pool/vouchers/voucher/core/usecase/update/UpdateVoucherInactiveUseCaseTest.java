package br.com.pool.vouchers.voucher.core.usecase.update;


import br.com.pool.vouchers.api.core.domain.voucher.Voucher;
import br.com.pool.vouchers.api.core.exception.VoucherException;
import br.com.pool.vouchers.api.core.gateway.VoucherGateway;
import br.com.pool.vouchers.api.core.usecase.voucher.update.DefaultUpdateVoucherInactiveUseCase;
import br.com.pool.vouchers.api.core.usecase.voucher.update.VoucherInactiveCommand;
import br.com.pool.vouchers.api.core.usecase.voucher.update.VoucherInactiveOutput;
import br.com.pool.vouchers.voucher.utils.RecordFactory;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateVoucherInactiveUseCaseTest {

    @InjectMocks
    private DefaultUpdateVoucherInactiveUseCase useCase;
    @Mock
    private VoucherGateway gateway;

    private final EasyRandom easyRandom = new EasyRandom(new EasyRandomParameters().objectFactory(new RecordFactory()));

    @Test
    public void givenAValidCommand_whenCallsRedeemVoucher_shouldUpdateIsActiveAndUseDate() {
        final var voucher = easyRandom.nextObject(Voucher.class);

        when(gateway.findByVoucherEmail(voucher.getCodeVoucher(), voucher.getEmail())).thenReturn(Optional.of(voucher));
        when(gateway.redeemVoucher(voucher)).thenReturn(Optional.of(voucher));
        VoucherInactiveOutput output = useCase.execute(VoucherInactiveCommand.with(voucher.getCodeVoucher(), voucher.getEmail()));

        assertFalse(voucher.isActive());
        assertNotNull(voucher.getUseDate());

        verify(gateway, times(1)).redeemVoucher(voucher);
    }

    @Test
    public void givenAInvalidCommand_whenCallsRedeemVoucher_WhenVoucherNotFound() {
        final var voucher = easyRandom.nextObject(Voucher.class);

        VoucherInactiveCommand command = new VoucherInactiveCommand(voucher.getCodeVoucher(), voucher.getEmail());

        when(gateway.findByVoucherEmail(voucher.getCodeVoucher(), voucher.getEmail())).thenReturn(Optional.empty());

        assertThrows(VoucherException.class, () -> useCase.execute(command));

        verify(gateway, times(1)).findByVoucherEmail(voucher.getCodeVoucher(), voucher.getEmail());
    }

}


