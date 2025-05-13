public class Segment
{
    public Point start;
    public Point end;
    //конструктор
    public Segment(Point start, Point end) {
        this.start = start;
        this.end = end;
    }
    //Метод length() вычисляет длину отрезка
    public double length() {
        double dx = end.x - start.x;
        double dy = end.y - start.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    //вывод стринг
    @Override
    public String toString() {
        return "Segment{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    // Теперь метод в классе Segment
    public static Segment findLongestSegment(Segment[] segments) {
        if (segments == null || segments.length == 0) {
            return null;
        }

        Segment longest = segments[0];
        for (Segment s : segments) {
            if (s.length() > longest.length()) {
                longest = s;
            }
        }
        return longest;
    }
}
