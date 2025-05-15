import java.util.List;

public class Main {
    public static void main(String[] args) {
        CustomList<Object> list = new CustomList<>();
        list.add("hello");
        list.add(123);
        list.add(45.6);
        list.add("world");

        System.out.println("Все элементы:");
        for (Object obj : list) {
            System.out.println(obj);
        }

        System.out.println("Только строки:");
        List<String> strings = CustomList.filterByClass(list, String.class);
        strings.forEach(System.out::println);

        System.out.println("Удаляем первый элемент: " + list.removeFirst());
        System.out.println("Удаляем последний элемент: " + list.removeLast());

        System.out.println("Оставшиеся элементы через stream():");
        list.stream().forEach(System.out::println);
    }
}
