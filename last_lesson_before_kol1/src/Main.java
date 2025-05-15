import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Путь к CSV-файлу с данными о смертях (замени на свой путь)
        String filePath = "stuff/zgony.csv";

        // Читаем данные из CSV в список объектов DeathCauseStatistic
        ArrayList<DeathCauseStatistic> stats = DeathCauseStatistic.readCsvFile(filePath);

        if (stats == null) {
            System.out.println("Ошибка чтения файла или файл пуст.");
            return;
        }

        // Выведем общее число причин смерти, которые загрузились
        System.out.println("Загружено причин смерти: " + stats.size());

        // Например, выведем ICD код и количество смертей в первой возрастной группе для первых 5 записей
        for (int i = 0; i < Math.min(5, stats.size()); i++) {
            DeathCauseStatistic stat = stats.get(i);
            System.out.println("Причина смерти ICD: " + stat.getIcdCode());
           }

        // Пример использования AgeBracketDeaths для возраста 25
        int age = 25;
        DeathCauseStatistic.AgeBracketDeaths bracket = DeathCauseStatistic.AgeBracketDeaths.getInstance(age);
        System.out.println("Возрастная группа для " + age + " лет начинается с: " + bracket.young);

        // Можно также проверить deathCount в этой группе, если он был заполнен
        System.out.println("Число смертей в возрастной группе: " + bracket.deathCount);
    }
}
