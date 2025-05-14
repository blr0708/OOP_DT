import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Family {
    ////Создаём пустую карту (HashMap), где храним людей по полному имени.
    //    private Map<String,Person> people = new HashMap<>();

    private Map<String, List<Person>> people = new HashMap<>();

    public void add(Person... peopleToAdd){
        for(Person p : peopleToAdd)
            add(p);
    }
    ////Метод принимает одного человека.
//    //Создаёт ключ "Имя Фамилия". Проверяет: если такой ключ уже есть — ничего не делает.
//    // Если нет — добавляет в карту.

//    public void add(Person personToAdd){
//        String newKey = personToAdd.getFname() + " " + personToAdd.getLname();
//        if (people.containsKey(newKey))
//            return;
//        people.put(newKey, personToAdd);
//    }
//
//    public Person get(String key){
//        return people.get(key);
//    }
    public void add(Person personToAdd){
        String newKey = personToAdd.getFname() + " " + personToAdd.getLname();
        people.putIfAbsent(newKey, new ArrayList<>());
        people.get(newKey).add(personToAdd);
    }

    public Person[] get(String key) {
        List<Person> persons = people.get(key);
        if (persons == null) {
            return new Person[0];
        }
        List<Person> sortedList = new ArrayList<>(persons);
        Collections.sort(sortedList);
        return sortedList.toArray(new Person[0]);
    }
}
