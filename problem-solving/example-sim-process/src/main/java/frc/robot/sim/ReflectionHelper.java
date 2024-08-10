package frc.robot.sim;

import java.lang.reflect.Field;

public class ReflectionHelper {

    private ReflectionHelper() {}

    public static Field getFieldShallow(Class<?> cls, String name) throws NoSuchFieldException {
        Field field;

        try {
            field = cls.getField(name);
        } catch (NoSuchFieldException e) {
            field = cls.getDeclaredField(name);
        }

        return field;
    }

    public static Field getField(Class<?> cls, String name) {
        while (cls != null) {
            try {
                return getFieldShallow(cls, name);
            } catch (NoSuchFieldException ignored) {}

            cls = cls.getSuperclass();
        }

        throw new Error("field not found " + name);
    }
}
