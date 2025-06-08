import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Тестирование шагов 10 и 11 с чтением из файлов ---");

        // Определяем пути к файлам
        String candidatesFilePath = "src/kandydaci.txt";
        String resultsFilePath = "src/1.csv";

        try {
            // --- ЭТАП 1: Подготовка данных ---

            // 1. Читаем файл с кандидатами.
            // Используем Stream API для краткости, это эквивалентно циклу for.
            List<Candidate> allCandidates = Files.lines(Paths.get(candidatesFilePath))
                    .map(Candidate::new) // Каждую строку превращаем в Candidate
                    .collect(Collectors.toList());
            System.out.println("Прочитано " + allCandidates.size() + " кандидатов из файла: " + candidatesFilePath);

            // 2. Читаем файл с результатами и берем только ПЕРВУЮ строку с данными.
            // Files.lines(path).skip(1) - открываем файл, пропускаем заголовок.
            // .findFirst() - берем следующий элемент (первую строку с данными).
            // .orElse(null) - если файл пуст, то firstDataLine будет null.
            String firstDataLine = Files.lines(Paths.get(resultsFilePath))
                    .skip(1)
                    .findFirst()
                    .orElse(null);

            if (firstDataLine == null) {
                System.err.println("Файл с результатами пуст или содержит только заголовок!");
                return; // Прекращаем выполнение, если нет данных для теста
            }
            System.out.println("Взята для теста первая строка данных из файла: " + resultsFilePath);


            // --- ЭТАП 2: Создание объекта и тестирование ---

            // 3. Используем наш метод из шага 6, чтобы создать объект Vote.
            Vote testVoteObject = Vote.fromCsvLine(firstDataLine, allCandidates);

            // 4. ВЫЗОВ, КОТОРЫЙ ТЕСТИРУЕТ ВСЁ (шаги 10 и 11)
            System.out.println("\n--- Результат вызова testVoteObject.toString() ---");
            System.out.println(testVoteObject);

            // 5. Проверяем, что кэш сработал (сообщение о подсчете было только один раз).
            System.out.println("Тест оптимизации шага 11 пройден успешно, если сообщение 'СЧИТАЮ ОБЩУЮ СУММУ' появилось только один раз.");


        } catch (IOException e) {
            // Если возникла проблема с чтением любого из файлов, мы увидим ошибку.
            System.err.println("Ошибка при чтении тестовых данных из файла: " + e.getMessage());
        }
    }
}