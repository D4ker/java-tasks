package ru.mail.polis.homework.objects;

import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Вам придется реализовать Iterable класс CustomArrayWrapper вместе с методами которые
 * могут возващать итераторы только по четным/нечетным позициям в массиве. Пример с классического
 * итератора можете взять из лекции. Обратите внимание что подсчет четного или нечетного элемента
 * идет с человеческой точки зрения.
 * Пример:
 * дан массив [100, 0 ,100, 0, 100]
 * тогда все элементы со значением 100 имеют нечетную позицию, а элементы = 0 - четную.
 */
public class CustomArrayWrapper implements Iterable<Integer> {

    private final int[] array;          // массив
    private int position;               // следующая позиция куда будет вставлен элемент

    public CustomArrayWrapper(int size) {
        this.array = new int[size];
    }

    public void add(int value) {
        checkIndex(position);
        array[position] = value;
        position++;
    }

    public void edit(int index, int value) {
        checkIndex(index);
        array[index] = value;
    }

    public int get(int index) {
        checkIndex(index);
        return array[index];
    }

    public int size() {
        return array.length;
    }

    /**
     * Реализовать метод:
     * Возврящает обычный итератор.
     *
     * @return default Iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new CustomIterator(0, 1);
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит только четные элементы.
     *
     * @return Iterator for EVEN elements
     */
    public Iterator<Integer> evenIterator() {
        return new CustomIterator(1, 2);
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new CustomIterator(0, 2);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    private class CustomIterator implements Iterator<Integer> {
        private int position;
        private final int shift;
        private final int modCount = CustomArrayWrapper.this.position;

        public CustomIterator(int position, int shift) {
            this.position = position;
            this.shift = shift;
        }

        @Override
        public boolean hasNext() {
            return position < array.length;
        }

        @Override
        public Integer next() {
            if (position >= array.length) {
                throw new NoSuchElementException();
            }
            if (modCount != CustomArrayWrapper.this.position) {
                throw new ConcurrentModificationException();
            }
            int element = array[position];
            position += shift;
            return element;
        }
    }
}