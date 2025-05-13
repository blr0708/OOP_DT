public class Point
{
    public double x;
    public double y;

    //конструкторы
    public Point(){
        this.x = 0;
        this.y = 0;
    }
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    //вывод,стринги
    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
    public String toSvg() {
        return "<circle cx='" + x + "' cy='" + y + "' r='5' fill='black' />";
    }
    //методы
    public void translate(double dx, double dy) {
        this.x += dx;  // x = x + dx
        this.y += dy;  // y = y + dy
    }
    public Point translated(double dx, double dy) {
        return new Point(this.x + dx, this.y + dy);
    }
}
