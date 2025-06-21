package auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import database.DatabaseConnection;

import javax.naming.AuthenticationException;
import java.sql.*;


public class AccountManager
{
    public int register(String username, String password) {
        String hashed = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        try {
            String sql = "INSERT INTO account(username, password) VALUES (?, ?)";
            PreparedStatement st = DatabaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, username);
            st.setString(2, hashed);
            st.executeUpdate();

            ResultSet keys = st.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1); // zwraca id nowego konta
            }
            throw new SQLException("Nie udało się wygenerować ID");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Account authenticate(String username, String password) throws AuthenticationException {
        try {
            String sql = "SELECT id, username, password FROM account WHERE username = ?";
            PreparedStatement st = DatabaseConnection.getConnection().prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                throw new AuthenticationException("Nie znaleziono użytkownika");
            }

            String hashed = rs.getString("password");
            boolean correct = BCrypt.verifyer()
                    .verify(password.toCharArray(), hashed.toCharArray())
                    .verified;

            if (!correct) {
                throw new AuthenticationException("Niepoprawne hasło");
            }

            return new Account(rs.getInt("id"), rs.getString("username"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void init() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS account (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL)";
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Account getAccount(String username) {
        try {
            String sql = "SELECT id, username FROM account WHERE username = ?";
            PreparedStatement st = DatabaseConnection.getConnection().prepareStatement(sql);
            st.setString(1, username);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt("id"), rs.getString("username"));
            } else {
                return null; // не найден
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Account getAccount(int id) {
        try {
            String sql = "SELECT id, username FROM account WHERE id = ?";
            PreparedStatement st = DatabaseConnection.getConnection().prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt("id"), rs.getString("username"));
            } else {
                return null; // не найден
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
