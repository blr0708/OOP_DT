package music;
import database.DatabaseConnection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListenerAccountTest {
    @Test
    void testRegisterListenerAccount() throws SQLException
    {
        // Шаг 0: Подключаемся к базе данных
        DatabaseConnection.connect("songs.db");

        // Шаг 1: Инициализируем таблицы
        ListenerAccount.Persistence.init();

        // Шаг 2: создаём тестовые логин и пароль
        String username = "test_user_" + System.currentTimeMillis();
        String password = "test123";

        // Шаг 3: Регистрируем нового пользователя
        int id = ListenerAccount.Persistence.register(username, password);

        // Шаг 4: Проверяем, что пользователь есть в таблице account
        PreparedStatement stmt1 = DatabaseConnection.getConnection().prepareStatement(
                "SELECT username FROM account WHERE id = ?"
        );
        stmt1.setInt(1, id);
        ResultSet rs1 = stmt1.executeQuery();
        assertTrue(rs1.next());
        assertEquals(username, rs1.getString("username"));

        // Шаг 5: Проверяем, что пользователь есть в таблице listener_account с 0 кредитов
        PreparedStatement stmt2 = DatabaseConnection.getConnection().prepareStatement(
                "SELECT credits FROM listener_account WHERE id_account = ?"
        );
        stmt2.setInt(1, id);
        ResultSet rs2 = stmt2.executeQuery();
        assertTrue(rs2.next());
        assertEquals(0, rs2.getInt("credits")); // кредиты = 0
    }

    @Test
    void testLoginToListenerAccount() throws Exception {
        // 1. Подключаем базу
        DatabaseConnection.connect("songs.db");

        // 2. Инициализируем таблицы
        ListenerAccount.Persistence.init();

        // 3. Уникальные логин и пароль
        String username = "user_" + System.currentTimeMillis();
        String password = "secret123";

        // 4. Регистрируем нового пользователя и сохраняем его id
        int id = ListenerAccount.Persistence.register(username, password);

        // 5. Выполняем логин с теми же данными
        ListenerAccount account = ListenerAccount.Persistence.authenticate(username, password);

        // 6. Проверки
        assertNotNull(account); // логин прошёл успешно, объект есть
        assertEquals(username, account.getUsername()); // имя совпадает
        assertEquals(id, account.getId());             // id совпадает
    }

    @Test
    void testInitialCreditsIsZero() throws SQLException {
        // Подключаем базу
        DatabaseConnection.connect("songs.db");

        // Инициализируем таблицы
        ListenerAccount.Persistence.init();

        String username = "user_" + System.currentTimeMillis();
        String password = "haslo";

        int id = ListenerAccount.Persistence.register(username, password);

        int credits = ListenerAccount.Persistence.getCredits(id);
        assertEquals(0, credits); // должно быть 0
    }

    @Test
    void testAddingCredits() throws SQLException {
        DatabaseConnection.connect("songs.db");

        // Инициализируем таблицы
        ListenerAccount.Persistence.init();
        String username = "user_" + System.currentTimeMillis();
        String password = "haslo";

        int id = ListenerAccount.Persistence.register(username, password);

        ListenerAccount.Persistence.addCredits(id, 50);
        int credits = ListenerAccount.Persistence.getCredits(id);

        assertEquals(50, credits); // должно быть 50
    }

    @Test
    void testBuySong_allCases() throws Exception {
        DatabaseConnection.connect("songs.db");
        ListenerAccount.Persistence.init();

        String username = "u_" + System.currentTimeMillis();
        String password = "pass";

        int id = ListenerAccount.Persistence.register(username, password);

        // Добавим 1 кредит вручную
        ListenerAccount.Persistence.addCredits(id, 1);

        // 🔹 Попытка купить песню (не куплена, есть кредиты)
        ListenerAccount.Persistence.buySong(id, 1);
        assertTrue(ListenerAccount.Persistence.hasSong(id, 1));
        assertEquals(0, ListenerAccount.Persistence.getCredits(id)); // кредит списан

        // 🔹 Попытка купить ту же песню повторно
        ListenerAccount.Persistence.buySong(id, 1); // должно пройти без ошибки

        // 🔹 Попытка купить новую песню без кредитов
        Exception exception = assertThrows(NotEnoughCreditsException.class, () ->
                ListenerAccount.Persistence.buySong(id, 2)
        );
        assertEquals("Nie masz kredytów!", exception.getMessage());
    }

//    @Test
//    void testCreatePlaylist() throws Exception {
//        DatabaseConnection.connect("songs.db");
//        ListenerAccount.Persistence.init();
//
//        String username = "user_" + System.currentTimeMillis();
//        String password = "pass";
//        int id = ListenerAccount.Persistence.register(username, password);
//
//        // Добавим кредиты и купим песню
//        ListenerAccount.Persistence.addCredits(id, 2);
//        ListenerAccount.Persistence.buySong(id, 1); // Купили 1
//
//        // Попытаемся создать плейлист с 1 и 2
//        List<Integer> songIds = Arrays.asList(1, 2);
//        Playlist playlist = ListenerAccount.Persistence.createPlaylist(id, songIds);
//
//        // Ожидаемый результат
//        Playlist expected = new Playlist();
//        expected.add(SongPersistence.getSongById(1));
//        expected.add(SongPersistence.getSongById(2)); // куплено во время createPlaylist
//
//        assertEquals(expected.toString(), playlist.toString()); // или использовать equals()
//    }

}