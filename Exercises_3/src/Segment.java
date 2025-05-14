public class Segment {
    private Point p1, p2;

    public Segment(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    // Метод для получения перпендикулярного отрезка
    public Segment perpendicular() {
        double midX = (p1.getX() + p2.getX()) / 2;
        double midY = (p1.getY() + p2.getY()) / 2;

        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();

        double length = Math.sqrt(dx * dx + dy * dy) / 3;
        double normX = -dy / Math.sqrt(dx * dx + dy * dy);
        double normY = dx / Math.sqrt(dx * dx + dy * dy);

        Point newP1 = new Point(midX - normX * length, midY - normY * length);
        Point newP2 = new Point(midX + normX * length, midY + normY * length);

        return new Segment(newP1, newP2);
    }

    @Override
    public String toString() {
        return "Segment[" + p1 + " - " + p2 + "]";
    }
}
