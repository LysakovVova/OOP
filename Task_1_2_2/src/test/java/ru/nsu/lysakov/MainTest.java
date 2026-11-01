package ru.nsu.lysakov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Тесты хеш-таблицы.
 */
class MainTest {
    /**
     * Проверяет создание пустой таблицы.
     */
    @Test
    void newTableShouldBeEmpty() {
        HashTable<String, Integer> table = new HashTable<>();

        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
        assertNull(table.get("missing"));
        assertFalse(table.containsKey("missing"));
        assertNull(table.remove("missing"));
        assertEquals("{}", table.toString());
    }

    /**
     * Проверяет добавление и поиск значений.
     */
    @Test
    void shouldPutAndGetValues() {
        HashTable<String, Integer> table = new HashTable<>();

        assertNull(table.add("one", 1));
        table.add("two", 2);

        assertEquals(1, table.get(new String("one")));
        assertEquals(2, table.get("two"));
        assertTrue(table.containsKey("one"));
        assertFalse(table.isEmpty());
        assertEquals(2, table.size());
    }

    /**
     * Проверяет замену значения при повторном добавлении ключа.
     */
    @Test
    void putShouldReplaceExistingValue() {
        HashTable<String, Integer> table = new HashTable<>();
        table.add("one", 1);

        assertEquals(1, table.add("one", 10));
        assertEquals(10, table.get("one"));
        assertEquals(1, table.size());
    }

    /**
     * Проверяет обновление значения из примера задания.
     */
    @Test
    void shouldUpdateValueAsInExample() {
        HashTable<String, Number> table = new HashTable<>();
        table.add("one", 1);

        assertEquals(1, table.update("one", 1.0));
        assertEquals(1.0, table.get("one"));
        assertEquals(1, table.size());
    }

    /**
     * Проверяет исключение при обновлении отсутствующего ключа.
     */
    @Test
    void updateMissingKeyShouldThrow() {
        HashTable<String, Integer> table = new HashTable<>();

        assertThrows(NoSuchElementException.class, () -> table.update("missing", 1));
        assertTrue(table.isEmpty());
    }

    /**
     * Проверяет удаление по ключу.
     */
    @Test
    void shouldRemoveValue() {
        HashTable<String, Integer> table = new HashTable<>();
        table.add("one", 1);
        table.add("two", 2);

        assertEquals(1, table.remove("one"));
        assertFalse(table.containsKey("one"));
        assertNull(table.remove("one"));
        assertEquals(2, table.get("two"));
        assertEquals(1, table.size());
    }

    /**
     * Проверяет удаление только при совпадении ключа и значения.
     */
    @Test
    void shouldRemoveOnlyMatchingPair() {
        HashTable<String, Integer> table = new HashTable<>();
        table.add("one", 1);

        assertFalse(table.remove("missing", 1));
        assertFalse(table.remove("one", 2));
        assertEquals(1, table.get("one"));
        assertTrue(table.remove("one", 1));
        assertTrue(table.isEmpty());
    }

    /**
     * Проверяет работу с ключами и значениями null.
     */
    @Test
    void shouldSupportNullKeysAndValues() {
        HashTable<String, Integer> table = new HashTable<>();
        table.add(null, 1);
        table.add("empty", null);

        assertEquals(1, table.get(null));
        assertNull(table.get("empty"));
        assertTrue(table.containsKey("empty"));
        assertNull(table.update("empty", 2));
        assertEquals(2, table.get("empty"));
        assertEquals(1, table.update(null, null));
        assertTrue(table.remove(null, null));
        assertFalse(table.containsKey(null));
    }

    /**
     * Проверяет операции с ключами с одинаковым хеш-кодом.
     */
    @Test
    void shouldHandleCollisions() {
        HashTable<String, Integer> table = new HashTable<>();
        table.add("AaAa", 1);
        table.add("BBBB", 2);
        table.add("AaBB", 3);
        table.add("BBAa", 4);

        assertEquals(1, table.get("AaAa"));
        assertEquals(2, table.get("BBBB"));
        assertEquals(3, table.update("AaBB", 30));
        assertEquals(30, table.get("AaBB"));
        assertNull(table.get("missing"));

        assertEquals(2, table.remove("BBBB"));
        assertEquals(4, table.remove("BBAa"));
        assertEquals(1, table.remove("AaAa"));
        assertEquals(30, table.get("AaBB"));
        assertEquals(1, table.size());
    }

    /**
     * Проверяет работу с отрицательными хеш-кодами.
     */
    @Test
    void shouldHandleNegativeHashCodes() {
        HashTable<Integer, String> table = new HashTable<>();
        table.add(-1, "negative");
        table.add(Integer.MIN_VALUE, "minimum");

        assertEquals("negative", table.get(-1));
        assertEquals("minimum", table.get(Integer.MIN_VALUE));
        assertEquals("minimum", table.remove(Integer.MIN_VALUE));
    }

