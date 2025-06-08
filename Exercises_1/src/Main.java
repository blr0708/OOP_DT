public class Main
{
    public static void main(String[] args)
    {
//         Point p1 = new Point();
//        System.out.println("Zerowa"+p1+"\n");
//
//        Point p2 = new Point(3.5, 7.2);
//        System.out.println("Custom point: " + p2.toString()+"\n");
//
//        // Testujemy translate
//        p2.translate(2.0, 3.0);  // modyfikuje obiekt p2
//        System.out.println(p2+"\n");   // Teraz p = (5.5, 10.2)
//
//        // Testujemy translated
//        Point p3 = p2.translated(2.0, 3.0);
//        System.out.println("New translated point: " + p3+"\n");
//
//        // Testujemy Segment
//        Segment s1 = new Segment(p1, p2);
//        System.out.println("Segment: " + s1);
//        System.out.println("Segment length: " + s1.length()+"\n");
//
//        System.out.println("SVG: " + p2.toSvg()+"\n");
        // 5

        // Tworzenie punktów
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        Point p3 = new Point(6, 8);
        Point p4 = new Point(10, 10);

        // Tworzenie tablicy segmentów
        Segment[] segments = {
                new Segment(p1, p2), // Отрезок 1
                new Segment(p2, p3), // Отрезок 2
                new Segment(p3, p4)  // Отрезок 3
        };
        // Tutaj będziemy szukać najdłuższego segmentu
        
        // Teraz wywołujemy metodę z klasy Segment
        Segment longest = Segment.findLongestSegment(segments);
        System.out.println("Najdłuższy odcinek: " + longest);
    }
}
