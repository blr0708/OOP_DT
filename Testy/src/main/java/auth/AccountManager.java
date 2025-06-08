package auth;

import database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountManager
{
    private DatabaseConnection dbc;
    public boolean register(String login, String password)
    {
        try {
            PreparedStatement ps = dbc.getConnection().prepareStatement("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
