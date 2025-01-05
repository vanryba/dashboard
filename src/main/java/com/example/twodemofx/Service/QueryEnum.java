package com.example.twodemofx.Service;

public enum QueryEnum {
    CREATE_TABLE_LINKS("CREATE TABLE IF NOT EXISTS links " +
            "(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), value_link VARCHAR(200))"),
    GET_FULL_LINKS("SELECT id, name, value_link FROM links"),
    GET_VALUES_BY_NAME_QUERY("SELECT value_link FROM links WHERE name = ?"),
    DELETE_ENTRY_BY_ID_QUERY("DELETE FROM links WHERE id = ?");

    private final String title;

    QueryEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
