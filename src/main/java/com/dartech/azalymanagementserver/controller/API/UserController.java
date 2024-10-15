package com.dartech.azalymanagementserver.controller.API;

import com.dartech.azalymanagementserver.controller.Request.UserAddRequest;
import com.dartech.azalymanagementserver.controller.Request.UserUpdateRequest;
import com.dartech.azalymanagementserver.dto.UserDto;
import com.dartech.azalymanagementserver.entity.User;
import com.dartech.azalymanagementserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid UserAddRequest userAddRequest) {

        UserDto user = userService.save(userAddRequest);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody @Valid UserUpdateRequest userUpdateRequest) {

        UserDto user = userService.update(userUpdateRequest);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() {

        List<UserDto> users = userService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user-info/{id}")
    public ResponseEntity getUserInfo(@PathVariable String id) {

        User user = userService.getUserInfo(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {

        userService.delete(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/approval-state/{state}")
    public ResponseEntity getAllByApprovalState(@PathVariable boolean state) {

        List<UserDto> users = userService.getAllByApprovalState(state);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity approve(@PathVariable String id) {
        UserDto userDto = userService.approveUser(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity activateUser(@PathVariable String id) {
        UserDto userDto = userService.activateUser(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity deactivateUser(@PathVariable String id) {
        UserDto userDto = userService.deactivateUser(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
