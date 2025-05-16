import java.time.LocalTime;

public abstract class ClockHand {

    // Абстрактный метод для установки времени стрелки
    public abstract void setTime(LocalTime time);

    // Абстрактный метод для получения SVG-кода стрелки
    public abstract String toSvg();
}
