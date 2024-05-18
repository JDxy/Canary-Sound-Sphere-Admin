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

/**
 * Gestiona las operaciones relacionadas con los autores.
 */
public class AuthorManager {

    /**
     * Obtiene todos los autores desde la API.
     *
     * @return Una lista de objetos Author que representan los autores obtenidos.
     * @throws IOException  Si ocurre un error de E/S al conectarse a la API.
     * @throws JSONException Si ocurre un error al procesar la respuesta JSON de la API.
     */
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

    /**
     * Inserta un nuevo autor en la base de datos.
     *
     * @param author    El autor a insertar.
     * @param authToken El token de autenticación para la API.
     * @return true si la operación de inserción fue exitosa, false de lo contrario.
     */
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

    /**
     * Crea un objeto JSON a partir de un autor.
     *
     * @param author El autor para el cual se creará el objeto JSON.
     * @return El objeto JSON creado.
     * @throws JSONException Si ocurre un error al crear el objeto JSON.
     */
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

    /**
     * Crea una conexión HTTP con la API de autores.
     *
     * @param authToken El token de autenticación para la API.
     * @return La conexión HTTP creada.
     * @throws IOException Si ocurre un error al crear la conexión.
     */
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


    /**
     * Envía una solicitud HTTP con el objeto JSON dado.
     *
     * @param con        La conexión HTTP a través de la cual se enviará la solicitud.
     * @param authorJson El objeto JSON que se enviará en la solicitud.
     * @throws IOException Si ocurre un error al enviar la solicitud.
     */
    private static void sendRequest(HttpURLConnection con, JSONObject authorJson) throws IOException {
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = authorJson.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
    }

    /**
     * Obtiene un autor por su ID desde la base de datos.
     *
     * @param authorId El ID del autor que se desea obtener.
     * @return El autor correspondiente al ID especificado, o null si no se encuentra.
     */
    public static Author getAuthorById(String authorId) {
        try {
            String apiUrl = "http://localhost:9006/authors/" + authorId;
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

                JSONObject authorJson = new JSONObject(content.toString());
                Author author = new Author(
                        authorJson.getString("_id"),
                        authorJson.getString("name"),
                        authorJson.getString("image"),
                        authorJson.getInt("foundation_year"),
                        authorJson.getString("music_type"),
                        authorJson.getString("description"),
                        authorJson.getString("music_list")
                );
                return author;
            } else {
                System.out.println("Error al conectar con la API: " + status);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            System.out.println("Error al obtener el autor desde la base de datos.");
        }
        return null;
    }

    /**
     * Incrementa el ID del autor en uno y lo devuelve como una cadena de cuatro dígitos.
     *
     * @return El ID del autor incrementado en uno como una cadena de cuatro dígitos.
     */
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

    /**
     * Actualiza un campo específico de un autor en la base de datos.
     *
     * @param authorId   El ID del autor cuyo campo se actualizará.
     * @param fieldName  El nombre del campo que se actualizará.
     * @param newValue   El nuevo valor para el campo especificado.
     * @param authToken  El token de autenticación para la API.
     * @return true si la operación de actualización fue exitosa, false de lo contrario.
     */
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

    /**
     * Elimina un autor de la base de datos.
     *
     * @param authorId  El ID del autor que se eliminará.
     * @param authToken El token de autenticación para la API.
     * @return true si la operación de eliminación fue exitosa, false de lo contrario.
     */
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