package ru.nsu.lysakov;

/**
 * Пара ключ-значение и ссылка на следующий узел цепочки.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class Entry<K, V> {
    private final K key;
    private V value;
    private Entry<K, V> next;

    Entry(K key, V value, Entry<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public void setNext(Entry<K, V> next) {
        this.next = next;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Entry<K, V> getNext() {return next;}

    /**
     * Возвращает ключ пары.
     *
     * @return ключ
     */
    public K getKey() {
        return key;
    }

    /**
     * Возвращает значение пары.
     *
     * @return значение
     */
    public V getValue() {
        return value;
    }
}
