public class Point {
    // Приватные поля для хранения координат
    private double x;
    private double y;

    public Point(Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    // Конструктор без аргументов, создающий точку в (0,0)
    public Point() {
        this.x = 0;
        this.y = 0;
    }

    // Конструктор с параметрами, задающий точку в указанном месте
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Геттеры (аксессоры)
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Сеттеры (мутаторы)
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Переопределение метода toString() для удобного вывода
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
