package Cucumber.steps;

import io.cucumber.java.AfterAll;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class FoodSteps {

    private static Connection connection;
    private Statement stmt;
    private ResultSet rs;

    @Дано("сетап")
    public static void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/mem:testdb", "user", "pass");
    }

    @Дано("Я подключился к базе данных")
    public void подключениеКБазеДанных() throws SQLException {
        stmt = connection.createStatement();
    }

    @Когда("Я запрашиваю записи из таблицы FOOD")
    public void запрашиваюЗаписиИзТаблицыFOOD() throws SQLException {
        rs = stmt.executeQuery("SELECT * FROM FOOD");
    }

    @Тогда("Я должен увидеть как минимум одну запись")
    public void долженУвидетьКакМинимумОднуЗапись() throws SQLException {
        assertTrue(rs.next(), "Таблица FOOD не должна быть пустой");
    }

    @Когда("Я добавляю новый товар с ID {int} и названием {string}")
    public void добавляюНовыйТовар(int id, String name) throws SQLException {
        int rowsAffected = stmt.executeUpdate("INSERT INTO FOOD (FOOD_ID, FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC) " +
                "VALUES (" + id + ", '" + name + "', 'FRUIT', FALSE)");
        assertEquals(1, rowsAffected, "Должна быть добавлена одна строка");
    }

    @Тогда("Товар должен быть добавлен в базу данных")
    public void товарДолженБытьДобавлен() throws SQLException {
        rs = stmt.executeQuery("SELECT * FROM FOOD WHERE FOOD_ID = 5");
        assertTrue(rs.next(), "Товар с FOOD_ID = 5 должен существовать");
        assertEquals("Банан", rs.getString("FOOD_NAME"));
        assertEquals("FRUIT", rs.getString("FOOD_TYPE"));
        assertFalse(rs.getBoolean("FOOD_EXOTIC"));
    }

    @Тогда("Я должен смочь получить товар с ID {int}")
    public void долженСмочьПолучитьТовар(int id) throws SQLException {
        rs = stmt.executeQuery("SELECT * FROM FOOD WHERE FOOD_ID = " + id);
        assertTrue(rs.next(), "Товар с FOOD_ID = " + id + " должен существовать");
    }

    // В классе FoodSteps
    @Когда("Я удаляю товар с идентификатором {int}")
    public void удаляюТовар(int id) throws SQLException {
        int rowsAffected = stmt.executeUpdate("DELETE FROM FOOD WHERE FOOD_ID = " + id);
        assertEquals(1, rowsAffected, "Должна быть удалена одна строка");
    }

    // В классе MyStepdefs
    @Когда("Я действительно удаляю товар с ID {int}")
    public void яУдаляюТоварСID(int id) throws SQLException {
        int rowsAffected = stmt.executeUpdate("DELETE FROM FOOD WHERE FOOD_ID = " + id);
        assertEquals(1, rowsAffected, "Должна быть удалена одна строка");
    }


    @Тогда("Товар должен быть удалён из базы данных")
    public void товарДолженБытьУдален() throws SQLException {
        rs = stmt.executeQuery("SELECT * FROM FOOD WHERE FOOD_ID = 5");
        assertFalse(rs.next(), "Товар с FOOD_ID = 5 не должен существовать");
    }

    @Тогда("Я должен увидеть все записи")
    public void долженУвидетьВсеЗаписи() throws SQLException {
        while (rs.next()) {
            System.out.println("FOOD_ID: " + rs.getInt("FOOD_ID") +
                    ", FOOD_NAME: " + rs.getString("FOOD_NAME") +
                    ", FOOD_TYPE: " + rs.getString("FOOD_TYPE") +
                    ", FOOD_EXOTIC: " + rs.getBoolean("FOOD_EXOTIC"));
        }
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Соединение с базой данных закрыто.");
        }
    }
}
