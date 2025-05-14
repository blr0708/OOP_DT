public class Segment
{
    private Point start;
    private Point end;

    // Конструктор, принимающий две точки
    public Segment(Point start, Point end)
    {
        this.start = start;
        this.end = end;
    }

    // Метод toString
    @Override
    public String toString() {
        return "Segment[" + start + " - " + end + "]";
    }
}
