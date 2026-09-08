package ru.nsu.lysakov;

public class Main {

    /* функция проталкивания элемента вниз
        на вход получает массив, его длинну, и элемент который нудно пропихнуть
        на выходе изменённый массив
        временная сложность O(log n)
        пространственная O(log n)
    */
    void heapify(int[] a, int n, int i) {
        int last = i;

        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && a[l] > a[last]) {
            last = l;
        }
        if (r < n && a[r] > a[last]) {
            last = r;
        }
        if (last != i) {
            int t = a[i];
            a[i] = a[last];
            a[last] = t;

            heapify(a, n, last);
        }
    }

    /* функция сортировки
        на вход получает массив
        на выходе функция сортирует массив в порядке неубывания
        временная сложность O(n * log(n)):
            построение кучи (n)
            n - 1 раз вызываем heapify(log n) = n * log n
        пространственная сложность
            O(log n) вызывается heapify - глубина рекурсии log n
    */

    void heapsort(int[] a) {
        for (int i = a.length / 2 - 1; i >= 0; --i) {
            heapify(a, a.length, i);
        }
        System.out.println();

        int t;
        for (int i = a.length - 1; i > 0; --i) {
            t = a[0];
            a[0] = a[i];
            a[i] = t;

            heapify(a, i, 0);
        }
    }
}