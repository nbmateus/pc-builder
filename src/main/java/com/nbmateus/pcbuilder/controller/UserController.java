package com.nbmateus.pcbuilder.controller;

import javax.servlet.http.HttpServletRequest;

import com.nbmateus.pcbuilder.dto.LoginForm;
import com.nbmateus.pcbuilder.dto.MessageResponse;
import com.nbmateus.pcbuilder.dto.SignUpForm;
import com.nbmateus.pcbuilder.dto.UserDetailsDto;
import com.nbmateus.pcbuilder.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/details")
    public ResponseEntity<UserDetailsDto> getUserDetails(HttpServletRequest request) {
        ResponseEntity<UserDetailsDto> response;
        try {
            response = new ResponseEntity<UserDetailsDto>(
                    userService.getUserDetails(request.getHeader("Authorization")), HttpStatus.OK);
        } catch (Exception exception) {
            response = new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return response;
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signUp(@RequestBody SignUpForm signUpForm) {
        ResponseEntity<MessageResponse> response;
        try {
            userService.signUp(signUpForm);
            response = new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception exception) {
            response = new ResponseEntity<MessageResponse>(new MessageResponse(exception.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(@RequestBody LoginForm loginForm) {
        ResponseEntity<MessageResponse> response;
        try {
            response = new ResponseEntity<MessageResponse>(new MessageResponse(userService.login(loginForm)),
                    HttpStatus.OK);

        } catch (Exception exception) {
            response = new ResponseEntity<MessageResponse>(new MessageResponse(exception.getMessage()),
                    HttpStatus.UNAUTHORIZED);
        }
        return response;
    }

}