package asaromi.dev.controller;

import asaromi.dev.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping(path = "/api")
    ApiResponse<String> homeController() {
        return ApiResponse.<String>builder().data("Application running").build();
    }
}
