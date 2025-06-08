package music;

public record Song(String title, String artist, int duration) {
    @Override
    public String title() {
        return title;
    }

    @Override
    public String artist() {
        return artist;
    }

    @Override
    public int duration() {
        return duration;
    }
}