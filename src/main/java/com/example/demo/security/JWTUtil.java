package com.example.demo.security;

import com.example.demo.config.SecretKeyReader;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JWTUtil {
    private JWTUtil(){

    }

    public static String generateToken(String username){
        return Jwts
                .builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + 3_600_000))
                .signWith(getSecretKey())
                .compact();
    }

    private static SecretKey getSecretKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(SecretKeyReader.getSecretKeyProperty());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static boolean validateToken(String token){
        getAllClaimsFromToken(token);
        return true;
    }

    private static Claims getAllClaimsFromToken(String token) {
        try{
            return Jwts
                    .parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String extractUsername(String token){
        Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject();
    }
}
