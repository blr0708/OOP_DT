import java.time.LocalTime;

public class HourHand extends ClockHand {
    private double angle = 0.0;

    @Override
    public void setTime(LocalTime time) {
        // Полный круг — 12 часов → 360 градусов
        // Угол учитывает часы + минуты + секунды, движение плавное:
        // angle = (часы % 12 + минуты / 60 + секунды / 3600) * 30
        int hours = time.getHour() % 12;
        int minutes = time.getMinute();
        int seconds = time.getSecond();
        angle = (hours + minutes / 60.0 + seconds / 3600.0) * 30.0;
    }

    @Override
    public String toSvg() {
        // Длина стрелки — 50, толщина — 5, цвет — черный
        return String.format(
                "<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"-50\" stroke=\"black\" stroke-width=\"5\" transform=\"rotate(%.2f)\" />",
                angle
        );
    }
}
