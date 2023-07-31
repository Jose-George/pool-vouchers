package br.com.pool.vouchers.voucher.core.usecase.update;


import br.com.pool.vouchers.api.core.domain.voucher.Voucher;
import br.com.pool.vouchers.api.core.exception.VoucherException;
import br.com.pool.vouchers.api.core.gateway.VoucherGateway;
import br.com.pool.vouchers.api.core.usecase.voucher.retrieve.get.VoucherEmailCommand;
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

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
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

        when(gateway.redeemVoucher(voucher.getCodeVoucher(), voucher.getEmail())).thenReturn(Optional.of(voucher));
        VoucherInactiveOutput output = useCase.execute(VoucherInactiveCommand.with(voucher.getCodeVoucher(), voucher.getEmail()));

        assertFalse(voucher.isActive());
        assertNotNull(voucher.getUseDate());

        verify(gateway, times(1)).redeemVoucher(voucher.getCodeVoucher(), voucher.getEmail());
    }

    @Test
    public void givenAInvalidCommand_whenCallsRedeemVoucher_WhenVoucherNotFound() {
        String codeVoucher = "INVALID_VOUCHER_CODE";
        String email = "user@example.com";

        VoucherInactiveCommand command = new VoucherInactiveCommand(codeVoucher, email);

        when(gateway.redeemVoucher(codeVoucher, email)).thenReturn(Optional.empty());

        assertThrows(VoucherException.class, () -> useCase.execute(command));

        verify(gateway, times(1)).redeemVoucher(codeVoucher, email);
    }

}


