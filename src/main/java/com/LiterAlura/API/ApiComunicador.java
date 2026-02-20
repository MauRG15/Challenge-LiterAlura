package com.LiterAlura.API;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiComunicador {
    private String respuestaAPI;
    public String comunicarConAPI(String direccion) {
        //Conectar aplicacion con servicios
        HttpClient client = HttpClient.newHttpClient();

        //Solicitar datos de la API
        URI direccionURI = URI.create(direccion);
        HttpRequest request = HttpRequest.newBuilder().uri(direccionURI).build();

        //Respuesta de la API
        try {
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
            respuestaAPI = response.body();
            return respuestaAPI;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
