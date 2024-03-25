package com.example.linkgenerator.service.ServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.linkgenerator.dto.Account;
import com.example.linkgenerator.dto.AuthRequest;
import com.example.linkgenerator.dto.RegisterUserResponse;
import com.example.linkgenerator.dto.RegistrationRequest;
import com.example.linkgenerator.exception.CustomException;
import com.example.linkgenerator.model.User;
import com.example.linkgenerator.repo.UserRepo;
import com.example.linkgenerator.security.JwtTokenProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private UserRepo userRepo;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void testRegisterUser() {
        User user = new User();
        user.setAccountList(new ArrayList<>());
        user.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setFullName("Dr Jane Doe");
        user.setId(123L);
        user.setPassword("iloveyou");
        user.setPin("Pin");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepo.findByUsername((String) any())).thenReturn(ofResult);

        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setAccountNumber(new ArrayList<>());
        registrationRequest.setEmail("jane.doe@example.org");
        registrationRequest.setFullName("Dr Jane Doe");
        registrationRequest.setPassword("iloveyou");
        registrationRequest.setPin("Pin");
        registrationRequest.setUsername("janedoe");
        assertThrows(CustomException.class, () -> this.userServiceImpl.registerUser(registrationRequest));
        verify(this.userRepo).findByUsername((String) any());
    }

    @Test
    void testRegisterUser2() {
        when(this.userRepo.findByUsername((String) any())).thenThrow(new CustomException("An error occurred", 0));

        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setAccountNumber(new ArrayList<>());
        registrationRequest.setEmail("jane.doe@example.org");
        registrationRequest.setFullName("Dr Jane Doe");
        registrationRequest.setPassword("iloveyou");
        registrationRequest.setPin("Pin");
        registrationRequest.setUsername("janedoe");
        assertThrows(CustomException.class, () -> this.userServiceImpl.registerUser(registrationRequest));
        verify(this.userRepo).findByUsername((String) any());
    }

    @Test
    void testRegisterUser3() {
        User user = new User();
        user.setAccountList(new ArrayList<>());
        user.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setFullName("Dr Jane Doe");
        user.setId(123L);
        user.setPassword("iloveyou");
        user.setPin("Pin");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        when(this.userRepo.save((User) any())).thenReturn(user);
        when(this.userRepo.findByUsername((String) any())).thenReturn(Optional.empty());

        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setAccountNumber(new ArrayList<>());
        registrationRequest.setEmail("jane.doe@example.org");
        registrationRequest.setFullName("Dr Jane Doe");
        registrationRequest.setPassword("iloveyou");
        registrationRequest.setPin("Pin");
        registrationRequest.setUsername("janedoe");
        RegisterUserResponse actualRegisterUserResult = this.userServiceImpl.registerUser(registrationRequest);
        assertEquals("00", actualRegisterUserResult.getResponseCode());
        assertTrue(actualRegisterUserResult.isSuccessful());
        assertEquals("Registration Successful", actualRegisterUserResult.getResponseMessage());
        verify(this.userRepo).findByUsername((String) any());
        verify(this.userRepo).save((User) any());
    }

    @Test
    void testRegisterUser4() {
        when(this.userRepo.save((User) any())).thenThrow(new CustomException("An error occurred", 0));
        when(this.userRepo.findByUsername((String) any())).thenReturn(Optional.empty());

        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setAccountNumber(new ArrayList<>());
        registrationRequest.setEmail("jane.doe@example.org");
        registrationRequest.setFullName("Dr Jane Doe");
        registrationRequest.setPassword("iloveyou");
        registrationRequest.setPin("Pin");
        registrationRequest.setUsername("janedoe");
        assertThrows(CustomException.class, () -> this.userServiceImpl.registerUser(registrationRequest));
        verify(this.userRepo).findByUsername((String) any());
        verify(this.userRepo).save((User) any());
    }

    @Test
    void testRegisterUser5() {
        User user = new User();
        user.setAccountList(new ArrayList<>());
        user.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setFullName("Dr Jane Doe");
        user.setId(123L);
        user.setPassword("iloveyou");
        user.setPin("Pin");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        when(this.userRepo.save((User) any())).thenReturn(user);
        when(this.userRepo.findByUsername((String) any())).thenReturn(Optional.empty());

        Account account = new Account();
        account.setAccountNumber("42");

        ArrayList<Account> accountList = new ArrayList<>();
        accountList.add(account);

        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setAccountNumber(accountList);
        registrationRequest.setEmail("jane.doe@example.org");
        registrationRequest.setFullName("Dr Jane Doe");
        registrationRequest.setPassword("iloveyou");
        registrationRequest.setPin("Pin");
        registrationRequest.setUsername("janedoe");
        RegisterUserResponse actualRegisterUserResult = this.userServiceImpl.registerUser(registrationRequest);
        assertEquals("00", actualRegisterUserResult.getResponseCode());
        assertTrue(actualRegisterUserResult.isSuccessful());
        assertEquals("Registration Successful", actualRegisterUserResult.getResponseMessage());
        verify(this.userRepo).findByUsername((String) any());
        verify(this.userRepo).save((User) any());
    }

    @Test
    void testGetUserByUsername() {
        User user = new User();
        user.setAccountList(new ArrayList<>());
        user.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setFullName("Dr Jane Doe");
        user.setId(123L);
        user.setPassword("iloveyou");
        user.setPin("Pin");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepo.findByUsername((String) any())).thenReturn(ofResult);
        assertSame(user, this.userServiceImpl.getUserByUsername("janedoe"));
        verify(this.userRepo).findByUsername((String) any());
    }

    @Test
    void testGetUserByUsername2() {
        when(this.userRepo.findByUsername((String) any())).thenThrow(new CustomException("An error occurred", 0));
        assertThrows(CustomException.class, () -> this.userServiceImpl.getUserByUsername("janedoe"));
        verify(this.userRepo).findByUsername((String) any());
    }

    @Test
    void testGetUserByUsername3() {
        when(this.userRepo.findByUsername((String) any())).thenReturn(Optional.empty());
        assertThrows(CustomException.class, () -> this.userServiceImpl.getUserByUsername("janedoe"));
        verify(this.userRepo).findByUsername((String) any());
    }

    @Test
    void testAuthenticate() {
        User user = new User();
        user.setAccountList(new ArrayList<>());
        user.setCreatedDate(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setFullName("Dr Jane Doe");
        user.setId(123L);
        user.setPassword("iloveyou");
        user.setPin("Pin");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepo.findByUsername((String) any())).thenReturn(ofResult);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("iloveyou");
        authRequest.setUsername("janedoe");
        assertThrows(CustomException.class, () -> this.userServiceImpl.authenticate(authRequest));
        verify(this.userRepo).findByUsername((String) any());
    }

    @Test
    void testAuthenticate2() {
        when(this.userRepo.findByUsername((String) any())).thenThrow(new CustomException("An error occurred", 0));

        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("iloveyou");
        authRequest.setUsername("janedoe");
        assertThrows(CustomException.class, () -> this.userServiceImpl.authenticate(authRequest));
        verify(this.userRepo).findByUsername((String) any());
    }

    @Test
    void testAuthenticate3() {
        when(this.userRepo.findByUsername((String) any())).thenReturn(Optional.empty());

        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("iloveyou");
        authRequest.setUsername("janedoe");
        assertThrows(CustomException.class, () -> this.userServiceImpl.authenticate(authRequest));
        verify(this.userRepo).findByUsername((String) any());
    }
}

