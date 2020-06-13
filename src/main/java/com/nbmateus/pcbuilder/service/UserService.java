package com.nbmateus.pcbuilder.service;

import com.nbmateus.pcbuilder.dao.UserRepository;
import com.nbmateus.pcbuilder.dto.LoginForm;
import com.nbmateus.pcbuilder.dto.SignUpForm;
import com.nbmateus.pcbuilder.dto.UserDetailsDto;
import com.nbmateus.pcbuilder.model.User;
import com.nbmateus.pcbuilder.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public User signUp(SignUpForm signUpForm) throws Exception {
        User user = new User(signUpForm.getUsername(), signUpForm.getEmail(), signUpForm.getPassword(), false);
        if (!signUpForm.getUsername().matches("[a-zA-Z0-9\\._\\-]{3,25}")) {
            throw new Exception(
                    "Username must contain between 3 and 25 valid characters (letters, numbers, dots or uderscores).");
        }

        if (userRepository.findByUsername(signUpForm.getUsername()) != null) {
            throw new Exception("Username already exists.");
        }

        if (!signUpForm.getEmail().matches("[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+")) {
            throw new Exception("Invalid email format.");
        }

        if (userRepository.findByEmail(signUpForm.getEmail()) != null) {
            throw new Exception("Email already in  use.");
        }

        if (!signUpForm.getPassword().matches("[a-zA-Z0-9]{6,40}")) {
            throw new Exception("Password must contain between 6 and 40 character (only letters or numbers).");
        }
        
        if (!signUpForm.getPassword().equals(signUpForm.getConfirmPassword())) {
            throw new Exception("Passwords don't match.");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        return userRepository.save(user);
    }

    public String login(LoginForm clientUser) throws Exception {
        User dbUser = userRepository.findByUsername(clientUser.getUsername());
        if (dbUser == null || !new BCryptPasswordEncoder().matches(clientUser.getPassword(), dbUser.getPassword())) {
            throw new Exception("Invalid username or password.");
        }
        return "Bearer " + jwtUtil.generateToken(dbUser);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(username);
        return user;
    }

    public UserDetailsDto getUserDetails(String token) throws Exception {
        token = token.substring(7);
        User user = userRepository.findByUsername(jwtUtil.getUsernameFromToken(token));
        if (user == null) {
            throw new Exception("Invalid token.");
        }
        return new UserDetailsDto(user.getUsername(), user.getEmail(), user.isAdmin());
    }

}