    /**
     * Проверяет сохранение данных при изменении размера массива.
     */
    @Test
    void shouldKeepValuesAfterGrowingAndShrinking() {
        HashTable<Integer, Integer> table = new HashTable<>();
        for (int i = 0; i < 1000; i++) {
            table.add(i, i * 2);
        }
        assertEquals(1000, table.size());
        for (int i = 0; i < 1000; i++) {
            assertEquals(i * 2, table.get(i));
        }

        for (int i = 0; i < 999; i++) {
            assertEquals(i * 2, table.remove(i));
        }
        assertEquals(1, table.size());
        assertEquals(1998, table.get(999));
        table.remove(999);
        assertTrue(table.isEmpty());
        table.add(1, 10);
        assertEquals(10, table.get(1));
    }


    /**
     * Проверяет исключение при достижении конца итератора.
     */
    @Test
    void exhaustedIteratorShouldThrow() {
        HashTable<String, Integer> table = new HashTable<>();
        Iterator<Entry<String, Integer>> empty = table.iterator();
        assertFalse(empty.hasNext());
        assertThrows(NoSuchElementException.class, empty::next);

        table.add("one", 1);
        Iterator<Entry<String, Integer>> iterator = table.iterator();
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        assertEquals("one", iterator.next().getKey());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    /**
     * Проверяет обнаружение добавления во время обхода.
     */
    @Test
    void iteratorShouldDetectInsertion() {
        HashTable<String, Integer> table = new HashTable<>();
        Iterator<Entry<String, Integer>> iterator = table.iterator();
        table.add("one", 1);

        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    /**
     * Проверяет обнаружение удаления во время обхода.
     */
    @Test
    void iteratorShouldDetectRemoval() {
        HashTable<String, Integer> table = new HashTable<>();
        table.add("one", 1);
        Iterator<Entry<String, Integer>> iterator = table.iterator();
        table.remove("one");

        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    /**
     * Проверяет обнаружение замены значения во время обхода.
     */
    @Test
    void iteratorShouldDetectValueReplacement() {
        HashTable<String, Integer> table = new HashTable<>();
        table.add("one", 1);
        Iterator<Entry<String, Integer>> iterator = table.iterator();
        table.update("one", 2);

        assertThrows(ConcurrentModificationException.class, iterator::next);
        Iterator<Entry<String, Integer>> second = table.iterator();
        table.add("one", 3);
        assertThrows(ConcurrentModificationException.class, second::hasNext);
    }

    /**
     * Проверяет работу итератора после чтения и неудачного удаления.
     */
    @Test
    void readAndFailedRemovalShouldNotInvalidateIterator() {
        HashTable<String, Integer> table = new HashTable<>();
        table.add("one", 1);
        Iterator<Entry<String, Integer>> iterator = table.iterator();

        table.get("one");
        table.containsKey("one");
        table.remove("missing");
        table.remove("one", 2);

        assertEquals(1, iterator.next().getValue());
        assertFalse(iterator.hasNext());
    }

    /**
     * Проверяет равенство независимо от порядка добавления и размера массива.
     */
    @Test
    void equalityShouldIgnoreInsertionOrderAndCapacity() {
        HashTable<Integer, Integer> first = new HashTable<>();
        HashTable<Integer, Integer> second = new HashTable<>();
        for (int i = 0; i < 50; i++) {
            first.add(i, i);
        }
        for (int i = 49; i >= 20; i--) {
            second.add(i, i);
        }
        for (int i = 0; i < 20; i++) {
            first.remove(i);
        }

        assertEquals(first, first);
        assertEquals(first, second);
        assertEquals(second, first);
        assertEquals(first.hashCode(), second.hashCode());
        second.update(20, -1);
        assertNotEquals(first, second);
        second.remove(20);
        assertNotEquals(first, second);
        assertNotEquals(first, null);
        assertNotEquals(first, "table");
    }

    /**
     * Проверяет различие отсутствующего ключа и значения null.
     */
    @Test
    void equalityShouldDistinguishMissingKeyFromNullValue() {
        HashTable<String, Integer> first = new HashTable<>();
        HashTable<String, Integer> second = new HashTable<>();
        assertEquals(first, second);
        first.add("Aa", null);
        second.add("BB", null);

        assertNotEquals(first, second);
        second.remove("BB");
        second.add("Aa", null);
        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    /**
     * Проверяет строковое представление таблицы.
     */
    @Test
    void shouldPrintPairs() {
        HashTable<String, Number> table = new HashTable<>();
        table.add("one", 1.0);
        assertEquals("{one=1.0}", table.toString());
        table.add("two", 2);

        String result = table.toString();
        assertTrue(result.startsWith("{"));
        assertTrue(result.endsWith("}"));
        assertTrue(result.contains("one=1.0"));
        assertTrue(result.contains("two=2"));
        assertTrue(result.contains(", "));
    }

}
