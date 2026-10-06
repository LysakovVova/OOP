package ru.nsu.lysakov.exception;

/**
 * Базовое исключение для ошибок при работе с выражениями.
 */
public class ExpressionException extends Exception {
    /**
     * Создает исключение с сообщением.
     *
     * @param message сообщение об ошибке
     */
    public ExpressionException(String message) {
        super(message);
    }
}