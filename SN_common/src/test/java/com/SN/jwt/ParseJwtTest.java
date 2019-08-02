package com.SN.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;
import java.util.logging.SimpleFormatter;

public class ParseJwtTest {
    public static void main(String[] args) {
        Claims claims = Jwts.parser().setSigningKey("itcast")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLlsI_pqawiLCJpYXQiOjE1NjMzNjA0NzEsImV4cCI6MTU2MzM2MDUzMSwicm9sZSI6ImFkbWluIn0.wLQr7vwsUsREmHa_zse5XBWfHdC8cbsocDaEW8TXyZI")
                .getBody();
        System.out.println("用户id:"+claims.getId());
        System.out.println("用户名:"+claims.getSubject());
        System.out.println("登录时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("过期时间:"+claims.getExpiration());
        System.out.println("角色："+claims.get("role"));
    }
}
