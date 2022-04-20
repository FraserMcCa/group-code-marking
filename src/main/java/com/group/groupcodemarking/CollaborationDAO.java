package com.group.groupcodemarking;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CollaborationDAO {
    private static final String authorization = "Bearer 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";
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public List<String> getGitHubDetails() throws IOException, URISyntaxException, InterruptedException, ParseException {
        URI url = new URI("https://api.github.com/user/repos");
        var request = HttpRequest.newBuilder().uri(url)
                .setHeader("Authorization", authorization)
                .GET()
                .build();

        var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JSONArray jsonObject = (JSONArray) new JSONParser().parse(response.body());
        return jsonObject.stream().map(e -> (JSONObject) e).map(e -> e.getAsString("name")).collect(Collectors.toList());
    }
}
