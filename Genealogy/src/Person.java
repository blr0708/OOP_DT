import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

public class Person implements Comparable<Person> {
    private String fname;
    private String lname;
    private LocalDate birthDate;
    private Set<Person> children = new HashSet<>();
    //для comparable
    @Override
    public int compareTo(Person other) {
        return this.birthDate.compareTo(other.birthDate);
    }
    //конструктор
    public Person(String fname, String lname, LocalDate birthDate) {
        this.fname = fname;
        this.lname = lname;
        this.birthDate = birthDate;
    }
    //усыновление
    public boolean adopt(Person child) {
        return children.add(child);
    }
    //наимладший ребенок
    public Person getYoungestChild()
    {
        if(children.isEmpty())
            return null;
        return Collections.max(children);
    }
    // вернуть отсортированный список детей
    public List<Person> getChildren()
    {
        List<Person> sortedChildren = new ArrayList<>(children);
        Collections.sort(sortedChildren);
        return sortedChildren;
    }

    @Override
    public String toString() {
        return fname + " " + lname + " (" + birthDate + ")";
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}