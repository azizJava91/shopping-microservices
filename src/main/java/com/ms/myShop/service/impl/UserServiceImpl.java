package com.ms.myShop.service.impl;

import com.ms.myShop.dto.request.UserRequest;

import com.ms.myShop.dto.response.Response;
import com.ms.myShop.dto.response.UserResponse;
import com.ms.myShop.entity.User;
import com.ms.myShop.enums.EnumAvailableStatus;
import com.ms.myShop.enums.ResponseCodes;
import com.ms.myShop.enums.ResponseMessages;
import com.ms.myShop.exception.ShopException;
import com.ms.myShop.repository.UserRepository;
import com.ms.myShop.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Response<UserResponse> login(UserRequest request) {

        Response<UserResponse> response = new Response<>();
        try {
            User user = userRepository.findByEmailAndAndPasswordAndActive(request.getEmail(), request.getPassword(), EnumAvailableStatus.ACTIVE.value);
            if (user == null) {
                throw new ShopException(ResponseMessages.USER_NOT_FOUND.value, ResponseCodes.NOT_FOUND.value);
            }
            UserResponse userResponse = UserResponse.builder()
                    .userId(request.getUserId())
                    .name(request.getName())
                    .email(request.getEmail())
                    .authorization(request.getAuthentication())
                    .build();
            response.setBody(userResponse);
            response.setMessage(ResponseMessages.SUCCESS.value);
            response.setStatusCode(ResponseCodes.SUCCESS.value);
        } catch (ShopException e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatusCode(e.getCode());
        } catch (Exception e) {
            response.setMessage(ResponseMessages.INTERNAL_SERVER_ERROR.value);
            response.setStatusCode(ResponseCodes.INTERNAL_SERVER_ERROR.value);
        }
        return response;
    }

    @Override
    public Response<UserResponse> create(UserRequest request) {
        Response<UserResponse> response = new Response<>();

        try {
            User user = userRepository.findByEmailAndAndPasswordAndActive(request.getEmail(), request.getPassword(), EnumAvailableStatus.ACTIVE.value);
            if (user != null) {
                throw new ShopException(ResponseMessages.MAIL_IS_IN_USE.value, ResponseCodes.CONFLICT.value);
            }

            UserResponse userResponse = convert(user);

            response.setBody(userResponse);
            response.setMessage(ResponseMessages.SUCCESS.value);
            response.setStatusCode(ResponseCodes.SUCCESS.value);
        } catch (ShopException e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatusCode(e.getCode());
        } catch (Exception e) {
            response.setMessage(ResponseMessages.INTERNAL_SERVER_ERROR.value);
            response.setStatusCode(ResponseCodes.INTERNAL_SERVER_ERROR.value);
        }
        return response;
    }


    private UserResponse convert(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .authorization(user.getAuthorization())
                .build();
    }
}
