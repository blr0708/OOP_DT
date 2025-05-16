import java.time.LocalTime;

public class SecondHand extends ClockHand {

    private int angle = 0;  // угол в градусах

    @Override
    public void setTime(LocalTime time) {
        // Секундная стрелка делает полный оборот за 60 секунд
        int seconds = time.getSecond();
        angle = (seconds * 360) / 60;  // угол от 0 до 354, шаг 6 градусов
    }

    @Override
    public String toSvg() {
        // Длина стрелки — 80, толщина — 1, цвет — красный
        // Поворот вокруг центра (0,0)
        return String.format(
                "<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"-80\" stroke=\"red\" stroke-width=\"1\" transform=\"rotate(%d)\" />",
                angle
        );
    }
}
