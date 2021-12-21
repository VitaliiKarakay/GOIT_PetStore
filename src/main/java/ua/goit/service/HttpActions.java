package ua.goit.service;

import com.google.gson.Gson;
import ua.goit.model.ApiResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

public class HttpActions {

    HttpClient client = HttpClient.newHttpClient();
    Services services = new Services();
    Gson gson = new Gson();

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

    public HttpResponse post(String templateName, String params) {
        String url = "https://petstore.swagger.io/v2/%s"+params;
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

    public HttpResponse postImage(String params, Map<Object, Object> data) {
        String url = "https://petstore.swagger.io/v2/pet/%s";
        String boundary = "-------------oiawn4tp89n4e9p5";

        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format(url, params)))
                    .headers("accept", "application/json", "Content-Type",
                            "multipart/form-data;boundary=" + boundary)
                    .POST(oMultipartData(data, boundary))
                    .build();
        } catch (IOException e) {
            services.printErrorMessage("Try again");
        }

        HttpResponse<String> response = null;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            services.printErrorMessage("Try again");
        }
        if (response != null && response.statusCode() == 200) {
            services.printRegularMessage("Image was sent");
        } else {
            services.printRegularMessage("Image was not sent");
        }
        return response;
    }

    public static HttpRequest.BodyPublisher oMultipartData(Map<Object, Object> data,
                                                           String boundary) throws IOException {
        var byteArrays = new ArrayList<byte[]>();
        byte[] separator = ("--" + boundary
                + "\r\nContent-Disposition: form-data; name=")
                .getBytes(StandardCharsets.UTF_8);
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            byteArrays.add(separator);

            if (entry.getValue() instanceof Path) {
                var path = (Path) entry.getValue();
                String mimeType = Files.probeContentType(path);
                byteArrays.add(("\"" + entry.getKey() + "\"; filename=\""
                        + path.getFileName() + "\"\r\nContent-Type: " + mimeType
                        + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                byteArrays.add(Files.readAllBytes(path));
                byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
            } else {
                byteArrays.add(
                        ("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue()
                                + "\r\n").getBytes(StandardCharsets.UTF_8));
            }
        }
        byteArrays
                .add(("--" + boundary + "--").getBytes(StandardCharsets.UTF_8));
        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }

    public HttpResponse put(String templateName){
        String url = "https://petstore.swagger.io/v2/%s";
        HttpRequest request = null;
        HttpResponse httpResponse = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format(url, templateName)))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofFile(Path.of(templateName + ".json")))
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

    public HttpResponse put(String templateName, String params){
        String url = "https://petstore.swagger.io/v2/%s/"+params;
        HttpRequest request = null;
        HttpResponse httpResponse = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format(url, templateName)))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofFile(Path.of(templateName + ".json")))
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
