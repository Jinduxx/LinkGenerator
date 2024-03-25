package com.example.linkgenerator.service.ServiceImpl;

import com.example.linkgenerator.dto.*;
import com.example.linkgenerator.exception.CustomException;
import com.example.linkgenerator.exception.GlobalExceptionHandler;
import com.example.linkgenerator.model.User;
import com.example.linkgenerator.model.UserAccount;
import com.example.linkgenerator.repo.UserRepo;
import com.example.linkgenerator.security.JwtTokenProvider;
import com.example.linkgenerator.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepo userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider,
                           UserRepo userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterUserResponse registerUser(RegistrationRequest request) {
        RegisterUserResponse response;
        User user = new User();
        try {
            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                throw new CustomException("Username already exist", HttpStatus.BAD_REQUEST.value());
            }
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setFullName(request.getFullName());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setPin(passwordEncoder.encode(request.getPin()));
            user.setRoles(Arrays.asList("USER"));
            List<UserAccount> accountList = new ArrayList<>();
            UserAccount account = new UserAccount();
            for(Account account1: request.getAccountNumber()){
                account.setAccountNumber(account1.getAccountNumber());
                account.setUser(user);
                account.setFullName(request.getFullName());
                accountList.add(account);
            }
            user.setAccountList(accountList);

            userRepository.save(user);
        } catch (Exception ex){
            ex.printStackTrace();
            throw new CustomException(ex.getMessage(),400);
        }

        response = RegisterUserResponse.builder()
                .isSuccessful(true)
                .responseMessage("Registration Successful")
                .responseCode("00")
                .build();

        return response;
    }

    public User getUserByUsername(String username) {
        try{
            return userRepository.findByUsername(username).orElseThrow(()
                    -> new CustomException("Invalid username", HttpStatus.BAD_REQUEST.value()));
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(),400);
        }

    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        try{
            User user = userRepository.findByUsername(request.getUsername()).orElseThrow(()
                    -> new CustomException("Invalid username", HttpStatus.BAD_REQUEST.value()));

            if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
                throw new CustomException("Invalid Password", HttpStatus.BAD_REQUEST.value());
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.createToken(authentication);

            return AuthResponse.builder()
                    .fullName(user.getFullName())
                    .userName(user.getUsername())
                    .token(token)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(), 400);
        }

    }
}
