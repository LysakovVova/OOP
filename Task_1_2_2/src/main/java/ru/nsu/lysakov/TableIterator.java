package ru.nsu.lysakov;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор, который последовательно обходит ячейки и их цепочки.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
class TableIterator<K, V> implements Iterator<Entry<K, V>> {
    private final HashTable<K, V> table;
    private final Entry<K, V>[] buckets;
    private final int expectedModCount;
    private int bucketIndex;
    private Entry<K, V> next;

    TableIterator(HashTable<K, V> table) {
        this.table = table;
        buckets = table.getBuckets();
        expectedModCount = table.getModCount();
        advanceBucket();
    }

    /**
     * Находит следующую непустую ячейку после завершения цепочки.
     */
    private void advanceBucket() {
        while (next == null && bucketIndex < buckets.length) {
            next = buckets[bucketIndex];
            bucketIndex++;
        }
    }

    /**
     * Проверяет, не изменилась ли таблица во время обхода.
     */
    private void checkModification() {
        if (expectedModCount != table.getModCount()) {
            throw new ConcurrentModificationException("Таблица изменена во время обхода");
        }
    }

    @Override
    public boolean hasNext() {
        checkModification();
        return next != null;
    }

    @Override
    public Entry<K, V> next() {
        checkModification();
        if (next == null) {
            throw new NoSuchElementException("Элементы закончились");
        }

        Entry<K, V> result = next;
        next = next.next;
        advanceBucket();
        return result;
    }
}
