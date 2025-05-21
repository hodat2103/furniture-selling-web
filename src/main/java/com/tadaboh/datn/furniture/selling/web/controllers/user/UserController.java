package com.tadaboh.datn.furniture.selling.web.controllers.user;

import com.tadaboh.datn.furniture.selling.web.dtos.request.UserRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.ResponseSuccess;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.UserResponse;
import com.tadaboh.datn.furniture.selling.web.services.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}users")
@Tag(name = "User", description = "User API")
public class UserController {
    private final IUserService userService;
    @GetMapping("/get-user-by-email")
    public ResponseEntity<ResponseSuccess> getUserByEmail(@RequestParam String email) {
        UserResponse userResponse = userService.getUserByEmail(email);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.ACCEPTED, "Get user by email successfully", userResponse);
        return ResponseEntity.ok(responseSuccess);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<ResponseSuccess> updateUser(@PathVariable Long id,
                                                       @RequestPart(value = "userRequest") UserRequest userRequest,
                                                       @RequestPart(value = "multipartFile") MultipartFile multipartFile) throws IOException {
        UserResponse updatedUser = userService.updateUser(userRequest, multipartFile);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.OK, "Update user successfully", updatedUser);
        return ResponseEntity.ok(responseSuccess);
    }

    @GetMapping("/get-by-email")
    public ResponseEntity<ResponseSuccess> getUser(@RequestParam String email) {
        UserResponse userResponse = userService.getUserByEmail(email);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.OK, "Get user successfully", userResponse);
        return ResponseEntity.ok(responseSuccess);
    }
}
