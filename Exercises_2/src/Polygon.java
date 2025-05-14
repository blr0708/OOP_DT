import java.util.Arrays;

public class Polygon
{
    //Хранит массив точек, образующих многоугольник.
    private Point[] points;

    //геттер
    public Point[] getPoints() {
        return points; // Возвращает массив точек
    }

    //неправильно
    //public Polygon(Point[] points) {
    //    this.points = points; // Просто копируем ссылку на массив
    //}

    //правильно
    // Конструктор, принимающий массив точек (глубокая копия)
    //чтобы изменения исходного массива не влияли на многоугольник
    public Polygon(Point[] points) {
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            this.points[i] = new Point(points[i]); // Глубокая копия
        }
    }

    //неправильно
    //public Polygon(Polygon other) {
    //    this.points = other.points; // Просто копируем ссылку на массив!
    //}

    //правильно
    // Конструктор копирования (глубокая копия)
    //Опять глубокая копия, чтобы оригинал и копия были независимы
    public Polygon(Polygon other) {
        this.points = new Point[other.points.length];
        for (int i = 0; i < other.points.length; i++) {
            this.points[i] = new Point(other.points[i]); // Копируем точки
        }
    }

    @Override
    public String toString() {
        return "Polygon{" +
                "points=" + Arrays.toString(points) + //Удобный способ вывода массива точек без ручных циклов
                '}';
    }

    // Метод для генерации SVG-представления
    public String toSvg() {
        // Начало SVG
        String beginning = " <svg height=\"220\" width=\"500\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "  <polygon points=\"";
        String middle = ""; // Строка с координатами

        // Формируем строку с точками
        for (Point p : points) {
            middle += p.getX() + "," + p.getY() + " "; // Получаем X и Y каждой точки
        }

        // Конец SVG
        String ending = "\" style=\"fill:lime;stroke:purple;stroke-width:3\" />\n" +
                "</svg>";

        return beginning + middle + ending; // Собираем все части
        //задание 6

    }
    public BoundingBox boundingBox() {
        // Инициализируем переменные для хранения координат
        double minX = points[0].getX();
        double maxX = points[0].getX();
        double minY = points[0].getY();
        double maxY = points[0].getY();

        // Находим минимальные и максимальные значения по осям X и Y
        for (Point p : points) {
            if (p.getX() < minX) minX = p.getX();
            if (p.getX() > maxX) maxX = p.getX();
            if (p.getY() < minY) minY = p.getY();
            if (p.getY() > maxY) maxY = p.getY();
        }

        // Ширина и высота прямоугольника
        double width = maxX - minX;
        double height = maxY - minY;

        // Возвращаем BoundingBox
        return new BoundingBox(minX, minY, width, height);
    }
}

