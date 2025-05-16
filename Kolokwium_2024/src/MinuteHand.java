import java.time.LocalTime;

public class MinuteHand extends ClockHand {
    private double angle = 0.0;

    @Override
    public void setTime(LocalTime time) {
        // Полный круг — 60 минут → 360 градусов
        // Плавное движение учитывает секунды: угол = (минуты + секунды/60) * 6
        int minutes = time.getMinute();
        int seconds = time.getSecond();
        angle = (minutes + seconds / 60.0) * 6.0;
    }

    @Override
    public String toSvg() {
        // Длина стрелки — 70, толщина — 3, цвет — черный
        return String.format(
                "<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"-70\" stroke=\"black\" stroke-width=\"3\" transform=\"rotate(%.2f)\" />",
                angle
        );
    }
}
