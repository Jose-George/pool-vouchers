package br.com.pool.vouchers.voucher.core.usecase.retrieve.get;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;
import br.com.pool.vouchers.api.core.exception.VoucherException;
import br.com.pool.vouchers.api.core.gateway.VoucherGateway;
import br.com.pool.vouchers.api.core.usecase.voucher.retrieve.get.DefaultGetVoucherEmailUseCase;
import br.com.pool.vouchers.api.core.usecase.voucher.retrieve.get.VoucherEmailCommand;
import br.com.pool.vouchers.voucher.utils.RecordFactory;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class GetVoucherUseCaseTest {

    @InjectMocks
    private DefaultGetVoucherEmailUseCase useCase;
    @Mock
    private VoucherGateway gateway;

    private final EasyRandom easyRandom = new EasyRandom(new EasyRandomParameters().objectFactory(new RecordFactory()));

    @Test
    public void givenAValidVoucherEmail_whenCallsGetVoucher_shouldReturnVoucher(){
        final var expectedVoucher = easyRandom.nextObject(Voucher.class);
        when(gateway.findByVoucherEmail(anyString(), anyString())).thenReturn(Optional.of(expectedVoucher));

        var voucherReturn = useCase.execute(VoucherEmailCommand.with(expectedVoucher));
        assertEquals(expectedVoucher.getCodeVoucher(), voucherReturn.codeVoucher());
        assertEquals(expectedVoucher.getFixedPercentageDiscount(), voucherReturn.percentageDiscount());
    }

    @Test
    void givenAInvalidVoucherEmail_whenCallsGetVoucher_shouldReturnNotFound() {
        final var expectedVoucher = easyRandom.nextObject(Voucher.class);
        expectedVoucher.setExpirationDate(Instant.now().minusMillis(1000));
        when(gateway.findByVoucherEmail(anyString(), anyString())).thenReturn(Optional.of(expectedVoucher));

        assertThrows(VoucherException.class, () -> useCase.execute(VoucherEmailCommand.with(expectedVoucher)));

    }

    @Test
    void givenAEmptyVoucherEmail_whenCallsGetVoucher_shouldReturnNotFound() {
        final var voucherMock = easyRandom.nextObject(Voucher.class);
        when(gateway.findByVoucherEmail(anyString(), anyString())).thenReturn(Optional.empty());
        assertThrows(VoucherException.class, () -> useCase.execute(VoucherEmailCommand.with(voucherMock)));

    }

}
