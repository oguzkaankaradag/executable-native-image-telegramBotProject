package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class Requester {

    public void httpGetRequest(String url) throws IOException, InterruptedException {

        ConfigPropertiesReader reader = new ConfigPropertiesReader();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(Long.parseLong(reader.getValueFromFile("timeout-duration"))))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HttpResponse response;
        try {
            response = client.send(
                    request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        MessageSender parser = new MessageSender();
        parser.responseParser(response);


    }
}
