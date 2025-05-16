import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AnalogClock extends Clock {

    // Список стрелок: часовая, минутная, секундная
    private final List<ClockHand> hands;

    public AnalogClock(City city) {
        super(city);
        // Создаем список и добавляем стрелки
        hands = new ArrayList<>();
        hands.add(new HourHand());
        hands.add(new MinuteHand());
        hands.add(new SecondHand());

        updateHands(); // Устанавливаем начальное положение стрелок
    }

    // Обновляем положение всех стрелок по текущему времени
    private void updateHands() {
        LocalTime currentTime = LocalTime.of(hour, minute, second);
        for (ClockHand hand : hands) {
            hand.setTime(currentTime);
        }
    }

    // Переопределять setTime и setCurrentTime не нужно,
    // но нужно вызвать updateHands после изменения времени.
    // Решаем это, например, через дополнительные методы:

    @Override
    public void setTime(int hour, int minute, int second) {
        super.setTime(hour, minute, second);
        updateHands();
    }

    @Override
    public void setCurrentTime() {
        super.setCurrentTime();
        updateHands();
    }

    // Метод toSvg — возвращает весь SVG код с циферблатом и стрелками
    public void toSvg(String filePath) throws IOException {
        StringBuilder svg = new StringBuilder();

        svg.append("""
            <svg width="200" height="200" viewBox="-100 -100 200 200" xmlns="http://www.w3.org/2000/svg">
              <circle cx="0" cy="0" r="90" fill="none" stroke="black" stroke-width="2" />
              <g text-anchor="middle">
                <text x="0" y="-80" dy="6">12</text>
                <text x="80" y="0" dy="4">3</text>
                <text x="0" y="80" dy="6">6</text>
                <text x="-80" y="0" dy="4">9</text>
              </g>
        """);

        // Добавляем SVG всех стрелок
        for (ClockHand hand : hands) {
            svg.append(hand.toSvg());
        }

        svg.append("</svg>");

        Files.writeString(Path.of(filePath), svg.toString());
    }
}
