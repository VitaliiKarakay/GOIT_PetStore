package ua.goit.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

public class HttpActions {

    HttpClient client = HttpClient.newHttpClient();
    Services services = new Services();

    public void delete (String answer, String handlerName) {

        String url = "https://petstore.swagger.io/v2/%s/%s";
        if (handlerName.equals("store")) {
            answer = "order/" + answer;
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(url, handlerName, answer)))
                .DELETE()
                .build();
        HttpResponse response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
           services.printErrorMessage("Try again");
        }
        if (response != null) {
            services.printRegularMessage("Answer" + response.statusCode());
        }
    }

    public HttpResponse get(String templateName, String params, String statusOrId){

        String url = "https://petstore.swagger.io/v2/%s/%s%s";

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format(url, templateName, params, statusOrId)))
                .build();

        HttpResponse response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response != null) {
                services.printRegularMessage("Answer" + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            services.printErrorMessage("Try again");
        }
        return response;
    }

    public HttpResponse post(String templateName) {
        String url = "https://petstore.swagger.io/v2/%s";
        if (templateName.equals("store")) {
            url+="/order";
        }
        HttpRequest request = null;
        HttpResponse httpResponse = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format(url, templateName)))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofFile(Path.of(templateName + ".json")))
                    .build();
        } catch (FileNotFoundException e) {
            services.printErrorMessage("File not found");
        }
        try {
            httpResponse = client.send(request, HttpResponse.BodyHandlers.ofLines());
        } catch (IOException | InterruptedException e) {
            services.printErrorMessage("Try again");
        }
        if (httpResponse != null && httpResponse.statusCode() == 200) {
            services.printRegularMessage("Done");
        }
        return httpResponse;
    }
}
