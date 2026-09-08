package ru.nsu.lysakov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MainTest {

    @Test
    void heapsortShouldSortUnorderedArray() {
        Main main = new Main();
        int[] data = {5, 1, 4, 2, 3};
        int[] expected = {1, 2, 3, 4, 5};

        main.heapsort(data);

        assertArrayEquals(expected, data);
    }

    @Test
    void heapsortShouldHandleAlreadySortedArray() {
        Main main = new Main();
        int[] data = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};

        main.heapsort(data);

        assertArrayEquals(expected, data);
    }

    @Test
    void heapsortShouldHandleReverseSortedArray() {
        Main main = new Main();
        int[] data = {9, 7, 5, 3, 1};
        int[] expected = {1, 3, 5, 7, 9};

        main.heapsort(data);

        assertArrayEquals(expected, data);
    }

    @Test
    void heapsortShouldHandleDuplicatesAndNegatives() {
        Main main = new Main();
        int[] data = {0, -1, 5, -1, 3, 3, 2};
        int[] expected = {-1, -1, 0, 2, 3, 3, 5};

        main.heapsort(data);

        assertArrayEquals(expected, data);
    }

    @Test
    void heapsortShouldHandleSingleElementArray() {
        Main main = new Main();
        int[] data = {42};
        int[] expected = {42};

        main.heapsort(data);

        assertArrayEquals(expected, data);
    }

    @Test
    void heapsortShouldHandleEmptyArray() {
        Main main = new Main();
        int[] data = {};
        int[] expected = {};

        main.heapsort(data);

        assertArrayEquals(expected, data);
    }
}