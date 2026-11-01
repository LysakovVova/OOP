package ru.nsu.lysakov;

/**
 * Главный класс программы.
 */
public class Main {

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        HashTable<String, Number> hashTable = new HashTable<>();
        hashTable.add("one", 1);
        hashTable.update("one", 1.0);
        System.out.println(hashTable.get("one"));

        hashTable.add("two", 2);
        hashTable.add("three", 3);

        System.out.println(hashTable);
    }
}
