package com.pauloricardo.frontend_estoque.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pauloricardo.frontend_estoque.Session.Session;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class AutenticacaoService {

    private static final String BASE_URL = "http://localhost:8085";

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public boolean login(String username, String password){
        try{
            Map<String,String> body = Map.of(
                    "username",username,
                    "password",password
            );

            String json = mapper.writeValueAsString(body);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/auth/login"))
                    .header("Content-Type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response=
                    client.send(request,HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200){
                var node = mapper.readTree(response.body());
                String token = node.get("token").asText();

                Session.setToken(token); //Salva  o JWT
                return true;
            }

            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
