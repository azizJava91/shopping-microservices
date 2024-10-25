package com.ms.myShop.controller.back;

import com.ms.myShop.dto.request.UserRequest;
import com.ms.myShop.dto.response.Response;
import com.ms.myShop.dto.response.UserResponse;
import com.ms.myShop.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public Response<UserResponse> login(@RequestBody UserRequest request) {
        return userService.login(request);
    }

    @PostMapping("/create")
    public Response<UserResponse> create(@RequestBody UserRequest request){
        return userService.create(request);
    }
}
