public class BoundingBox {
    private double x;      // Координата x левого верхнего угла
    private double y;      // Координата y левого верхнего угла
    private double width;  // Ширина прямоугольника
    private double height; // Высота прямоугольника

    // Конструктор для инициализации значений
    public BoundingBox(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Геттеры для значений
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    // Переопределим метод toString, чтобы красиво выводить информацию
    @Override
    public String toString() {
        return "BoundingBox{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
