package com.project.footballApp.service.impl;

import com.project.footballApp.entity.UserInfo;
import com.project.footballApp.exceptionHandling.custom.AlreadyExistException;
import com.project.footballApp.model.request.AddUserRequest;
import com.project.footballApp.repository.UserInfoRepository;
import com.project.footballApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserInfoRepository userInfoRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserInfoRepository userInfoRepository,PasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * this method checks first whether user exist or not and then
     * accordingly add user or returns an exception
     * 
     * @param request
     * @return
     */
    @Override
    public UserInfo addUser(AddUserRequest request) {
        Optional<UserInfo> optionalUserInfo = userInfoRepository.findByName(request.getName());

        if(optionalUserInfo.isPresent()) {
            throw new AlreadyExistException("user with name : " + request.getName()+ " already exists");
        } else {
            UserInfo userInfo = new UserInfo();
            userInfo.setName(request.getName());
            userInfo.setEmail(request.getEmail());
            userInfo.setPassword(passwordEncoder.encode(request.getPassword()));
            userInfo.setRoles(request.getRoles());
            UserInfo savedUser = userInfoRepository.save(userInfo);
            return savedUser;
        }
    }
}
