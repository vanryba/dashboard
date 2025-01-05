package com.example.twodemofx.Service;

import com.example.twodemofx.Model.AddEntryDto;
import com.example.twodemofx.Model.EntryItemDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManagerService {

    private static final Logger logger = Logger.getLogger(DatabaseManagerService.class.getName());

    private static final String JDBC_URL = "jdbc:h2:~/test;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    private Connection connection;

    public DatabaseManagerService() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Подключение к базе данных установлено.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при подключении к базе данных", e);
        }
    }

    public void createTable() {
        String createTableSQL = QueryEnum.CREATE_TABLE_LINKS.getTitle();
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSQL);
            System.out.println("Таблица 'links' успешно создана.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при создании таблицы", e);
        }
    }

    public void addLink(AddEntryDto dto) {
        String insertSQL = "INSERT INTO links (name, value_link) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setString(1, dto.getName());
            statement.setString(2, dto.getValueEntry());
            statement.executeUpdate();
            System.out.println("Запись добавлена: " + dto.getName());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при добавлении записи в бд", e);
        }
    }

    public void deleteLink(int linkId) {
        try (PreparedStatement statement = connection.prepareStatement(QueryEnum.DELETE_ENTRY_BY_ID_QUERY.getTitle())) {
            statement.setInt(1, linkId);
            statement.executeUpdate();
            System.out.println("Запись с ID " + linkId + " удалена.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при удалении записи из бд", e);
        }
    }

    public List<String> getValuesByName(String name) {
        List<String> results = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(QueryEnum.GET_VALUES_BY_NAME_QUERY.getTitle())) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                results.add(rs.getString("value_link"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при получении записи по имени", e);
        }
        return results;
    }

    public List<EntryItemDto> getEntryItems() {
        List<EntryItemDto> results = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(QueryEnum.GET_FULL_LINKS.getTitle())) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                results.add(EntryItemDto.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .valueEntry(rs.getString("value_link"))
                        .build());
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при получении записей из бд", e);
        }
        return results;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Соединение с базой данных закрыто.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при закрытии соединения с базой данных", e);
        }
    }
}
