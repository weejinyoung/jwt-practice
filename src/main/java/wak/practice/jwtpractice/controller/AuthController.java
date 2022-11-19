package wak.practice.jwtpractice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wak.practice.jwtpractice.dto.LoginDto;
import wak.practice.jwtpractice.dto.TokenDto;
import wak.practice.jwtpractice.jwt.JwtFilter;
import wak.practice.jwtpractice.jwt.JwtProvider;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        //authenticate()가 실행될 때 loadUserByUserName()가 실행된다
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        //생성된 authentication을 context에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //만들어진 authentication으로 토큰 생성
        String jwt = jwtProvider.createToken(authentication);

        //토큰을 Response Header에 넣어줌
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        //TokenDto를 Response Body에 넣어줌, 그리고 return
        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}