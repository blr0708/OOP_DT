package org.example;

import auth.Account;
import auth.AccountManager;
import database.DatabaseConnection;

import javax.naming.AuthenticationException;

public class Main {
    public static void main(String[] args) {
        // 1. Подключаемся к БД
        DatabaseConnection db = new DatabaseConnection();
        db.connect("test.db");

        // 2. Создаём менеджер и таблицу
        AccountManager manager = new AccountManager();
        manager.init();

        // 3. Регистрируем пользователя
        int userId = manager.register("janek", "tajnehaslo");
        System.out.println("Zarejestrowano: ID = " + userId);

        // 4. Пробуем залогиниться
        try {
            Account acc = manager.authenticate("janek", "tajnehaslo");
            System.out.println("Zalogowano jako: " + acc.username());
        } catch (AuthenticationException e) {
            System.out.println("Nie udało się zalogować: " + e.getMessage());
        }

        // 5. Получаем пользователя по логину
        Account foundByName = manager.getAccount("janek");
        if (foundByName != null) {
            System.out.println("Znaleziono po loginie: " + foundByName);
        }

        // 6. Получаем пользователя по ID
        Account foundById = manager.getAccount(userId);
        if (foundById != null) {
            System.out.println("Znaleziono po ID: " + foundById);
        }

        // 7. Отключаемся от базы
        db.disconnect();
    }
}
