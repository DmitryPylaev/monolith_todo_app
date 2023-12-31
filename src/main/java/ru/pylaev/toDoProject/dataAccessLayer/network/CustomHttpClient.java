package ru.pylaev.toDoProject.dataAccessLayer.network;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.pylaev.toDoProject.ToDoMain;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class CustomHttpClient {
    public static String get(String urlString, String[] params, String jsonElementName) {
        var urlResult = new StringBuilder(urlString);
        for (int i = 0; i < params.length; i++) {
            if (i==0) urlResult.append("?");
            else urlResult.append("&");
            urlResult.append(params[i]);
        }
        try {
            var connection = (HttpURLConnection) new URL(urlResult.toString()).openConnection();
            return getRequestString(connection, jsonElementName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(ToDoMain.PROPERTIES.get("networkError"));
            return "";
        }
    }

    public static String post(String urlString, Map<String, String> params, String jsonElementName, Map<String, String> headers) {
        try {
            var connection = (HttpURLConnection) new URL(urlString).openConnection();
            connection.setRequestMethod("POST");
            headers.forEach(connection::setRequestProperty);
            connection.setDoOutput(true);
            var out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(ParameterStringBuilder.getParamsString(params));
            out.flush();
            out.close();
            return getRequestString(connection, jsonElementName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(ToDoMain.PROPERTIES.get("networkError"));
            return "";
        }
    }

    private static String getRequestString(HttpURLConnection connection, String jsonElementName) throws IOException {
        JsonNode parent= new ObjectMapper().readTree((InputStream) connection.getContent());
        return parent.path(jsonElementName).toString();
    }
}
