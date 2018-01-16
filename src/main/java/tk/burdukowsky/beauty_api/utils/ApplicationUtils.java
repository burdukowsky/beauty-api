package tk.burdukowsky.beauty_api.utils;

import java.lang.reflect.Field;

public class ApplicationUtils {

    /**
     * Сливает объекты o1 и o2 в новый объект.
     * Новый объект является в итоге объектом o1 с влитыми в него полями из o2
     * при условии, что сливаемое поле в o1 == null.
     *
     * @param o1    первый объект
     * @param o2    второй объект
     * @param clazz класс сливаемых объектов
     * @param <T>   тип объектов
     * @return новый объект
     * @throws Exception если не удается создать новый экземпляр объекта
     */
    public static <T> T merge(T o1, T o2, Class<T> clazz) throws Exception {
        T merged = clazz.newInstance();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            field.set(merged, field.get(o1) != null ? field.get(o1) : field.get(o2));
        }
        return merged;
    }
}
