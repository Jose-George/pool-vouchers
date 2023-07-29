package br.com.pool.vouchers.api.core.usecase;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN in);
}
