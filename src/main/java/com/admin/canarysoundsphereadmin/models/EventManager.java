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

public class EventManager {
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

    private static void sendRequest(HttpURLConnection con, JSONObject eventJson) throws IOException {
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = eventJson.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
    }

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