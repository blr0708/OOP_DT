import java.io.FileWriter;
import java.io.IOException;

public class SvgScene {
    private Shape[] shapes = new Shape[3];
    private int index = 0;

    // Метод для добавления фигуры
    public void addShape(Shape shape) {
        shapes[index] = shape;
        index = (index + 1) % 3;
    }

    // Генерация SVG
    public String toSvg() {
        StringBuilder svg = new StringBuilder();
        svg.append("<svg height=\"500\" width=\"500\" xmlns=\"http://www.w3.org/2000/svg\">\n");

        for (Shape s : shapes) {
            if (s != null) {
                svg.append("  ").append(s.toSvg()).append("\n");
            }
        }

        svg.append("</svg>");
        return svg.toString();
    }

    // Сохранение в файл
    public void save(String filePath) {
        String svgContent = toSvg();
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(svgContent);
            System.out.println("SVG файл успешно сохранен как " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
