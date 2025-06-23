package music;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlaylistTest {
    @Test
    void testPlaylistIsEmptyWhenCreated() {
        Playlist playlist = new Playlist();
        assertTrue(playlist.isEmpty());
    }

    @Test
    void testPlaylistEquals1() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("A shot of vodka", "belorusskaja", 134));//Playlist наследует ArrayList<Song>, а у ArrayList уже есть метод add()
        assertEquals(1, playlist.size());
    }

    @Test
    void testPlaylistContainsExactSong() {
        Playlist playlist = new Playlist();
        Song song = new Song("Artist", "Title", 120);
        playlist.add(song);
        assertSame(song, playlist.get(0));
    }

    @Test
    void testPlaylistContainsEqualSong() {
        Playlist playlist = new Playlist();
        Song song = new Song("Artist", "Title", 120);
        playlist.add(song);
        Song sameSong = new Song("Artist", "Title", 120);
        assertEquals(sameSong, playlist.get(0));
    }

    @Test
    void testAtSecondReturnsCorrectSong(){
        Playlist playlist = new Playlist();
        Song song1 = new Song("Artist", "Title", 120);
        Song song2 = new Song("Artist", "Title", 120);
        playlist.add(song1);
        playlist.add(song2);
        Song correctSong = playlist.atSecond(50);
        assertEquals(correctSong, song1);
    }

    @Test
    void testAtSecondThrowsWhenTooFar() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("Artist1", "Song1", 100));
        assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(200));
        //или IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(200));
        //    assertEquals("Czas przekracza długość playlisty", e.getMessage());
    }

    @Test
    void testAtSecondThrowsWithNegativeTime() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("Artist1", "Song1", 100));
        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(-10));
        assertEquals("Czas nie może być ujemny", e.getMessage());
        // или assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(-10)); просто
    }
}