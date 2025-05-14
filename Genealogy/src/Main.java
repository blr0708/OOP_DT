import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Person rodzic = new Person("maks", "liasotski", LocalDate.of(1975, 6, 20));
        Person dziecko = new Person("olalk", "liasotska", LocalDate.of(2024, 6, 20));
        Person dziecko1 = new Person("olalka", "liasotska", LocalDate.of(2025, 6, 20));
        rodzic.adopt(dziecko);
        rodzic.adopt(dziecko1);
        Person youngest = rodzic.getYoungestChild();
        System.out.println(youngest);
        List<Person> children = rodzic.getChildren();
        for(Person child : children)
        {
            System.out.println(child);
        }

        //с этими ключами
        // Создаем Map
        Map<String, Person> people = new HashMap<>();

        // Добавляем Анну в Map
        Person anna = new Person("Anna", "Nowak", LocalDate.of(1990, 1, 1));
        String key = anna.getFname() + " " + anna.getLname();
        people.put(key, anna);

        // Достаём Анну из Map
        Person found = people.get("Anna Nowak");
        System.out.println("Найдена из Map: " + found); // напечатает Анну
    }
}
