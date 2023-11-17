package com.vanchondo.security.service;

import com.vanchondo.security.configs.properties.LoginConfiguration;
import com.vanchondo.security.dto.CurrentUserDTO;
import com.vanchondo.security.dto.TokenDTO;
import com.vanchondo.security.dto.UserInfoForTokenDTO;
import com.vanchondo.security.util.Constants;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SecurityService {

  private final LoginConfiguration loginConfiguration;

  public TokenDTO generateToken(UserInfoForTokenDTO userInfo) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.MINUTE, loginConfiguration.getExpirationToken());


    return new TokenDTO(Jwts.builder()
      .setIssuer(loginConfiguration.getIssuer())
      .setSubject(userInfo.getEmail())
      .claim(Constants.CLAIM_USERNAME_PROPERTY, userInfo.getUsername())
//            .claim("role", currentUser.getRole().getName())
//            .claim("authorities", getListOfAuthorities(currentUser.getRole().getAuthorities()))
//            .claim("store", currentUser.getStore().toString())
      .setIssuedAt(new Date())
      .setExpiration(cal.getTime())
      .signWith(
        getSigningKey(loginConfiguration.getSecretKey()),
        SignatureAlgorithm.HS256
      )
      .compact());
  }

  public static Key getSigningKey(String secretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
