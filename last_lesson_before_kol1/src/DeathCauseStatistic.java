import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DeathCauseStatistic {
    // Код причины смерти (например, ICD код)
    private String icdCode;

    // Массив с количеством смертей в каждой возрастной группе
    // Длина массива зависит от количества возрастных групп в CSV
    //здесь можно и List
    private int[] numOfDeathsInGroup;

    // Метод для чтения данных из CSV-файла
    public static ArrayList<DeathCauseStatistic> readCsvFile(String fileName) {
        try {
            ArrayList<DeathCauseStatistic> deathCauseStatistics = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            // Читаем первую строку с заголовками, где указаны возрастные группы
            String line = br.readLine();
            String[] tokens = line.split(",");

            // Парсим возрастные группы из заголовка и создаём соответствующие объекты AgeBracketDeaths
            // Начинаем с индекса 2, т.к. первые колонки — код и описание причины смерти
            for (int i = 2; i < tokens.length; i++) {
                // Убираем пробелы и разбиваем диапазон возраста (например, "20-24") по дефису
                String[] brackets = tokens[i].replace(" ", "").split("-");
                // Левая граница возрастной группы (например, 20)
                int left = Integer.parseInt(brackets[0]);
                // Получаем/создаём объект AgeBracketDeaths для этой возрастной группы
                AgeBracketDeaths.getInstance(left).young = left;
            }

            // Читаем вторую строку с числом смертей в каждой возрастной группе
            line = br.readLine();
            tokens = line.split(",");
            for (int i = 2; i < tokens.length; i++) {
                // Записываем число смертей в соответствующую возрастную группу
                AgeBracketDeaths.getInstance(i).deathCount = Integer.parseInt(tokens[i]);
            }

            // Читаем остальные строки с данными по конкретным причинам смерти
            while ((line = br.readLine()) != null) {
                // Парсим строку в объект DeathCauseStatistic и добавляем в список
                deathCauseStatistics.add(fromCsvLine(line));
            }
            return deathCauseStatistics;

        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // Метод парсинга строки CSV в объект DeathCauseStatistic
    public static DeathCauseStatistic fromCsvLine(String line){
        // Убираем пробелы для корректного парсинга
        String lineWithoutWhiteCharacters = line.replace(" ","");
        String[] lineParts = lineWithoutWhiteCharacters.split(",");

        // Первый элемент — ICD код причины смерти
        String icdCode = lineParts[0];

        // Создаём массив для количества смертей по возрастным группам
        int[] numOfDeathsInGroup = new int[lineParts.length - 2];
        for(int i = 2; i < lineParts.length; i++){
            // Если в CSV стоит "-", считаем количество смертей нулём
            if(lineParts[i].equals("-")){
                numOfDeathsInGroup[i-2] = 0;
            } else {
                // Иначе парсим число смертей
                numOfDeathsInGroup[i-2] = Integer.parseInt(lineParts[i]);
            }
        }
        // Возвращаем новый объект с распарсенными данными
        return new DeathCauseStatistic(icdCode, numOfDeathsInGroup);
    }

    // Конструктор класса
    public DeathCauseStatistic(String icdCode, int[] numOfDeathsInGroup) {
        this.icdCode = icdCode;
        this.numOfDeathsInGroup = numOfDeathsInGroup;
    }

    // Геттер для ICD кода
    public String getIcdCode() {
        return icdCode;
    }

    // Метод для получения AgeBracketDeaths по возрасту
    // Делит возраст на группы по 5 лет (например, 25 -> 25)
    public AgeBracketDeaths getAgeBracketDeaths(int age) {
        age /= 5;
        age *= 5;
        return AgeBracketDeaths.getInstance(age);
    }

    // Вложенный статический класс, представляющий возрастную группу
    public static class AgeBracketDeaths {
        // Левая граница возрастной группы (например, 20 для 20-24)
        int young;

        // Количество смертей в этой возрастной группе
        int deathCount;

        // Массив инстансов AgeBracketDeaths для всех возрастных групп
        private static AgeBracketDeaths[] instances;

        // Метод для получения объекта AgeBracketDeaths по возрасту
        public static AgeBracketDeaths getInstance(int age) {
            int i = age / 5;
            if(instances == null){
                // Создаём массив из 20 элементов (группы по 5 лет)
                instances = new AgeBracketDeaths[20];
            }
            if (instances[i] == null) {
                // Если объект для группы ещё не создан — создаём
                instances[i] = new AgeBracketDeaths(age);
            }
            return instances[i];
        }

        // Конструктор класса, инициализируем левую границу группы
        public AgeBracketDeaths(int young) {
            this.young = young;
        }
    }
}
