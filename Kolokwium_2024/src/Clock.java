import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class Clock
{
    protected int hour;
    protected int minute;
    protected int second;
    private City city; // ← новое поле

    public Clock(City city) {
        this.city = city;
        setCurrentTime();
    }

    // Устанавливает текущее системное время
    public void setCurrentTime() {
        LocalTime now = LocalTime.now().plusHours(city.getSummerTimezone());
        this.hour = now.getHour();
        this.minute = now.getMinute();
        this.second = now.getSecond();
    }

    public void setCity(City newCity) {
        // Вычисляем разницу между часовыми поясами
        int offset = newCity.getSummerTimezone() - this.city.getSummerTimezone();

        // Сдвигаем время с учетом разницы в часовых поясах
        this.hour = (this.hour + offset) % 24;
        if (this.hour < 0) this.hour += 24; // Чтобы не было отрицательных часов

        // Обновляем ссылку на новый город
        this.city = newCity;
    }

    public void setTime(int hour, int minute, int second) {
        if (hour < 0 || hour > 23)
            throw new IllegalArgumentException("Часы должны быть в диапазоне 0-23, передано: " + hour);
        if (minute < 0 || minute > 59)
            throw new IllegalArgumentException("Минуты должны быть в диапазоне 0-59, передано: " + minute);
        if (second < 0 || second > 59)
            throw new IllegalArgumentException("Секунды должны быть в диапазоне 0-59, передано: " + second);

        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    // Возвращает строку времени в формате hh:mm:ss
    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

}
