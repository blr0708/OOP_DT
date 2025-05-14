public class Ellipse extends Shape {
    private Point center;
    private double radiusX;
    private double radiusY;

    public Ellipse(Point center, double radiusX, double radiusY, Style style) {
        super(style);
        this.center = center;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    @Override
    public String toSvg() {
        String styleStr = style != null ? style.toSvg() : "fill:transparent;stroke:black;stroke-width:1";
        return String.format("<ellipse cx=\"%.2f\" cy=\"%.2f\" rx=\"%.2f\" ry=\"%.2f\" style=%s />",
                center.getX(), center.getY(), radiusX, radiusY, styleStr);
    }
}
