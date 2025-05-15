import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CustomList<T> extends AbstractList<T> {
    // Вершина связного списка (первый элемент)
    private Node<T> head;

    // Хвост связного списка (последний элемент)
    private Node<T> tail;

    // Количество элементов в списке
    private int size = 0;

    @Override
    public T get(int index) {
        // Возвращает элемент по индексу (0-based)
        // Если список пуст — возвращает null
        if (size == 0){
            return null;
        }
        // Проверка на выход за границы списка (должно быть: index < 0 || index >= size)
        if (index >= size || index < 0){
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        // Идем по списку по ссылкам next до нужного индекса
        for (int i = 0; i < index; i++){
            current = current.next;
        }
        return current.value;
    }

    @Override
    public int size() {
        // Возвращает текущее количество элементов
        return size;
    }

    // Вложенный приватный класс — элемент (узел) списка
    private static class Node<T>{
        T value;       // значение элемента
        Node<T> next;  // ссылка на следующий элемент

        Node(T value){
            this.value = value;
            this.next = null;
        }
    }

    // Добавить элемент в конец списка
    public void addLast(T value){
        Node<T> newNode = new Node<T>(value);
        if (head == null){
            // Если список пуст — новый элемент и голова, и хвост
            head = tail = newNode;
        } else {
            // Если нет — прикрепляем новый элемент в конец и обновляем хвост
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    // Добавить элемент (реализация абстрактного метода AbstractList)
    public boolean add(T value){
        int prevSize = size;
        addLast(value);   // добавляем в конец
        return size > prevSize;
    }

    // Добавить элемент в начало списка
    public void addFirst(T value){
        Node<T> newNode = new Node<T>(value);
        if (head == null){
            head = tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    // Получить последний элемент списка (без удаления)
    public T getLast(){
        if (head != null)
            return tail.value;
        return null;
    }

    // Получить первый элемент списка (без удаления)
    public T getFirst(){
        if (head != null)
            return head.value;
        return null;
    }

    // Удалить и вернуть первый элемент
    public T removeFirst(){
        if (head != null){
            T valueToReturn = head.value;
            head = head.next;
            if (head == null){
                // Если список стал пустым, обновляем хвост
                tail = null;
            }
            size--;
            return valueToReturn;
        }
        return null;
    }

    // Удалить и вернуть последний элемент
    public T removeLast(){
        if (tail != null){
            T valueToReturn = tail.value;
            if (head == tail){
                // Если в списке один элемент
                head = tail = null;
                size--;
                return valueToReturn;
            }
            // Ищем предпоследний элемент
            Node<T> currentElement = head;
            while (currentElement.next != tail){
                currentElement = currentElement.next;
            }
            tail = currentElement;
            tail.next = null;
            size--;
            return valueToReturn;
        }
        return null;
    }

    // Итератор для прохождения по списку
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }

    // Позволяет получить Stream из элементов списка
    @Override
    public Stream<T> stream() {
        Spliterator<T> spliterator = Spliterators.spliterator(iterator(), size(), 0);
        return StreamSupport.stream(spliterator, false);
    }

    // Функция, которая фильтрует список, оставляя только элементы указанного класса
    public static <T> List<T> filterByClass(List<?> list, Class<T> clazz) {
        return list.stream()
                .filter(clazz::isInstance)   // проверяет, что элемент является экземпляром clazz
                .map(clazz::cast)            // приводит элемент к типу T
                .collect(Collectors.toList());
    }
}
