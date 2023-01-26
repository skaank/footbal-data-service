package com.project.footballApp.controller;

import com.project.footballApp.entity.UserInfo;
import com.project.footballApp.model.request.AddUserRequest;
import com.project.footballApp.model.response.DataResponse;
import com.project.footballApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "Adding user into system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully added user in the system",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))}),
            @ApiResponse(responseCode = "400", description = "User already exists in the system",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden (only open for users with admin access)",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))})
    })
    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<DataResponse> addUser(@RequestBody @Valid AddUserRequest request) {
        UserInfo createdUser = userService.addUser(request);
        DataResponse dataResponse =new DataResponse(true,createdUser,null);
        return ResponseEntity.ok().body(dataResponse);
    }

}
