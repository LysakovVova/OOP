package ru.nsu.lysakov;

/// класс для печати состояния игры в консоль
public class Myconsole {
    /// очистка консоли
    void clearConsole() {
        for (int i = 0; i < 100; ++i) {
            System.out.println();
        }
    }

    /// печать состояния игры
    /// @param user
    /// @param diller
    void printState(Gambler user, Gambler diller) {
        System.out.println("- Вы");
        System.out.print("\t");
        user.print();

        System.out.println("\tСумма карт: " + user.getNominal());


        System.out.println("- Диллер");
        System.out.print("\t");
        diller.print();

        System.out.println("\tСумма карт: " + diller.getNominal());
    }
}
