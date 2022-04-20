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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public void getCodeScan() throws IOException, URISyntaxException, InterruptedException, ParseException {
        URI url = new URI("https://api.github.com/FraserMcCa/CollaborativeCodeMarkingTool/code-scanning/alerts");
        var request = HttpRequest.newBuilder().uri(url)
                .setHeader("Authorization", authorization)
                .GET()
                .build();

        var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.body());
    }

    public Map<String, Integer> getCommits(String repo) throws URISyntaxException, IOException, InterruptedException, ParseException {
        Map<String, Integer> commitMap = new HashMap<>();
        URI url = new URI("https://api.github.com/repos/FraserMcCa/CollaborativeCodeMarkingTool/commits");
        var request = HttpRequest.newBuilder().uri(url)
                .setHeader("Authorization", authorization)
                .GET()
                .build();

        var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JSONArray jsonObject = (JSONArray) new JSONParser().parse(response.body());
        for (Object object: jsonObject) {
            JSONObject json = (JSONObject) object;
            JSONObject commit = (JSONObject) json.get("commit");
            JSONObject author = (JSONObject)commit.get("author");
            String authorName = author.getAsString("name");
            if (commitMap.containsKey(authorName)) {
                commitMap.put(authorName, commitMap.get(authorName) + 1);
            } else {
                commitMap.put(authorName, 1);
            }
        }
        commitMap.keySet().forEach(e -> System.out.println(commitMap.get(e)));
        return commitMap;
    }
}
