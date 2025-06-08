public class BoundingBox {
    private double x;      // Współrzędna x lewego górnego rogu
    private double y;      // Współrzędna y lewego górnego rogu
    private double width;  // Szerokość prostokąta
    private double height; // Wysokość prostokąta

    // Konstruktor do inicjalizacji wartości
    public BoundingBox(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Gettery dla wartości
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

    // Zastąpmy metodę toString, aby ładnie wyświetlić informacje
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
