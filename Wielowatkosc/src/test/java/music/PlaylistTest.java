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
    void testPlaylistSizeAfterAddingOneSong() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("Artist", "Title", 120));
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
    void testAtSecondReturnsCorrectSong() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("Artist1", "Song1", 100));
        playlist.add(new Song("Artist2", "Song2", 200));
        Song song = playlist.atSecond(150);
        assertEquals("Song2", song.title());
    }

    @Test
    void testAtSecondThrowsWhenTooFar() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("Artist1", "Song1", 100));
        assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(200));
    }

    @Test
    void testAtSecondThrowsWithNegativeTime() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("Artist1", "Song1", 100));
        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(-10));
        assertEquals("Czas nie może być ujemny", e.getMessage());
    }

    @Test
    void testAtSecondThrowsWithTooLargeTimeMessage() {
        Playlist playlist = new Playlist();
        playlist.add(new Song("Artist1", "Song1", 100));
        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(200));
        assertEquals("Czas przekracza długość playlisty", e.getMessage());
    }
}