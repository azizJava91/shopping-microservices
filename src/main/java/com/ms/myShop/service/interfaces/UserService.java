package com.ms.myShop.service.interfaces;

import com.ms.myShop.dto.request.UserRequest;
import com.ms.myShop.dto.response.Response;
import com.ms.myShop.dto.response.UserResponse;

public interface UserService {
    Response<UserResponse> login(UserRequest request);

    Response<UserResponse> create(UserRequest request);
}
