package com.modernadev.Moderna.Home.service.interf;

import com.modernadev.Moderna.Home.dto.LoginRequest;
import com.modernadev.Moderna.Home.dto.Response;
import com.modernadev.Moderna.Home.dto.UserDto;
import com.modernadev.Moderna.Home.entity.User;

public interface UserService {
    Response registerUser(UserDto registrationRequest );
    Response loginUser(LoginRequest loginRequest );
    Response getAllUsers();
    User getLoginUser();
    Response getUserInfoAndOrderHistory();
}
