package com.devteria.idetityservice.service;

import com.devteria.idetityservice.dto.request.AuthenticationRequest;
import com.devteria.idetityservice.dto.request.IntrospectRequest;
import com.devteria.idetityservice.dto.response.AuthenticationResponse;
import com.devteria.idetityservice.dto.response.IntrospectResponse;
import com.devteria.idetityservice.entity.User;
import com.devteria.idetityservice.exception.AppException;
import com.devteria.idetityservice.exception.ErrorCode;
import com.devteria.idetityservice.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal// de no kh inject vao contructe cua clombok
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY ;

    public IntrospectResponse introspect(IntrospectRequest request)
    throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

//        token cua  ng dung
        SignedJWT signedJWT = SignedJWT.parse(token);

//        lay ra han cua token
        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();


//        tra ve true hoac flase
         var verified =  signedJWT.verify(verifier);

//         tra ve introspectresponse
         return  IntrospectResponse.builder()
                 .valid( verified && expityTime.after(new Date()))
                 .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOTFOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        so sanh pass ng dung nhap vaao va pass o dataabase
        boolean authenticated =  passwordEncoder.matches(request.getPassword(),
                user.getPassword());
//        neu dang nhap khong thanh cong
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        } else {
//            dang nhap thanh cong
            var tokken = generateToken(user);
            return AuthenticationResponse.builder()
                    .token(tokken)
                    .authenticated(true)
                    .build();
        }
    }

//    tao token bang username
    private String generateToken(User user){

//        tao header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
//      tao body
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("devteria.com") // token nay dc issuer tu ai
                .issueTime(new Date()) // thoi diem hien tai
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS)
                .toEpochMilli())) // thoi han cua token, het han sau 1h
                .claim("scope", buildScope(user))
                .build();
//      tao pay load
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

//        built thong tin token
        JWSObject jwsObject = new JWSObject(header,payload);

//      ky token
        try {
//            hash content nay
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
//            tra ve kieu string
            return jwsObject.serialize();
        }catch (JOSEException e){
//            log.error("Cannnot create token",e);
            throw new RuntimeException(e);
        }


    }

    private String buildScope (User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
//        kiem tra xem list roles co rong  hay khong
//        neu khong rong
        if (!CollectionUtils.isEmpty(user.getRoles()))
        {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
//                neu list khong rong thi add toan bo vao scope
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
                }
            }  );
        }
    return  stringJoiner.toString();
    }
}
