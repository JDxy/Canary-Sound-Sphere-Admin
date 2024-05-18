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
 * Gestiona las operaciones relacionadas con los Eventos.
 */
public class EventManager {

    /**
     * Recupera todos los eventos desde la API remota.
     *
     * @return Una lista de objetos EventClass que representan todos los eventos.
     * @throws IOException   Si ocurre un error de E/S mientras se conecta a la API.
     * @throws JSONException Si hay un error en el procesamiento de JSON.
     */
    public static List<EventClass> getAllEvents() throws IOException, JSONException {
        List<EventClass> events = new ArrayList<>();
        String apiUrl = "http://localhost:9006/events";
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

    /**
     * Inserta un nuevo evento en la API remota.
     *
     * @param event     El objeto EventClass que representa el evento a insertar.
     * @param authToken El token de autenticación para acceder a la API.
     * @return True si la inserción del evento es exitosa, false de lo contrario.
     */
    public static boolean insertEvent(EventClass event, String authToken) {
        try {
            JSONObject eventJson = createEventJson(event);
            HttpURLConnection con = createConnection(authToken);
            sendRequest(con, eventJson);

            int status = con.getResponseCode();
            System.out.println(status);
            return true;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            System.out.println("Error al insertar el evento.");
            return false;
        }
    }

    /**
     * Crea un objeto JSON a partir de un objeto EventClass.
     *
     * @param event El evento del cual se crea el objeto JSON.
     * @return El objeto JSON creado a partir del evento.
     * @throws JSONException Si ocurre un error al construir el objeto JSON.
     */
    private static JSONObject createEventJson(EventClass event) throws JSONException {
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
        return eventJson;
    }


    /**
     * Crea una conexión HTTP con la API remota para la inserción de un evento.
     *
     * @param authToken El token de autenticación para acceder a la API.
     * @return La conexión HTTP configurada para la inserción de un evento.
     * @throws IOException Si ocurre un error de E/S durante la conexión.
     */
    private static HttpURLConnection createConnection(String authToken) throws IOException {
        String apiUrl = "http://localhost:9006/events";
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Authorization", "Bearer " + authToken);
        con.setDoOutput(true);
        return con;
    }

    /**
     * Envía una solicitud HTTP con el objeto JSON del evento a la API remota.
     *
     * @param con       La conexión HTTP configurada para la solicitud.
     * @param eventJson El objeto JSON del evento que se enviará en la solicitud.
     * @throws IOException Si ocurre un error de E/S durante el envío de la solicitud.
     */
    private static void sendRequest(HttpURLConnection con, JSONObject eventJson) throws IOException {
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = eventJson.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
    }

    /**
     * Recupera un evento por su ID desde la API remota.
     *
     * @param eventId El ID del evento a recuperar.
     * @return El objeto EventClass que representa el evento recuperado.
     */
    public static EventClass getEventById(String eventId) {
        try {
            String apiUrl = "http://localhost:9006/events/" + eventId;
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

                JSONObject eventJson = new JSONObject(content.toString());
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
                return event;
            } else {
                System.out.println("Error al conectar con la API: " + status);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            System.out.println("Error al obtener el evento desde la base de datos.");
        }
        return null;
    }

    /**
     * Genera el ID para un nuevo evento incrementando el ID máximo existente.
     *
     * @return El ID generado para el nuevo evento.
     */
    public static String idEventPlusOne(){
        try {
            List<EventClass> events = EventManager.getAllEvents();
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

    /**
     * Actualiza un campo específico de un evento en la API remota.
     *
     * @param eventId   El ID del evento que se actualizará.
     * @param fieldName El nombre del campo que se actualizará.
     * @param newValue  El nuevo valor para el campo especificado.
     * @param authToken El token de autenticación para acceder a la API.
     * @return True si la actualización del campo es exitosa, false de lo contrario.
     */
    public static boolean updateEventFieldById(String eventId, String fieldName, String newValue, String authToken) {
        try {
            JSONObject updateJson = new JSONObject();
            updateJson.put(fieldName, newValue);

            String apiUrl = "http://localhost:9006/events/" + eventId;
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
            System.out.println("Error al actualizar el campo del evento.");
            return false;
        }
    }

    /**
     * Elimina un evento por su ID desde la API remota.
     *
     * @param eventId   El ID del evento que se eliminará.
     * @param authToken El token de autenticación para acceder a la API.
     * @return True si el evento se elimina exitosamente, false de lo contrario.
     */
    public static boolean deleteEventById(String eventId, String authToken) {
        try {
            String apiUrl = "http://localhost:9006/events/" + eventId;
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            con.setRequestProperty("Authorization", "Bearer " + authToken);

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
}