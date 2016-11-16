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
            builder.append("\n");
            builder.append(iterator.next());
        }
        return builder.toString();
    }
}
