package ru.nsu.lysakov.exception;

/**
 * Исключение для ошибок, связанных с переменными.
 */
public class VariableException extends ExpressionException {
    /**
     * Создает исключение с сообщением.
     *
     * @param message сообщение об ошибке
     */
    public VariableException(String message) {
        super(message);
    }
}
