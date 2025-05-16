public class DigitalClock extends Clock {
    public DigitalClock(City city) {
        super(city); // вызываем конструктор родительского класса Clock
    }

    private int format = 0; // 0 = 24ч, 1 = 12ч

    public void setFormat(int format) {
        if (format != 0 && format != 1)
            throw new IllegalArgumentException("Неверный формат: 0 = 24ч, 1 = 12ч");

        this.format = format;
    }

    @Override
    public String toString() {
        if (format == 0) {
            // 24-часовой формат
            return String.format("%02d:%02d:%02d", hour, minute, second);
        } else {
            // 12-часовой формат с AM/PM
            int displayHour = hour % 12;
            if (displayHour == 0) displayHour = 12;
            String ampm = (hour < 12) ? "AM" : "PM";
            return String.format("%02d:%02d:%02d %s", displayHour, minute, second, ampm);
        }
    }
}
