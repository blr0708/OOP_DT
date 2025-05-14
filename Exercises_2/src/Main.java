import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SvgScene scene = new SvgScene(); // Создаем сцену

        // Создаем 5 многоугольников
        Point[] p1 = {new Point(10, 10), new Point(60, 10), new Point(35, 50)};
        Point[] p2 = {new Point(100, 100), new Point(150, 100), new Point(125, 150)};
        Point[] p3 = {new Point(200, 200), new Point(250, 200), new Point(225, 250)};
        Point[] p4 = {new Point(300, 300), new Point(350, 300), new Point(325, 350)};
        Point[] p5 = {new Point(400, 400), new Point(450, 400), new Point(425, 450)};

        // Добавляем их в сцену
        scene.addPolygon(new Polygon(p1));
        scene.addPolygon(new Polygon(p2));
        scene.addPolygon(new Polygon(p3));
        scene.printPolygons(); // Должно вывести 3 многоугольника
//
//        System.out.println("Добавляем еще 2...");
//        scene.addPolygon(new Polygon(p4)); // Перезапишет p1
//        scene.addPolygon(new Polygon(p5)); // Перезапишет p2
//        scene.printPolygons(); // Должны остаться p3, p4, p5

        String svgContent = scene.toSvg();
        System.out.println(svgContent); // Выводим SVG-контент на экран

        // Записываем SVG в файл
        try (FileWriter writer = new FileWriter("scene_with_triangles.svg")) {
            writer.write(svgContent);
            System.out.println("SVG файл успешно сохранен как scene_with_triangles.svg!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//7 8
//public class Main {
//    public static void main(String[] args) {
//        SvgScene scene = new SvgScene(); // Создаем сцену
//
//        // Создаем 5 многоугольников
//        Point[] p1 = {new Point(10, 10), new Point(60, 10), new Point(35, 50)};
//        Point[] p2 = {new Point(100, 100), new Point(150, 100), new Point(125, 150)};
//        Point[] p3 = {new Point(200, 200), new Point(250, 200), new Point(225, 250)};
//        Point[] p4 = {new Point(300, 300), new Point(350, 300), new Point(325, 350)};
//        Point[] p5 = {new Point(400, 400), new Point(450, 400), new Point(425, 450)};
//
//        // Добавляем их в сцену
//        scene.addPolygon(new Polygon(p1));
//        scene.addPolygon(new Polygon(p2));
//        scene.addPolygon(new Polygon(p3));
//
//        // Сохраняем в файл
//        scene.save("scene_with_bounding_box.svg");
//    }
//}