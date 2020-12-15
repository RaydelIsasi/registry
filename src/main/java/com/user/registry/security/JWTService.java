package com.user.registry.security;


import com.user.registry.controller.UsuarioController;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class JWTService {


    private static final Logger LOGGER = LoggerFactory.getLogger(JWTService.class);

    public static final String ISSUER_INFO = "https://userregistry/";
    public static final String SECRET_KEY = "secret_key";
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000;

    public String generateToken(UserDetails userDetails) throws IOException, ServletException {
        LOGGER.info("Initiating generation of Bearer Token");
        Map<String, Object> claims = new LinkedHashMap<>();
        return createToken(claims, userDetails.getUsername());


    }

    public Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);


    }

    public String extractUser(String token) {
        LOGGER.info("Extracting user from  Token");
        return extractClaim(token, Claims::getSubject);


    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsresolver) {

        final Claims claims = extractAllClaims(token);
        return claimsresolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private String createToken(Map<String, Object> claims, String username) throws IOException, ServletException {

        String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(ISSUER_INFO)
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
        LOGGER.info("Finishing token generation");
        return token;
    }

    private Boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());

    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        LOGGER.info("Validating token");
        String username = extractUser(token);
        return username.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token);


    }
}
