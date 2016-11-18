package util;

import java.util.Iterator;

/**
 * полезные функции для работы со строками
 */
public class StringUtils {
    /**
     * объединяет коллекцию строк в одну строку, используя указанный разделитель
     * @param iterable коллекция строк
     * @param delimiter разделитель
     * @return объединенная строка
     */
    public static String join(Iterable<? extends CharSequence> iterable, String delimiter) {
        StringBuilder builder = new StringBuilder();
        Iterator<? extends CharSequence> iterator = iterable.iterator();
        if (iterator.hasNext()) {
            builder.append(iterator.next());
        }
        while (iterator.hasNext()) {
            builder.append(delimiter);
            builder.append(iterator.next());
        }
        return builder.toString();
    }

    /**
     * проверяет, является ли строка null или пустой
     * @param str строка
     * @return является ли строка null или пустой
     */
    public static boolean isNullOrEmpty(String str) {
        return ((str == null) || str.isEmpty());
    }

    /**
     * проверяет, является ли строка null, пустой или состоит из пробельных символов
     * @param str строка
     * @return является ли строка null, пустой или состоит из пробельных символов
     */
    public static boolean isNullOrWhitespace(String str) {
        if (str == null) {
            return true;
        }
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) > '\u0020') {
                return false;
            }
        }
        return true;
    }
}
