package org.example.portfolio.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.portfolio.dtos.AuthenticationRequest;
import org.example.portfolio.dtos.AuthenticationResponse;
import org.example.portfolio.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@RequestParam(required = false) MultipartFile image,@RequestParam String userData){

        try {
            return ResponseEntity.ok(authenticationService.createOwner(userData,image));
        } catch (JsonProcessingException e) {
            System.out.println(e);
            return new ResponseEntity<>("The user data is not valid ,you have to enter (email,firstName,lastName, password)",HttpStatusCode.valueOf(400));
        } catch (IOException e) {
            System.out.println(e);
            return new ResponseEntity<>("Error while storing the image",HttpStatusCode.valueOf(400));
        }catch (IllegalArgumentException e) {
            System.out.println(e);
            return new ResponseEntity<>(e.getMessage(),HttpStatusCode.valueOf(400));
          }catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("Error while Creating the user",HttpStatusCode.valueOf(400));
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @RequestBody AuthenticationRequest authenticationRequest
            )  {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response,
             @CookieValue("refreshToken") String refreshToken
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }





}
