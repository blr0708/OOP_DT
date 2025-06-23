package music;

import database.DatabaseConnection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.sql.ResultSet;

public record Song(String title, String artist, int duration)
{
//    @Override
//    public String title() {
//        return title;
//    }
//
//    @Override
//    public String artist() {
//        return artist;
//    }
//
//    @Override
//    public int duration() {
//        return duration;
//    }
    public static class Persistence
    {
        public static Optional<Song> read(int id)
        {
            try
            {
                String sql = "SELECT artist, title, length FROM song WHERE id = ?";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    String artist = result.getString("artist");
                    String title = result.getString("title");
                    int duration = result.getInt("length");

                    Song song = new Song(title, artist, duration);
                    return Optional.of(song); // Песня найдена
                } else {
                    return Optional.empty(); // Нет такой песни
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



    }
















}