package br.com.pool.vouchers.voucher.domain;

import br.com.pool.vouchers.api.core.domain.voucher.Voucher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

public class VoucherTest {

    @Test
    public void givenAValidParams_whenCallNewVoucher_thenInstantiateAVoucher() {

        final var expectedName = "Francesco Forgione";
        final var expectedNameVoucher = "Promoção dia dos pais";
        final var expectedEmail = "francescoforgione@email.com";
        final var expectedIsActive = true;
        final var expectedFixedPercentageDiscount = 20;
        final var expectedId = "1341341";
        final var expectedExpirationDate = Instant.now();

        final var actualVoucher =
                Voucher.newVoucher(expectedId, expectedName, expectedNameVoucher, expectedEmail,
                        BigDecimal.valueOf(expectedFixedPercentageDiscount), expectedExpirationDate);

        Assertions.assertNotNull(actualVoucher);
        Assertions.assertNotNull(actualVoucher.getId());
        Assertions.assertNotNull(actualVoucher.getCodeVoucher());
        Assertions.assertEquals(expectedName, actualVoucher.getName());
        Assertions.assertEquals(expectedNameVoucher, actualVoucher.getNameVoucher());
        Assertions.assertEquals(expectedEmail, actualVoucher.getEmail());
        Assertions.assertEquals(expectedIsActive, actualVoucher.isActive());

    }
}
