import java.io.FileWriter;
import java.io.IOException;
public class SvgScene
{
    private Polygon[] polygons = new Polygon[3]; // Массив из 3 объектов Polygon
    private int index = 0; // Текущая позиция для вставки

    public void addPolygon(Polygon polygon)
    {
        polygons[index] = polygon; // Добавляем в массив
        index = (index + 1) % 3;   // Переключаем индекс по модулю 3
    }

    public void printPolygons() { // Вспомогательный метод для теста
        for (int i = 0; i < polygons.length; i++)
        {
            System.out.println("Polygon " + i + ": " + polygons[i]);
        }
    }

    // Новый метод для генерации полного SVG
    public String toSvg() {
        StringBuilder svg = new StringBuilder();
        svg.append("<svg height=\"500\" width=\"500\" xmlns=\"http://www.w3.org/2000/svg\">\n");

        // Добавляем SVG-код каждого многоугольника
        for (Polygon p : polygons) {
            if (p != null) { // Проверяем, что не пусто
                svg.append("  ").append(p.toSvg()).append("\n");
            }
        }

        svg.append("</svg>"); // Закрываем тег SVG
        return svg.toString();
    }
    //8
    // Метод для сохранения SVG в файл с учетом boundingBox
    public void save(String filePath) {
        // Ищем минимальные и максимальные координаты для всех многоугольников
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;

        for (Polygon polygon : polygons) {
            if (polygon != null) {
                BoundingBox bbox = polygon.boundingBox();
                minX = Math.min(minX, bbox.getX());
                minY = Math.min(minY, bbox.getY());
                maxX = Math.max(maxX, bbox.getX() + bbox.getWidth());
                maxY = Math.max(maxY, bbox.getY() + bbox.getHeight());
            }
        }

        // Ширина и высота SVG
        double width = maxX - minX;
        double height = maxY - minY;

        // Формируем SVG-контент с учетом размеров
        StringBuilder svg = new StringBuilder();
        svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" ")
                .append("width=\"").append(width).append("\" ")
                .append("height=\"").append(height).append("\" ")
                .append("viewBox=\"0 0 ").append(width).append(" ").append(height).append("\">\n");

        // Добавляем каждый многоугольник
        for (Polygon polygon : polygons) {
            if (polygon != null) {
                svg.append("  ").append(polygon.toSvg()).append("\n");
            }
        }

        svg.append("</svg>");

        // Сохраняем SVG в файл
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(svg.toString());
            System.out.println("SVG файл успешно сохранен как " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}