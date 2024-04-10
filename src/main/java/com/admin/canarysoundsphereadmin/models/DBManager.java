package com.admin.canarysoundsphereadmin.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.event.Event;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DBManager {

    public static List<Author> getAllAuthors() throws IOException, JSONException {
        List<Author> authors = new ArrayList<>();
        String apiUrl = "http://localhost:9006/authors"; // URL del endpoint que devuelve todos los autores
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            JSONArray authorsArray = new JSONArray(content.toString());

            for (int i = 0; i < authorsArray.length(); i++) {
                JSONObject authorJson = authorsArray.getJSONObject(i);
                Author author = new Author(
                        authorJson.getString("_id"),
                        authorJson.getString("name"),
                        authorJson.getString("image"),
                        authorJson.getInt("foundation_year"),
                        authorJson.getString("music_type"),
                        authorJson.getString("description"),
                        authorJson.getString("music_list")
                );
                authors.add(author);
            }
        } else {
            System.out.println("Error al conectar con la API: " + status);
        }
        con.disconnect();
        return authors;
    }


    public static List<EventClass> getAllEvents() throws IOException, JSONException {
        List<EventClass> events = new ArrayList<>();
        String apiUrl = "http://localhost:9006/events"; // URL del endpoint que devuelve todos los eventos
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            JSONArray eventsArray = new JSONArray(content.toString());

            for (int i = 0; i < eventsArray.length(); i++) {
                JSONObject eventJson = eventsArray.getJSONObject(i);
                EventClass event = new EventClass(
                        eventJson.getString("_id"),
                        eventJson.getString("logo"),
                        eventJson.getString("image"),
                        eventJson.getString("name"),
                        eventJson.getString("date"),
                        eventJson.getString("time"),
                        eventJson.getInt("capacity"),
                        eventJson.getString("description"),
                        eventJson.getString("direction"),
                        eventJson.getString("marker"),
                        eventJson.getString("ticket_store")
                );
                events.add(event);
            }
        } else {
            System.out.println("Error al conectar con la API: " + status);
        }
        con.disconnect();
        return events;
    }

    public static boolean findAdmin(String name, String password) throws IOException, JSONException {
        String apiUrl = "http://localhost:9006/admins/" + name;
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            JSONObject datos = new JSONObject(content.toString());

            // Obtener la contraseña de la respuesta de la API
            String apiPassword = datos.getString("password");

            // Comparar la contraseña de la API con la contraseña proporcionada como parámetro
            if (password.equals(apiPassword)) {
                return true;
            }

        } else {
            System.out.println("Error al conectar con la API: " + status);
        }
        con.disconnect();
        return false;
    }
}
