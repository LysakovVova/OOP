package ru.nsu.lysakov;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Хеш-таблица с цепочками для разрешения коллизий.
 *
 * <p>Добавление, поиск и удаление работают в среднем за O(1).
 * При большом количестве коллизий время операции может достигать O(n).
 * Размер массива увеличивается при заполнении более чем на 75 процентов
 * и уменьшается при заполнении менее чем на 25 процентов.
 * Допускаются ключ null и значения null. Порядок обхода не определён.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class HashTable<K, V> implements Iterable<Entry<K, V>> {
    private static final int MIN_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Entry<K, V>[] buckets;
    private int size;
    private int modCount;

    /**
     * Создаёт пустую хеш-таблицу.
     */
    @SuppressWarnings("unchecked")
    public HashTable() {
        buckets = (Entry<K, V>[]) new Entry<?, ?>[MIN_CAPACITY];
    }

    /**
     * Добавляет пару или заменяет значение существующего ключа.
     *
     * @param key ключ
     * @param value значение
     * @return предыдущее значение или null, если ключ отсутствовал
     */
    public V add(K key, V value) {
        Entry<K, V> entry = findEntry(key);
        if (entry != null) {
            V oldValue = entry.getValue();
            entry.setValue(value);
            modCount++;
            return oldValue;
        }

        if (size + 1 > buckets.length * LOAD_FACTOR) {
            resize(buckets.length * 2);
        }

        int index = index(key, buckets.length);
        buckets[index] = new Entry<>(key, value, buckets[index]);
        size++;
        modCount++;
        return null;
    }

    /**
     * Находит значение по ключу.
     *
     * @param key ключ
     * @return значение или null, если ключ отсутствует
     */
    public V get(K key) {
        Entry<K, V> entry = findEntry(key);
        return entry == null ? null : entry.getValue();
    }

    /**
     * Обновляет значение существующего ключа.
     *
     * @param key ключ
     * @param value новое значение
     * @return предыдущее значение
     * @throws NoSuchElementException если ключ отсутствует
     */
    public V update(K key, V value) {
        Entry<K, V> entry = findEntry(key);
        if (entry == null) {
            throw new NoSuchElementException("Ключ не найден: " + key);
        }

        V oldValue = entry.getValue();
        entry.setValue(value);
        modCount++;
        return oldValue;
    }

    /**
     * Проверяет наличие ключа, в том числе если его значение равно null.
     *
     * @param key ключ
     * @return true, если ключ есть в таблице
     */
    public boolean containsKey(K key) {
        return findEntry(key) != null;
    }

    /**
     * Удаляет пару по ключу.
     *
     * @param key ключ
     * @return удалённое значение или null, если ключ отсутствует
     */
    public V remove(K key) {
        int index = index(key, buckets.length);
        Entry<K, V> entry = buckets[index];
        Entry<K, V> previous = null;

        while (entry != null) {
            if (Objects.equals(entry.getKey(), key)) {
                if (previous == null) {
                    buckets[index] = entry.getNext();
                } else {
                    previous.setNext(entry.getNext());
                }

                size--;
                modCount++;
                if (buckets.length > MIN_CAPACITY && size < buckets.length / 4) {
                    resize(buckets.length / 2);
                }
                return entry.getValue();
            }

            previous = entry;
            entry = entry.getNext();
        }
        return null;
    }

    /**
     * Удаляет пару, только если совпадают и ключ, и значение.
     *
     * @param key ключ
     * @param value значение
     * @return true, если пара была удалена
     */
    public boolean remove(K key, V value) {
        Entry<K, V> entry = findEntry(key);
        if (entry == null || !Objects.equals(entry.getValue(), value)) {
            return false;
        }
        remove(key);
        return true;
    }

    /**
     * Возвращает число пар в таблице.
     *
     * @return число пар
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли таблица.
     *
     * @return true, если таблица пуста
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Возвращает массив ячеек для обхода итератором внутри пакета.
     */
    Entry<K, V>[] getBuckets() {
        return buckets;
    }

    /**
     * Возвращает счётчик изменений для проверки итератором внутри пакета.
     */
    int getModCount() {
        return modCount;
    }

    /**
     * Вычисляет индекс ячейки, учитывая отрицательные хеш-коды.
     */
    private int index(Object key, int capacity) {
        return Math.floorMod(Objects.hashCode(key), capacity);
    }

    /**
     * Ищет пару в цепочке соответствующей ячейки.
     */
    private Entry<K, V> findEntry(Object key) {
        Entry<K, V> entry = buckets[index(key, buckets.length)];
        while (entry != null) {
            if (Objects.equals(entry.getKey(), key)) {
                return entry;
            }
            entry = entry.getNext();
        }
        return null;
    }

    /**
     * Переносит существующие узлы в массив нового размера.
     */
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Entry<K, V>[] newBuckets = (Entry<K, V>[]) new Entry<?, ?>[capacity];

        for (Entry<K, V> bucket : buckets) {
            Entry<K, V> entry = bucket;
            while (entry != null) {
                Entry<K, V> next = entry.getNext();
                int index = index(entry.getKey(), capacity);
                entry.setNext(newBuckets[index]);
                newBuckets[index] = entry;
                entry = next;
            }
        }
        buckets = newBuckets;
    }

    /**
     * Создаёт итератор по парам ключ-значение.
     *
     * <p>Добавление, удаление или замена значения после создания итератора
     * приводит к ConcurrentModificationException при вызове hasNext или next.
     * Удаление через итератор не поддерживается.
     *
     * @return итератор по парам
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new TableIterator<>(this);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof HashTable<?, ?>)) {
            return false;
        }

        HashTable<?, ?> other = (HashTable<?, ?>) object;
        if (size != other.size) {
            return false;
        }
        for (Entry<K, V> entry : this) {
            Entry<?, ?> otherEntry = other.findEntry(entry.getKey());
            if (otherEntry == null || !Objects.equals(entry.getValue(), otherEntry.getValue())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (Entry<K, V> entry : this) {
            hash += Objects.hashCode(entry.getKey()) ^ Objects.hashCode(entry.getValue());
        }
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        for (Entry<K, V> entry : this) {
            if (result.length() > 1) {
                result.append(", ");
            }
            result.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return result.append("}").toString();
    }

}
