package ru.pylaev.util;

import ru.pylaev.toDoProject.ToDoMain;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class SQLRequestExecutor {
    public static void executeFromFile(String request) throws URISyntaxException, IOException {
        URI sqlPath = Objects.requireNonNull(SQLRequestExecutor.class.getClassLoader()
                        .getResource(request))
                .toURI();
        List<String> list = Files.readAllLines(Paths.get(sqlPath));
        String sql = String.join("", list);

        try (Connection dbConnection = ConnectionBuilder.getDbConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(ToDoMain.PROPERTIES.get("storageError"));
        }
    }
}
