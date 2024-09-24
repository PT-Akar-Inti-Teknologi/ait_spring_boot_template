package org.ait.project.guideline.example.shared.utils.response.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.config.properties.ApplicationProperties;
import org.ait.project.guideline.example.shared.constant.enums.TokenTypeEnum;
import org.ait.project.guideline.example.shared.dto.data.JwtData;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUtils {

  private final ApplicationProperties properties;

  public String createAccessToken(Integer userId, String name, String email, List<String> authorities) {
    return createToken(
        new JwtData(userId, name, email, TokenTypeEnum.ACCESS_TOKEN.name(), authorities),
        properties.getJwtProperties().getAccessTokenExpirationTime());
  }

  public String createRefreshToken(Integer userId, String name, String email) {
    return createToken(
        new JwtData(userId, name, email, TokenTypeEnum.REFRESH_TOKEN.name(), null),
        properties.getJwtProperties().getRefreshTokenExpirationTime());
  }

  private String createToken(JwtData jwtData, Long expiration) {
    return Jwts.builder().subject(String.valueOf(jwtData.getUserId()))
            .claim("name", jwtData.getName())
            .claim("email", jwtData.getEmail())
            .claim("type", jwtData.getType())
            .claim("authorities", jwtData.getAuthorities())
            .expiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(secretKey(properties.getJwtProperties().getSecret())).compact();
  }


  public JwtData parseToken(String token)
      throws MalformedJwtException, SignatureException, ExpiredJwtException {
    Jws<Claims> claimsJws =
        Jwts.parser().verifyWith(secretKey(properties.getJwtProperties().getSecret()))
            .build().parseSignedClaims(token);
    return new JwtData(Integer.parseInt(claimsJws.getPayload().getSubject()),
        claimsJws.getPayload().get("name", String.class),
        claimsJws.getPayload().get("email", String.class),
        claimsJws.getPayload().get("type", String.class),
        claimsJws.getPayload().get("authorities", ArrayList.class));
  }

  private SecretKey secretKey(String key) {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
  }

  /**
   * Generates a new secret key.
   * @return The generated secret key as a hexadecimal string.
   */

  private static String createNewSecretKey() {
    byte[] key = new byte[64];
    SecureRandom secureRandom = new SecureRandom();
    secureRandom.nextBytes(key);

    // Print the key as a hexadecimal string
    StringBuilder keyString = new StringBuilder();

    for
    (byte b : key) {
      keyString.append(String.format("%02x",b));
    }
    return keyString.toString();
  }

}
