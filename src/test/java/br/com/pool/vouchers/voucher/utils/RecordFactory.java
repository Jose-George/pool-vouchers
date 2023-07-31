package br.com.pool.vouchers.voucher.utils;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.ObjectCreationException;
import org.jeasy.random.ObjenesisObjectFactory;
import org.jeasy.random.api.RandomizerContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.RecordComponent;
import java.util.List;

public class RecordFactory extends ObjenesisObjectFactory {

    private EasyRandom easyRandom;

    @Override
    public <T> T createInstance(Class<T> type, RandomizerContext context) {
        if (easyRandom == null) {
            easyRandom = new EasyRandom(context.getParameters());
        }

        if (type.isRecord()) {
            return createRandomRecord(type);
        } else {
            return super.createInstance(type, context);
        }
    }

    private <T> T createRandomRecord(Class<T> recordType) {
        RecordComponent[] recordComponents = recordType.getRecordComponents();
        Object[] randomValues = new Object[recordComponents.length];
        for (int i = 0; i < recordComponents.length; i++) {
            var record = recordComponents[i];
            if (List.class.isAssignableFrom(record.getType())) {
                ParameterizedType listType = (ParameterizedType) record.getGenericType();
                randomValues[i] = List.of(easyRandom.nextObject((Class<?>) listType.getActualTypeArguments()[0]));
            } else if (record.getType().isEnum()) {
                Object[] enumValues = record.getType().getEnumConstants();
                randomValues[i] = enumValues[easyRandom.nextInt(enumValues.length)];
            } else {
                randomValues[i] = easyRandom.nextObject(record.getType());
            }
        }
        try {
            return getCanonicalConstructor(recordType).newInstance(randomValues);
        } catch (Exception e) {
            throw new ObjectCreationException("Unable to create a random instance of recordType " + recordType, e);
        }
    }

    private <T> Constructor<T> getCanonicalConstructor(Class<T> recordType) {
        RecordComponent[] recordComponents = recordType.getRecordComponents();
        Class<?>[] componentTypes = new Class<?>[recordComponents.length];
        for (int i = 0; i < recordComponents.length; i++) {
            componentTypes[i] = recordComponents[i].getType();
        }
        try {
            return recordType.getDeclaredConstructor(componentTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Invalid record definition", e);
        }
    }

}

