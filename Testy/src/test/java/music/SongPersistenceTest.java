package music;

import com.sun.jdi.connect.Connector;
import database.DatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SongPersistenceTest
{
    @BeforeAll
    static void connectToDatabase() {
        DatabaseConnection.connect("songs.db");
    }

    @AfterAll
    static void disconnectFromDatabase() {
        DatabaseConnection.disconnect(); // если реализован
    }

    @Test
    void testReadSongWithCorrectId()
    {
        Optional<Song> maybeSong = Song.Persistence.read(1);
        // Проверяем, что песня действительно была найдена
        assertTrue(maybeSong.isPresent());

        Song song = maybeSong.get();

        assertEquals("Hey Jude", song.title());         // ✅ название песни
        assertEquals("The Beatles", song.artist());     // ✅ исполнитель
        assertEquals(431, song.duration());             // ✅ длительность
    }

    @Test
    void testReadSongWithIncorrectId() {
        Optional<Song> maybeSong = Song.Persistence.read(9999); // несуществующий ID
        assertTrue(maybeSong.isEmpty()); // Проверяем, что Optional пуст
    }

    //2d
    static Stream<Arguments> provideSongsForTest()
    {
        return Stream.of(
                Arguments.of(1, new Song("Hey Jude", "The Beatles", 431)),
                Arguments.of(2, new Song("(I Can't Get No) Satisfaction", "The Rolling Stones", 224))
        );
    }

    @ParameterizedTest
    @MethodSource("provideSongsForTest")
    void testReadSongParameterized(int id, Song expectedSong)
    {
        Optional<Song> maybeSong = Song.Persistence.read(id);
        assertTrue(maybeSong.isPresent());

        Song actualSong = maybeSong.get();

        assertEquals(expectedSong.title(), actualSong.title());
        assertEquals(expectedSong.artist(), actualSong.artist());
        assertEquals(expectedSong.duration(), actualSong.duration());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "songs.csv", numLinesToSkip = 1)
    void testReadSongFromCsv(int id, String artist, String title, int duration) {
        Optional<Song> maybeSong = Song.Persistence.read(id);
        assertTrue(maybeSong.isPresent());

        Song actual = maybeSong.get();

        assertEquals(title, actual.title());
        assertEquals(artist, actual.artist());
        assertEquals(duration, actual.duration());
    }
}

