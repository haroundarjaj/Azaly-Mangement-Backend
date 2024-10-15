package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.UserAddRequest;
import com.dartech.azalymanagementserver.controller.Request.UserUpdateRequest;
import com.dartech.azalymanagementserver.dto.UserDto;
import com.dartech.azalymanagementserver.dto.UserDto;
import com.dartech.azalymanagementserver.entity.User;

import java.util.List;

public interface UserService {
    
    UserDto save(UserAddRequest userAddRequest);

    UserDto update(UserUpdateRequest userUpdateRequest);

    List<UserDto> getAll();

    User getUserInfo(String id);

    void delete(String id);

    UserDto findByUsername (String username);

    UserDto findByEmail (String email);

    List<UserDto> getAllByApprovalState(boolean state);

    UserDto approveUser(String id);

    UserDto activateUser(String id);

    UserDto deactivateUser(String id);
}
