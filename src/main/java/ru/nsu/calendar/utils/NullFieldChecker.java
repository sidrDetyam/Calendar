package ru.nsu.calendar.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;

@UtilityClass
@Log4j2
public class NullFieldChecker {
    public static void check(final @NonNull Object object) {
        final Class<?> clazz = object.getClass();
        final Field[] fields = clazz.getDeclaredFields();

        for (final Field field : fields) {
            if (field.isAnnotationPresent(NonNullField.class)) {
                field.setAccessible(true);
                try {
                    final Object value = field.get(object);
                    if (value == null) {
                        throw new NullFieldException("Field " + field.getName() + " cannot be null");
                    }
                } catch (IllegalAccessException e) {
                    log.error("Something goes wrong...", e);
                    throw new RuntimeException(e);
                }
            }
        }
    }
}