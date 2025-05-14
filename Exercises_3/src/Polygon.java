public class Polygon extends Shape{
    private Point[] points;

    // Конструктор, принимающий массив точек и стиль
    public Polygon(Point[] points, Style style) {

        super(style); // передаём стиль в родительский класс Shape

        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            this.points[i] = new Point(points[i].getX(), points[i].getY());
        }
    }

    // Метод для проверки, является ли многоугольник квадратом
    public boolean square() {
        if (points.length != 4) {
            return false;
        }

        double d1 = points[0].distance(points[1]);
        double d2 = points[1].distance(points[2]);
        double d3 = points[2].distance(points[3]);
        double d4 = points[3].distance(points[0]);

        double diag1 = points[0].distance(points[2]);
        double diag2 = points[1].distance(points[3]);

        return (d1 == d2 && d2 == d3 && d3 == d4 && diag1 == diag2);
    }

    // Метод для генерации SVG представления многоугольника
    public String toSvg() {
        StringBuilder pointsStr = new StringBuilder();
        for (Point p : points) {
            pointsStr.append(p.getX()).append(",").append(p.getY()).append(" ");
        }

        // Если стиль не задан, используем дефолтный стиль
        String styleStr = style != null ? style.toSvg() : "fill:transparent;stroke:black;stroke-width:1";

        return String.format("<polygon points=\"%s\" style=\"%s\" />", pointsStr.toString().trim(), styleStr);
    }
}
