package com.group.groupcodemarking;

import net.minidev.json.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {

    CollaborationDAO collaborationDAO = new CollaborationDAO();

    @GetMapping("/api/hello")
    public List<String> hello() throws IOException, URISyntaxException, InterruptedException, ParseException {
        collaborationDAO.getCodeScan();
        return collaborationDAO.getGitHubDetails();
    }

    @GetMapping("/api/commits")
    public Map<String, Integer> commits(String repo) throws IOException, URISyntaxException, InterruptedException, ParseException {
        return collaborationDAO.getCommits(repo);
    }
}
