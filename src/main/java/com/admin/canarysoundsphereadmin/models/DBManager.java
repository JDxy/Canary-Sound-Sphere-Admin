package com.admin.canarysoundsphereadmin.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

    public static boolean insertEvent(EventClass event) {
        try {
            // Convertir el objeto EventClass a JSON
            JSONObject eventJson = new JSONObject();
            eventJson.put("_id", event.get_id());
            eventJson.put("logo", event.getLogo());
            eventJson.put("image", event.getImage());
            eventJson.put("name", event.getName());
            eventJson.put("date", event.getDate());
            eventJson.put("time", event.getTime());
            eventJson.put("capacity", event.getCapacity());
            eventJson.put("description", event.getDescription());
            eventJson.put("direction", event.getDirection());
            eventJson.put("marker", event.getMarker());
            eventJson.put("ticket_store", event.getTicket_store());

            // Enviar la solicitud HTTP POST al endpoint correspondiente
            String apiUrl = "http://localhost:9006/events/add_event"; // URL del endpoint para insertar eventos
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = eventJson.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int status = con.getResponseCode();
            System.out.println(status);
            return true;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            System.out.println("Error al insertar el evento.");
            return false;
        }
    }

    public static boolean insertAuthor(Author author) {
        try {
            // Convertir el objeto Author a JSON
            JSONObject authorJson = new JSONObject();
            authorJson.put("_id", author.get_id());
            authorJson.put("name", author.getName());
            authorJson.put("image", author.getImage());
            authorJson.put("foundation_year", author.getFoundation_year());
            authorJson.put("music_type", author.getMusic_type());
            authorJson.put("description", author.getDescription());
            authorJson.put("music_list", author.getMusic_list());

            // Enviar la solicitud HTTP POST al endpoint correspondiente
            String apiUrl = "http://localhost:9006/authors/add_author"; // URL del endpoint para insertar autores
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = authorJson.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                System.out.println("Autor insertado exitosamente.");
                return true;
            } else {
                System.out.println("Error al conectar con la API: " + status);
                return false;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            System.out.println("Error al insertar el autor.");
            return false;
        }
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

    public static String idEventPlusOne(){
        try {
            List<EventClass> events = DBManager.getAllEvents();

            int maxId = 0;
            for (EventClass event : events) {
                int eventId = Integer.parseInt(event.get_id());
                if (eventId > maxId) {
                    maxId = eventId;
                }
            }

            maxId++;

            String newEventId = String.format("%04d", maxId);
            return newEventId;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            System.out.println("Error al obtener los eventos desde la base de datos.");
        }
        return "0001";
    }

    public static String idAuthorPlusOne(){
        try {
            List<Author> authors = DBManager.getAllAuthors();

            int maxId = 0;
            for (Author author : authors) {
                int authorId = Integer.parseInt(author.get_id());
                if (authorId > maxId) {
                    maxId = authorId;
                }
            }

            maxId++;

            String newAuthorId = String.format("%04d", maxId);
            return newAuthorId;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            System.out.println("Error al obtener los eventos desde la base de datos.");
        }
        return "0001";
    }

    public static boolean deleteEventById(String eventId) {
        try {
            String apiUrl = "http://localhost:9006/events/delete/" + eventId;
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");

            int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                System.out.println("Evento eliminado exitosamente.");
                return true;
            } else {
                System.out.println("Error al conectar con la API: " + status);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el evento.");
            return false;
        }
    }

    public static boolean deleteAuthorById(String authorId) {
        try {
            String apiUrl = "http://localhost:9006/authors/delete/" + authorId;
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");

            int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                System.out.println("Evento eliminado exitosamente.");
                return true;
            } else {
                System.out.println("Error al conectar con la API: " + status);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el autor.");
            return false;
        }
    }


    public static boolean updateEventFieldById(String eventId, String fieldName, String newValue) {
        try {
            JSONObject updateJson = new JSONObject();
            updateJson.put(fieldName, newValue);

            String apiUrl = "http://localhost:9006/events/update/" + eventId;
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = updateJson.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                System.out.println("Campo actualizado exitosamente.");
                return true;
            } else {
                System.out.println("Error al conectar con la API: " + status);
                return false;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el campo del evento.");
            return false;
        }
    }

    public static boolean updateAuthorFieldById(String authorId, String fieldName, String newValue) {
        try {
            JSONObject updateJson = new JSONObject();
            updateJson.put(fieldName, newValue);

            String apiUrl = "http://localhost:9006/authors/update/" + authorId;
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = updateJson.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                System.out.println("Campo actualizado exitosamente.");
                return true;
            } else {
                System.out.println("Error al conectar con la API: " + status);
                return false;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el campo del autor.");
            return false;
        }
    }

}
