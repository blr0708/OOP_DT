package music;

import auth.Account;
import database.DatabaseConnection;

import javax.naming.AuthenticationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ListenerAccount extends Account {
    public ListenerAccount(int id, String name) {
        super(id, name);
    }

    public static class Persistence {
        public static void init() throws SQLException {
            Account.Persistence.init();
            {
                String sql = "CREATE TABLE IF NOT EXISTS listener_account( " +
                        "id_account INTEGER NOT NULL PRIMARY KEY," +
                        "credits INTEGER NOT NULL)";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                statement.executeUpdate();
            }
            {
                String sql = "CREATE TABLE IF NOT EXISTS owned_songs( " +
                        "id_account INTEGER NOT NULL," +
                        "id_song INTEGER NOT NULL," +
                        "PRIMARY KEY (id_account, id_song))";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                statement.executeUpdate();
            }
        }

        public static int register(String username, String password) throws SQLException{
            try {
                int id = Account.Persistence.register(username, password);
                String sql = "INSERT INTO listener_account(id_account, credits) VALUES (?, 0)";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                statement.setInt(1,id);
                statement.executeUpdate();
                return id;
            } catch (SQLException | RuntimeException e) {
                throw new RuntimeException(e);
            }
        }

        public static int getCredits(int id) throws SQLException {
            String sql = "SELECT credits FROM listener_account WHERE id_account = ?";
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt("credits");
            }
            else throw new SQLException();
        }

        public static void addCredits(int id, int amount) throws SQLException {
            int currentCredits = getCredits(id);
            String sql = "UPDATE listener_account SET credits = ? WHERE id_account = ?";
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setInt(1,currentCredits + amount);
            statement.setInt(2, id);
            statement.executeUpdate();
        }

        public static void addSong(int accountId, int songId) throws SQLException {
            String sql = "INSERT INTO owned_songs VALUES(?, ?)";
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setInt(1, accountId);
            statement.setInt(2, songId);
            statement.executeUpdate();
        }

        public static boolean hasSong(int accountId, int songId) throws SQLException {
            String sql = "SELECT * FROM owned_songs WHERE id_account = ? AND id_song = ?";
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setInt(1, accountId);
            statement.setInt(2, songId);
            return statement.executeQuery().next();
        }

        static ListenerAccount authenticate(String username, String password) throws AuthenticationException {
            Account account = Account.Persistence.authenticate(username, password);
            return new ListenerAccount(account.getId(), account.getUsername());
        }

        public static void buySong(int accountId, int songId) throws SQLException, NotEnoughCreditsException
        {
            if (hasSong(accountId, songId)) {
                return; // уже куплена — ничего не делаем
            }

            int credits = getCredits(accountId);
            if (credits <= 0) {
                throw new NotEnoughCreditsException("Nie masz kredytów!");
            }

            // Добавляем песню и вычитаем кредит
            addSong(accountId, songId);
            addCredits(accountId, -1);
        }

//        public static Playlist createPlaylist(int accountId, List<Integer> songIds) throws SQLException, NotEnoughCreditsException {
//            Playlist playlist = new Playlist();
//            for (int songId : songIds) {
//                if (!hasSong(accountId, songId)) {
//                    buySong(accountId, songId); // тут может быть выброшено исключение
//                }
//                Song song = SongPersistence.getSongById(songId); // метод уже есть
//                playlist.add(song); // метод из Playlist
//            }
//            return playlist;
//        }

    }
}