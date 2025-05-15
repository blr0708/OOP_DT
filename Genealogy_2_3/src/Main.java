import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Список всех людей
        List<Person> people = new ArrayList<>();

        // Родители
        Person father = new Person("Alojzy", "Czeremcha", LocalDate.of(1903,12,7));
        Person mother = new Person("Janina", "Czeremcha", LocalDate.of(1908,5,22));

        // Дети
        Person child1 = new Person("Grażyna", "Duda", LocalDate.of(1951,4,7));
        Person child2 = new Person("Ksawery", "Czeremcha", LocalDate.of(1955,8,12));
        Person child3 = new Person("Brajan", "Czeremcha", LocalDate.of(1959,2,18));
        Person child4 = new Person("Dżesika", "Mercedes", LocalDate.of(2000,7,11));

        // Добавляем всех в список
        people.add(father);
        people.add(mother);
        people.add(child1);
        people.add(child2);
        people.add(child3);
        people.add(child4);

        // Устанавливаем родственные связи: усыновление
        father.adopt(child1);
        father.adopt(child2);
        father.adopt(child3);
        father.adopt(child4);

        mother.adopt(child1);
        mother.adopt(child2);
        mother.adopt(child3);
        mother.adopt(child4);

        // Устанавливаем мать и отца для каждого ребёнка
        for (Person child : List.of(child1, child2, child3, child4)) {
            child.setFather(father);
            child.setMother(mother);
        }

        // Вывод детей отца
        System.out.println("👨‍👧 Дети " + father.getFname() + ":");
        for (Person child : father.getChildren()) {
            System.out.println("- " + child.getFname() + " " + child.getLname());
        }

        // Сортировка по дате рождения
        System.out.println("\n📅 Сортировка по дате рождения:");
        List<Person> sorted = Person.sortPeopleByBirthYear(people);
        for (Person p : sorted) {
            System.out.println(p.getFname() + " " + p.getLname() + " - " + p.getBirthDate());
        }

        // Фильтрация по ключу "Czeremcha"
        System.out.println("\n🔍 Фильтрация по 'Czeremcha':");
        List<Person> filtered = Person.filterList(people, "Czeremcha");
        for (Person p : filtered) {
            System.out.println(p.getFname() + " " + p.getLname());
        }

        // UML-генерация
        System.out.println("\n📐 UML:");
        String uml = Person.toUMLFile(people);
        System.out.println(uml);

        // Сохранение в бинарный файл
        String binaryPath = "family_data.bin";
        Person.saveToBinaryFile(people, binaryPath);
        System.out.println("\n💾 Данные сохранены в файл: " + binaryPath);

        // Загрузка из файла
        List<Person> loadedPeople = Person.readFromBinaryFile(binaryPath);
        System.out.println("📂 Загружено из файла:");
        for (Person p : loadedPeople) {
            System.out.println(p.getFname() + " " + p.getLname());
        }
    }
}
