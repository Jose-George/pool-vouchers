package br.com.pool.vouchers.voucher.core.usecase.create;

import br.com.pool.vouchers.api.core.gateway.VoucherGateway;
import br.com.pool.vouchers.api.core.usecase.voucher.create.CreateVoucherCommand;
import br.com.pool.vouchers.api.core.usecase.voucher.create.DefaultCreateVoucherUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class CreateVoucherUseCaseTest {

    // 1. Teste do caminho feliz)
    // 2. Teste simulando um erro generico vindo do gateway

    @InjectMocks
    private DefaultCreateVoucherUseCase useCase;
    @Mock
    private VoucherGateway gateway;

    @Test
    public void givenAValidCommand_whenCallsCreateVoucher_shouldReturnVoucherId() {

        final var expectedName = "Francesco Forgione";
        final var expectedNameVoucher = "Promoção dia dos pais";
        final var expectedEmail = "francescoforgione@email.com";
        final var expectedFixedPercentageDiscount = 20;
        final var expectedId = "1341341";
        final var expectedExpirationDate = Instant.now();
        final var specialOffer = "COMPRA DE CADEIRA R123";

        final var command =
                CreateVoucherCommand.with(expectedId, expectedName, expectedNameVoucher,
                        expectedEmail, BigDecimal.valueOf(expectedFixedPercentageDiscount), expectedExpirationDate, specialOffer);

        Mockito.when(gateway.create(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(command);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.codeVoucher());

        Mockito.verify(gateway, Mockito.times(1))
                .create(Mockito.argThat(voucher ->
                        Objects.equals(expectedName, voucher.getName())
                                && Objects.equals(expectedNameVoucher, voucher.getNameVoucher())
                                && Objects.nonNull(voucher.getExpirationDate())
                                && Objects.equals(expectedEmail, voucher.getEmail())
                                && Objects.nonNull(voucher.getCreatedAt())
                                && Objects.equals(expectedId, voucher.getId())
                ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final var expectedName = "Francesco Forgione";
        final var expectedNameVoucher = "Promoção dia dos pais";
        final var expectedEmail = "francescoforgione@email.com";
        final var expectedFixedPercentageDiscount = 20;
        final var expectedId = "1341341";
        final var expectedExpirationDate = Instant.now();
        final var expectedErrorMessage = "Gateway error";
        final var expectedIsActive = true;
        final var specialOffer = "COMPRA DE CADEIRA R123";

        final var command =
                CreateVoucherCommand.with(expectedId, expectedName, expectedNameVoucher,
                        expectedEmail, BigDecimal.valueOf(expectedFixedPercentageDiscount), expectedExpirationDate, specialOffer);

        Mockito.when(gateway.create(Mockito.any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(command));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        Mockito.verify(gateway, Mockito.times(1))
                .create(Mockito.argThat(voucher ->
                        Objects.equals(expectedName, voucher.getName())
                        && Objects.equals(expectedIsActive, voucher.isActive())
                        && Objects.nonNull(voucher.getCreatedAt())

                ));
    }

}
