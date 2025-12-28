package org.example.portfolio.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.portfolio.conf.JwtService;
import org.example.portfolio.dtos.AccountCreationDto;
import org.example.portfolio.dtos.AuthenticationRequest;
import org.example.portfolio.dtos.AuthenticationResponse;
import org.example.portfolio.entities.Image;
import org.example.portfolio.entities.JwtTokenObject;
import org.example.portfolio.entities.Owner;
import org.example.portfolio.enums.Role;
import org.example.portfolio.enums.SavingPlace;
import org.example.portfolio.enums.TokenType;
import org.example.portfolio.repos.UserRepository;
import org.example.portfolio.utils.ImageManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private UserRepository ownerRepo;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private ImageManagement imageManagement;
    @Autowired
    private final ObjectMapper mapper;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse createOwner(String userData, MultipartFile image) throws IOException {
        AccountCreationDto accountCreationDto=parseUserData(userData);
        if(accountCreationDto.getPassword().length()<8) throw new IllegalArgumentException("Password length must be 8 characters at least");
        if(accountCreationDto.getFirstName() == null ||accountCreationDto.getFirstName().length()< 4||
                accountCreationDto.getLastName() == null ||accountCreationDto.getLastName().length()<4)
            throw new IllegalArgumentException("First and last names must be provided and with at least 4 characters each");
        if(image!=null) {
            accountCreationDto.setImageLink(imageManagement.saveImageLocally(image));
        }
        Owner owner= Owner.builder()
                .email(accountCreationDto.getEmail())
                .password(passwordEncoder.encode(accountCreationDto.getPassword()))
                .firstName(accountCreationDto.getFirstName())
                .lastName(accountCreationDto.getLastName())
                .image(Image.builder()
                        .savingPlace(SavingPlace.LOCAL_FS)
                        .defaultUrl(accountCreationDto.getImageLink())
                        .build())
                .role(Role.ADMIN)
                .build();

        owner.setLastLogin(new Date());
        ownerRepo.save(owner);
        Map<String, Object> tokenDetails=new HashMap<>();
        tokenDetails.put("role",owner.getRole());
        String accessToken = jwtService.generateToken(owner,TokenType.ACCESS_TOKEN, 30,tokenDetails);
        JwtTokenObject refreshToken = jwtService.generateJWTRefreshToken(owner);
        jwtService.revokeOtherJWTRefreshTokens(owner);
        jwtService.save(refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getValue())
                .build();

    }


    public AuthenticationResponse authenticate(AuthenticationRequest authenticationDto){
        Owner owner = ownerRepo.findByEmail(authenticationDto.getEmail()).orElse(null);
        if(owner==null||!passwordEncoder.matches(authenticationDto.getPassword(),owner.getPassword())){
            throw new BadCredentialsException("email or password is not correct");
        }
        Map<String, Object> tokenDetails=new HashMap<>();
        tokenDetails.put("role",owner.getRole());
        String accessToken = jwtService.generateToken(owner,TokenType.ACCESS_TOKEN, 30,tokenDetails);
        JwtTokenObject refreshToken = jwtService.generateJWTRefreshToken(owner);
        jwtService.revokeOtherJWTRefreshTokens(owner);
        jwtService.save(refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getValue())
                .build();

    }


    private AccountCreationDto parseUserData(String userData) throws JsonProcessingException {
        return  mapper.readValue(userData,AccountCreationDto.class);
    }



    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BadCredentialsException("no Bearer token found in the header of the request");
        }
        try{
            refreshToken = authHeader.substring(7);
            System.out.println("Reaching here 1------");
            userEmail = jwtService.extractUsername(refreshToken);
            if (userEmail != null) {
                var user = this.ownerRepo.findByEmail(userEmail)
                        .orElseThrow(()->new BadCredentialsException("no user with specified email"));
                if (jwtService.isTokenValid(refreshToken, user)) {
                    System.out.println("Reaching here 3------");

                    Map<String, Object> tokenDetails=new HashMap<>();
                    tokenDetails.put("role",user.getRole());
                    String accessToken =jwtService.generateToken( user, TokenType.ACCESS_TOKEN, 30,tokenDetails);
                    var authResponse = AuthenticationResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
                    new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                }
            }
        }catch(BadCredentialsException ex){
            response.setStatus(401);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // ✅ Fix: Add CORS headers
            response.setHeader("Access-Control-Allow-Origin", "*"); // Allow all origins (change if needed)
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

            String jsonResponse = String.format("{\"error\": \"%s\"}", ex.getMessage());
            response.getWriter().write(jsonResponse);
        }catch(ExpiredJwtException ex){
            response.setStatus(401);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // ✅ Fix: Add CORS headers
            response.setHeader("Access-Control-Allow-Origin", "*"); // Allow all origins (change if needed)
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

            String jsonResponse = String.format("{\"error\": \"%s\"}", "jwt expired");
            response.getWriter().write(jsonResponse);
        }
    }



}
