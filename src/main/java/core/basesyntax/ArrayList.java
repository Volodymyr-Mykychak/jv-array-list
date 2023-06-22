package core.basesyntax;

import java.util.NoSuchElementException;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    /**
     * Створює новий об'єкт ArrayList з вказаною початковою ємністю.
     *
     * @param initialCapacity початкова ємність списку
     * @throws IllegalArgumentException якщо передана недопустима початкова ємність (менше або дорівнює 0)
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException();
        }
        elements = new Object[initialCapacity];
    }

    /**
     * Створює новий об'єкт ArrayList зі стандартною початковою ємністю.
     * Початкова ємність за замовчуванням - 10.
     */
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Додає елемент до кінця списку.
     *
     * @param value елемент, який потрібно додати
     */
    @Override
    public void add(T value) {
        grow(); // Перевіряємо, чи потрібно збільшити розмір масиву
        elements[size] = value; // Додаємо елемент до масиву
        size++; // Збільшуємо розмір списку
    }

    /**
     * Додає елемент на вказану позицію у списку.
     * Інші елементи, що знаходяться праворуч від позиції, зсуваються вправо.
     *
     * @param value елемент, який потрібно додати
     * @param index позиція, на яку потрібно додати елемент
     * @throws IndexOutOfBoundsException якщо вказаний індекс виходить за межі списку
     */
    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value); // Якщо індекс дорівнює поточному розміру списку, то просто додаємо елемент в кінець
            return;
        }
        checkIndex(index); // Перевіряємо валідність індексу
        grow(); // Перевіряємо, чи потрібно збільшити розмір масиву
        // Копіюємо елементи вправо, щоб зробити місце для нового елемента
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = value; // Додаємо елемент на вказану позицію
        size++; // Збільшуємо розмір списку
    }

    /**
     * Додає всі елементи з переданого списку до поточного списку.
     *
     * @param list список, з якого потрібно додати елементи
     */
    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i)); // Додаємо кожен елемент з переданого списку до поточного списку
        }
    }

    /**
     * Повертає елемент зі списку за вказаним індексом.
     *
     * @param index індекс елемента
     * @return елемент зі списку
     * @throws IndexOutOfBoundsException якщо вказаний індекс виходить за межі списку
     */
    @Override
    public T get(int index) {
        checkIndex(index); // Перевіряємо валідність індексу
        return (T) elements[index]; // Повертаємо елемент зі списку
    }

    /**
     * Замінює елемент у списку за вказаним індексом новим значенням.
     *
     * @param value нове значення елемента
     * @param index індекс елемента, який потрібно замінити
     * @throws IndexOutOfBoundsException якщо вказаний індекс виходить за межі списку
     */
    @Override
    public void set(T value, int index) {
        checkIndex(index); // Перевіряємо валідність індексу
        elements[index] = value; // Замінюємо елемент за вказаним індексом на нове значення
    }

    /**
     * Видаляє елемент зі списку за вказаним індексом.
     *
     * @param index індекс елемента, який потрібно видалити
     * @return видалений елемент
     * @throws IndexOutOfBoundsException якщо вказаний індекс виходить за межі списку
     */
    @Override
    public T remove(int index) {
        checkIndex(index); // Перевіряємо валідність індексу
        Object removeElement = elements[index];
        // Запам'ятовуємо видаляємий елемент копіюємо elements  масив елементів, від index+1 це значить, що index при
        // копіюванні вртатиться, копіюємо в той самй elements масив елементів від index який перезатирається до
        // (від кількості елементів size віднімаємо  index  та ще один індекс 1
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        size--; // Зменшуємо розмір списку
        return (T) removeElement; // Повертаємо видалений елемент
    }

    /**
     * Видаляє перший зустрічний елемент зі списку, який дорівнює заданому елементу.
     *
     * @param element елемент, який потрібно видалити
     * @return видалений елемент
     * @throws NoSuchElementException якщо заданий елемент не знайдено в списку
     */
    @Override
    public T remove(T element) {
        for (int i = 0; i < size; i++) {
            if (element == elements[i] || element != null && element.equals(elements[i])) {
                return remove(i); // Якщо знайдено елемент, видаляємо його
            }
        }
        throw new NoSuchElementException("No value was found" + element);
    }

    /**
     * Повертає розмір списку.
     *
     * @return розмір списку
     */
    @Override
    public int size() {
        return size; // Повертаємо розмір списку
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Перевіряє, чи список є порожнім.
     *
     * @return true, якщо список порожній, false - в іншому випадку
     */
    private void grow() {
        if (elements.length == size) {
             // збільшуємо масив на 2
            Object[] newArray = new Object[elements.length + (elements.length >> 1)];
             // копіюємо масив elements від елемент 0 в новий масив newArray збільшений в двічі починаючи з елемента 0 і скільки size
            System.arraycopy(elements, 0, newArray, 0, size);
             // пересетимо масиви
            elements = newArray;
        }
    }

    /**
     * Перевіряє, чи вказаний індекс є в межах списку.
     *
     * @param index індекс, який потрібно перевірити
     * @throws IndexOutOfBoundsException якщо вказаний індекс виходить за межі списку
     */
    private void checkIndex(int index) {
        /**
         * ящко index більше рівне кількості елементів або index менше 0
         */
        if (index >= size || index < 0) {
            //виводимо ArrayListIndexOutOfBoundsException
            throw new ArrayListIndexOutOfBoundsException("The index: " + index
                    + " exceeds the array size: " + size);
        }
    }
}
