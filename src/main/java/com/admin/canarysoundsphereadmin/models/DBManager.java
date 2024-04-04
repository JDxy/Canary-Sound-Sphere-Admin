package com.admin.canarysoundsphereadmin.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class DBManager {
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
