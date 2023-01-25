package com.project.footballApp.service;

import com.project.footballApp.entity.UserInfo;
import com.project.footballApp.model.request.AddUserRequest;

public interface UserService {
    UserInfo addUser(AddUserRequest request);
}
