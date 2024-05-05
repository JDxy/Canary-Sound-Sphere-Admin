package com.admin.canarysoundsphereadmin.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AuthorManager {
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

    public static boolean insertAuthor(Author author, String authToken) {
        try {
            JSONObject authorJson = createAuthorJson(author);
            HttpURLConnection con = createConnection(authToken);
            sendRequest(con, authorJson);

            int status = con.getResponseCode();
            System.out.println(status);
            return true;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            System.out.println("Error al insertar el autor.");
            return false;
        }
    }

    private static JSONObject createAuthorJson(Author author) throws JSONException {
        JSONObject authorJson = new JSONObject();
        authorJson.put("_id", author.get_id());
        authorJson.put("name", author.getName());
        authorJson.put("image", author.getImage());
        authorJson.put("foundation_year", author.getFoundation_year());
        authorJson.put("music_type", author.getMusic_type());
        authorJson.put("description", author.getDescription());
        authorJson.put("music_list", author.getMusic_list());
        return authorJson;
    }

    private static HttpURLConnection createConnection(String authToken) throws IOException {
        String apiUrl = "http://localhost:9006/authors";
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Authorization", "Bearer " + authToken);
        con.setDoOutput(true);
        return con;
    }

    private static void sendRequest(HttpURLConnection con, JSONObject authorJson) throws IOException {
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = authorJson.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
    }

    public static String idAuthorPlusOne(){
        try {
            List<Author> authors = AuthorManager.getAllAuthors();

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

    public static boolean updateAuthorFieldById(String authorId, String fieldName, String newValue, String authToken) {
        try {
            JSONObject updateJson = new JSONObject();
            updateJson.put(fieldName, newValue);

            String apiUrl = "http://localhost:9006/authors/" + authorId;
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Authorization", "Bearer " + authToken);
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

    public static boolean deleteAuthorById(String authorId, String authToken) {
        try {
            String apiUrl = "http://localhost:9006/authors/" + authorId;
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            con.setRequestProperty("Authorization", "Bearer " + authToken);

            int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                System.out.println("Author eliminado exitosamente.");
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
}