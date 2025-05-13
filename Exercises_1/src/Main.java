public class Main
{
    public static void main(String[] args)
    {
//         Point p1 = new Point();
//        System.out.println("Нулевое"+p1+"\n");
//
//        Point p2 = new Point(3.5, 7.2);
//        System.out.println("Custom point: " + p2.toString()+"\n");
//
//        // Тестируем translate
//        p2.translate(2.0, 3.0);  // Изменяет объект p2
//        System.out.println(p2+"\n");   // Теперь p = (5.5, 10.2)
//
//        // Тестируем translated
//        Point p3 = p2.translated(2.0, 3.0);
//        System.out.println("New translated point: " + p3+"\n");
//
//        // Тестируем Segment
//        Segment s1 = new Segment(p1, p2);
//        System.out.println("Segment: " + s1);
//        System.out.println("Segment length: " + s1.length()+"\n");
//
//        System.out.println("SVG: " + p2.toSvg()+"\n");
        // пятое

        // Создаём точки
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        Point p3 = new Point(6, 8);
        Point p4 = new Point(10, 10);

        // Создаём массив отрезков
        Segment[] segments = {
                new Segment(p1, p2), // Отрезок 1
                new Segment(p2, p3), // Отрезок 2
                new Segment(p3, p4)  // Отрезок 3
        };
        // Здесь мы будем искать самый длинный отрезок
        
        // Теперь вызываем метод из класса Segment
        Segment longest = Segment.findLongestSegment(segments);
        System.out.println("Самый длинный отрезок: " + longest);
    }
}