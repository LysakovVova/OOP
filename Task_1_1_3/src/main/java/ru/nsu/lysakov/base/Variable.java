package ru.nsu.lysakov.base;

import ru.nsu.lysakov.exception.VariableException;
import ru.nsu.lysakov.operation.ExpressionPriority;

/**
 * Класс, представляющий переменную в математическом выражении.
 *
 * <p>Переменная имеет имя и может содержать заданное числовое значение.
 */
public class Variable extends Expression {

    private Double value;
    private final String name;

    /**
     * Создаёт переменную с заданным именем без начального значения.
     *
     * @param name имя переменной
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Возвращает имя переменной.
     *
     * @return имя переменной
     */
    public String getName() {
        return name;
    }

    /**
     * Создаёт переменную с заданным именем и значением.
     *
     * @param name имя переменной
     * @param value значение переменной
     */
    public Variable(String name, double value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Устанавливает значение переменной.
     *
     * @param value новое значение переменной
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Сравнивает текущую переменную с другим объектом.
     *
     * <p>Переменные считаются равными, если их имена совпадают.
     *
     * @param obj объект для сравнения
     * @return {@code true}, если переменные равны, иначе {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Variable other)) {
            return false;
        }

        return name.equals(other.name);
    }

    /**
     * Упрощает выражение переменной.
     *
     * <p>Переменная уже является простейшим выражением,
     * поэтому возвращается текущий объект.
     *
     * @return текущая переменная
     */
    @Override
    public Expression simplify() {
        return this;
    }

    /**
     * Вычисляет производную переменной.
     *
     * <p>Если производная берётся по этой же переменной,
     * возвращается единица, иначе возвращается ноль.
     *
     * @param varName имя переменной, по которой берётся производная
     * @return производная переменной
     */
    @Override
    public Expression derivative(String varName) {
        if (varName.equals(name)) {
            return new Number(1);
        }

        return new Number(0);
    }

    /**
     * Вычисляет значение переменной.
     *
     * <p>Значение переменной ищется в переданной строке.
     * Если значение не найдено, используется сохранённое значение.
     *
     * @param signification строка со значениями переменных
     * @return значение переменной
     */
    @Override
    public Double evaluate(String signification) throws VariableException {
        int pos = signification.indexOf(name);

        if (pos == -1) {
            return value;
        }

        int equalPos = signification.indexOf('=', pos);

        if (equalPos == -1) {
            return value;
        }

        int endPos = signification.indexOf(';', equalPos);

        String val;

        if (endPos == -1) {
            val = signification.substring(equalPos + 1);
        } else {
            val = signification.substring(equalPos + 1, endPos);
        }

        val = val.trim();

        try {
            value = Double.parseDouble(val);
        } catch (NumberFormatException exception) {
            value = null;
            throw new VariableException(
                    "Некорректное значение переменной " + name + " : " + val
            );
        }
        return value;
    }

    /**
     * Возвращает строковое представление переменной.
     *
     * <p>Если значение переменной не задано, возвращается её имя.
     * Иначе возвращается числовое значение.
     *
     * @return строковое представление переменной
     */
    @Override
    public String toString() {
        if (value == null) {
            return name;
        }

        return value.toString();
    }

    /**
     * Возвращает приоритет переменной.
     *
     * @return приоритет выражения
     */
    @Override
    public int getPriority() {
        return ExpressionPriority.ATOM.getValue();
    }
}