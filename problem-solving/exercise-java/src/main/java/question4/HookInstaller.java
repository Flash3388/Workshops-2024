package question4;

import java.lang.reflect.Field;
import java.util.List;

@SuppressWarnings("rawtypes")
public class HookInstaller {

    // PLEASE IGNORE THIS CLASS!!!
    // ITS COMPLEX AND NOT PART OF THE PROBLEM.

    public static ListHook installHookOnField(Object instance, String fieldName, ItemChecker itemChecker) {
        try {
            Class<?> cls = instance.getClass();
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);

            List originalList = (List) field.get(instance);
            ListHook hook = new ListHook(originalList, itemChecker);
            field.set(instance, hook);

            return hook;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error("install hook failed", e);
        }
    }

    public static void uninstallHook(Object instance, String fieldName, ListHook hook) {
        try {
            Class<?> cls = instance.getClass();
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);

            field.set(instance, hook.originalList);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error("install hook failed", e);
        }
    }
}
