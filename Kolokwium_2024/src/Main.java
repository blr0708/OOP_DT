import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        City lublin = new City("Lublin", 2, 51.25, 22.5667);
        DigitalClock clock  = new DigitalClock(lublin);
        clock.setFormat(1);
        clock.setTime(15,12,13);
        System.out.println(clock);

        City kijow = new City("Kijow", 3, 50.0833, 19.9167); // примерные координаты
        clock.setCity(kijow);
        System.out.println(clock);
        //5 шаг
        LocalTime zonedTime = LocalTime.of(12, 0, 0);
        LocalTime localTime = lublin.localMeanTime(zonedTime);
        System.out.println("Zoned time: " + zonedTime);       // 12:00
        System.out.println("Local mean time: " + localTime);  // Ожидается 13:30:16

        //C компоратором
        List<City> cityList = new ArrayList<>();
        // Добавляем города вручную
        cityList.add(lublin);
        cityList.add(kijow);
        cityList.add(new City("Warszawa", 2, 52.23, 21.01));
        // Теперь можем сортировать
        cityList.sort(City.worstTimezoneFit());
            // И вывести
        for (City city : cityList) {
            System.out.println(city.getName());
        }

        //7, с циферблатом
        try {
            // Создаём аналоговые часы с этим городом
            AnalogClock analogClock = new AnalogClock(lublin);

            // Записываем SVG циферблат в файл
            analogClock.toSvg("analog_clock.svg");

            System.out.println("SVG-файл с циферблатом успешно создан: analog_clock.svg");

        } catch (IOException e) {
            System.err.println("Ошибка записи SVG файла: " + e.getMessage());
        }

//        //8-10
//        try {
//            List<City> cities = new ArrayList<>();
//            cities.add(new City("Lublin", 2, 51.25, 22.5667));
//            cities.add(new City("Kijow", 3, 50.08, 19.92));
//            // dodaj więcej miast lub wczytaj z pliku
//
//            AnalogClock clock = new AnalogClock(cities.get(0)); // start z pierwszym miastem
//
//            City.generateAnalogClocksSvg(cities, clock);
//
//            System.out.println("Pliki SVG zostały wygenerowane.");
//        } catch (IOException e) {
//            System.err.println("Błąd: " + e.getMessage());
//        }
    }
}


