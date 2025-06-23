package music;

import java.util.ArrayList;

public class Playlist extends ArrayList<Song> {
    public Song atSecond(int second) {
        if (second < 0) {
            throw new IndexOutOfBoundsException("Czas nie może być ujemny");
        }

        int currentTime = 0;
        for (Song song : this) {
            if (second < currentTime + song.duration()) {
                return song;
            }
            currentTime += song.duration();
        }

        throw new IndexOutOfBoundsException("Czas przekracza długość playlisty");
    }
}