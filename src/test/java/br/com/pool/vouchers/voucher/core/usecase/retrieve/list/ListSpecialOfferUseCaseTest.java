package br.com.pool.vouchers.voucher.core.usecase.retrieve.list;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;
import br.com.pool.vouchers.api.core.exception.VoucherException;
import br.com.pool.vouchers.api.core.gateway.VoucherGateway;
import br.com.pool.vouchers.api.core.usecase.voucher.retrieve.get.VoucherEmailCommand;
import br.com.pool.vouchers.api.core.usecase.voucher.retrieve.list.DefaultListSpecialOfferUseCase;
import br.com.pool.vouchers.api.core.usecase.voucher.retrieve.list.VoucherSpecialOfferCommand;
import br.com.pool.vouchers.voucher.utils.RecordFactory;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
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
public class ListSpecialOfferUseCaseTest {

    @InjectMocks
    private DefaultListSpecialOfferUseCase useCase;
    @Mock
    private VoucherGateway gateway;

    private final EasyRandom easyRandom = new EasyRandom(new EasyRandomParameters().objectFactory(new RecordFactory()));

    @Test
    public void givenAValidListSpecialOffers_whenCallsListVoucher_thenShouldReturnVoucher() {
        final var voucher = easyRandom.nextObject(Voucher.class);
        when(gateway.findAllSpecialOffer(anyString())).thenReturn(Optional.of(List.of(voucher)));

        var vouchersList = useCase.execute(VoucherSpecialOfferCommand.with(voucher.getEmail()));
        assertEquals(1, vouchersList.size());
        assertEquals(vouchersList.get(0).codeVoucher(), voucher.getCodeVoucher());
        assertEquals(vouchersList.get(0).SpecialOffer(), voucher.getSpecialOffer());
    }

    @Test
    void givenAInvalidVoucherSpecialOffers_whenCallsListVoucher_shouldReturnNotFound() {
        final var expectedVoucher = easyRandom.nextObject(Voucher.class);
        when(gateway.findAllSpecialOffer((anyString()))).thenReturn(Optional.empty());

        assertThrows(VoucherException.class, () -> useCase.execute(VoucherSpecialOfferCommand.with("teste")));

    }

}
