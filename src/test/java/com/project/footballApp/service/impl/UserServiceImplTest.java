package com.project.footballApp.service.impl;

import com.project.footballApp.repository.UserInfoRepository;
import com.project.footballApp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserInfoRepository userInfoRepository;


    @BeforeEach
    void setUp() {
    }

    @Test
    void addUser() {
    }
}