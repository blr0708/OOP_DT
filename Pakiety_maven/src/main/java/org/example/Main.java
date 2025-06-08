package org.example;

import database.DatabaseConnection;

public class Main
{
    public static void main(String[] args)
    {
        String url = "university.sqlite";
        DatabaseConnection dbc = new DatabaseConnection();
        dbc.connect(url);
    }
}