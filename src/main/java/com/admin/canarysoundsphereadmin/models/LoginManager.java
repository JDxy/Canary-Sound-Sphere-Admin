package com.admin.canarysoundsphereadmin.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginManager {
    /**
     * Método signin
     * Realiza una solicitud de inicio de sesión a un servidor HTTP.
     * @param user
     * @return Devuelve un token de acceso si la autenticación es exitosa sino devuelve null.
     */
    public static String signin(User user) {
        try {
            // Crea el JSON para la autenticación
            JSONObject loginJson = new JSONObject();
            loginJson.put("username", user.getUsername());
            loginJson.put("password", user.getPassword());

            // Configura la conexión HTTP
            String apiUrl = "http://localhost:9006/api/auth/signin";
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);

            // Envia la solicitud
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = loginJson.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Obtiene y procesa la respuesta
            int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                return processResponse(con);
            } else {
                System.out.println("Error: La autenticación falló con el código de estado " + status);
                return null;
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Método processResponse
     * Se encarga de leer y procesar la respuesta recibida del servidor
     * después de realizar una solicitud de inicio de sesión.
     * @param con
     * @return Devuelve el token si lo encuentra o null en caso contrario.
     * @throws IOException
     * @throws JSONException
     */
    private static String processResponse(HttpURLConnection con) throws IOException, JSONException {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONObject jsonResponse = new JSONObject(response.toString());
        if (jsonResponse.has("accessToken")) {
            return jsonResponse.getString("accessToken");
        } else {
            System.out.println("Error: No se encontró un token en la respuesta JSON.");
            return null;
        }
    }
}