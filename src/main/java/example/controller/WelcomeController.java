package example.controller;

import example.service.WelcomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/deposit-service-accounts")
@Tag(name = "Welcome", description = "Basic endpoints for verifying the application is up")
public class WelcomeController {

    private final WelcomeService welcomeService;

    public WelcomeController(WelcomeService welcomeService) {
        this.welcomeService = welcomeService;
    }

    @GetMapping("/welcome")
    @Operation(summary = "Get a welcome message")
    public Map<String, String> welcome() {
        return Map.of("message", welcomeService.getWelcomeMessage());
    }
}
