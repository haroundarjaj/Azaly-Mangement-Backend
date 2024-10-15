package com.dartech.azalymanagementserver.service;

import com.dartech.azalymanagementserver.controller.Request.UserAddRequest;
import com.dartech.azalymanagementserver.controller.Request.UserUpdateRequest;
import com.dartech.azalymanagementserver.dto.UserDto;
import com.dartech.azalymanagementserver.dto.UserDto;
import com.dartech.azalymanagementserver.entity.Role;
import com.dartech.azalymanagementserver.entity.User;
import com.dartech.azalymanagementserver.entity.User;
import com.dartech.azalymanagementserver.repository.RoleRepository;
import com.dartech.azalymanagementserver.repository.UserRepository;
import com.dartech.azalymanagementserver.repository.UserRepository;
import com.dartech.azalymanagementserver.utils.StringDecryption;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    StringDecryption stringDecryption;

    @Override
    public UserDto save(UserAddRequest userAddRequest) {
        User user = modelMapper.map(userAddRequest, User.class);
        user.setPassword(stringDecryption.decryptString(userAddRequest.getPassword()));
        List<Role> roles = new ArrayList<>();
        for (String code : userAddRequest.getRoles()) {
            Role role = roleRepository.findByCode(code).get();
            roles.add(role);
        }
        user.setRoles(roles);
        user.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        user = userRepository.save(user);
        UserDto userDto = generateUserDto(user);
        return userDto;
    }

    @Override
    public UserDto update(UserUpdateRequest userUpdateRequest) {
        User user = modelMapper.map(userUpdateRequest, User.class);
        System.out.println(user);
        List<Role> roles = new ArrayList<>();
        for (String id : userUpdateRequest.getRoles()) {
            Role role = roleRepository.findById(id).get();
            roles.add(role);
        }
        user.setRoles(roles);
        user.setApproved(true);
        user.setLastModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setLastModifiedDate(LocalDateTime.now().toString());
        user = userRepository.save(user);
        UserDto userDto = generateUserDto(user);
        return userDto;
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAllByUsernameNot("super@admin.com");
        List<UserDto> userDtos = new ArrayList<>();
        for(User user : users) {
            userDtos.add(generateUserDto(user));
        };
        return userDtos;
    }

    @Override
    public User getUserInfo(String id) {
        User user = userRepository.findById(id).get();
        return user;
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()){

            return generateUserDto(user.get());
        }
        else throw new UsernameNotFoundException("User Not Found with username: "+username);
    }

    @Override
    public UserDto findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()){

            return generateUserDto(user.get());
        }
        else throw new UsernameNotFoundException("User Not Found with email: " + email);
    }

    @Override
    public List<UserDto> getAllByApprovalState(boolean state) {
        List<User> users = userRepository.findAllByApprovedAndUsernameNot(state, "super@admin.com");
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = generateUserDto(user);
            userDtos.add(userDto);
        };
        return userDtos;
    }

    @Override
    public UserDto approveUser(String id) {
        User user = userRepository.findById(id).get();
        user.setApproved(true);
        user.setActive(true);
        user = userRepository.save(user);
        UserDto userDto = generateUserDto(user);
        return userDto;
    }

    @Override
    public UserDto activateUser(String id) {
        UserDto userDto = changeUserActivationState(id, true);
        return userDto;
    }

    @Override
    public UserDto deactivateUser(String id) {
        UserDto userDto = changeUserActivationState(id, false);
        return userDto;
    }

    public UserDto changeUserActivationState(String id, boolean active) {
        User user = userRepository.findById(id).get();
        user.setActive(active);
        user = userRepository.save(user);
        UserDto userDto =  generateUserDto(user);
        return userDto;
    }

    public UserDto generateUserDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        List<String> roles = new ArrayList<>();
        user.getRoles().forEach(role -> {
            roles.add(role.getCode());
        });
        userDto.setRoles(roles);
        return userDto;
    }
}
