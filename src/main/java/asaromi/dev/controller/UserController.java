package asaromi.dev.controller;

import asaromi.dev.dto.ApiResponse;
import asaromi.dev.dto.RegisterUserRequest;
import asaromi.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(
            path = "/api/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResponse<String> register(@RequestBody RegisterUserRequest request) {
        userService.register(request);
        return new ApiResponse<String>("OK", null);
    }
}
