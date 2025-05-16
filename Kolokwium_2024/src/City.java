import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.time.LocalTime;
import java.util.Comparator;
import java.nio.file.Files;
import java.nio.file.Path;
public class City
{
    // Поля объекта City: имя, часовой пояс, широта, долгота
    private String name;
    private int summerTimezone;
    private double latitude;
    private double longitude;
    // Конструктор — используется для создания объекта City
    public City(String name, int summerTimezone, double latitude, double longitude) {
        this.name = name;
        this.summerTimezone = summerTimezone;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    // Геттеры — методы, чтобы получить значения полей
    public String getName() { return name; }
    public int getSummerTimezone() { return summerTimezone; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }




    // parseCoordinate — преобразует строку вида "52.2297 N" → число
    private static double parseCoordinate(String coordinate) {
        String[] parts = coordinate.trim().split(" "); // Разделяем на число и направление
        double value = Double.parseDouble(parts[0]);    // Преобразуем строку в число
        String direction = parts[1];                    // Сохраняем направление (N, S, E, W)

        // Если юг (S) или запад (W), делаем координату отрицательной
        if (direction.equals("S") || direction.equals("W")) {
            value = -value;
        }

        return value;
    }

    // parseLine — создаёт объект City из строки в файле
    private static City parseLine(String line) {
        String[] parts = line.split(","); // Разделяем строку по запятым

        // Убираем пробелы и извлекаем данные
        String name = parts[0].trim();
        int timezone = Integer.parseInt(parts[1].trim());
        double latitude = parseCoordinate(parts[2].trim());
        double longitude = parseCoordinate(parts[3].trim());

        return new City(name, timezone, latitude, longitude); // Возвращаем новый объект City
    }

    // parseFile — читает все строки из файла и создаёт Map<название города, City>
    public static Map<String, City> parseFile(String filePath) throws IOException {
        Map<String, City> cities = new HashMap<>();

        // Читаем все строки из файла в список
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        for (String line : lines) {
            // Пропускаем пустые строки
            if (!line.trim().isEmpty()) {
                // Создаём объект City и добавляем его в карту
                City city = parseLine(line);
                cities.put(city.getName(), city);
            }
        }
        return cities; // Возвращаем карту
    }

    // ⭐ Шаг 5 — метод для расчета среднего местного времени по долготе
    public LocalTime localMeanTime(LocalTime zonedTime) {
        // 1 час = 15 градусов => 1 градус = 4 минуты = 240 секунд
        double offsetInHours = longitude / 15.0;
        int offsetInSeconds = (int) (offsetInHours * 3600);
        return zonedTime.plusSeconds(offsetInSeconds);
    }
    //6
    public static Comparator<City> worstTimezoneFit() {
        return new Comparator<City>() {
            @Override
            public int compare(City city1, City city2) {
                LocalTime baseTime = LocalTime.of(12, 0, 0); // базовое время — 12:00

                // Считаем отклонение у первого города
                long diff1 = Math.abs(city1.localMeanTime(baseTime).toSecondOfDay() - baseTime.toSecondOfDay());

                // Считаем отклонение у второго города
                long diff2 = Math.abs(city2.localMeanTime(baseTime).toSecondOfDay() - baseTime.toSecondOfDay());

                // Чем больше отклонение, тем раньше в списке, поэтому сравниваем по убыванию
                return Long.compare(diff2, diff1);
            }
        };
    }
    //
    public static void generateAnalogClocksSvg(List<City> cities, AnalogClock clock) throws IOException {
        String folderName = clock.toString();
        Path folderPath = Path.of(folderName);

        // Tworzymy katalog, jeśli nie istnieje
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        for (City city : cities) {
            // Ustawiamy miasto w zegarze
            clock.setCity(city);

            // Generujemy nazwę pliku: NazwaMiasta.svg
            String fileName = city.getName() + ".svg";
            Path filePath = folderPath.resolve(fileName);

            // Zapisujemy SVG do pliku
            clock.toSvg(filePath.toString());
        }
    }

}
