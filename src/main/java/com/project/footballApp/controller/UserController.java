package com.project.footballApp.controller;

import com.project.footballApp.entity.UserInfo;
import com.project.footballApp.model.request.AddUserRequest;
import com.project.footballApp.model.response.DataResponse;
import com.project.footballApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<DataResponse> addUser(@RequestBody @Valid AddUserRequest request) {
        UserInfo createdUser = userService.addUser(request);
        DataResponse dataResponse =new DataResponse(true,createdUser,null);
        return ResponseEntity.ok().body(dataResponse);
    }

}
