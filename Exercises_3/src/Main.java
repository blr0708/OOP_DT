public class Main {
    public static void main(String[] args) {
        Style style = new Style("yellow", "blue", 2.5);

        Point[] pentagonPoints = {
                new Point(250, 100),
                new Point(300, 150),
                new Point(275, 200),
                new Point(225, 200),
                new Point(200, 150)
        };
        Polygon pentagon = new Polygon(pentagonPoints, style);

        Point ellipseCenter = new Point(150, 150);
        Ellipse ellipse = new Ellipse(ellipseCenter, 50, 30, new Style("red", "black", 1.0));

        SvgScene scene = new SvgScene();
        scene.addShape(pentagon); // пятиугольник
        scene.addShape(ellipse);  // эллипс

        String svgContent = scene.toSvg();
        System.out.println(svgContent);
        scene.save("scene.svg");
    }
}
