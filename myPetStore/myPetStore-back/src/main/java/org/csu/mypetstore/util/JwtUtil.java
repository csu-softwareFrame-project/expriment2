package org.csu.mypetstore.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.Test;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;


public class JwtUtil {
    //私钥
    public static final String SECRET = "jYFKkdf1Hfalk1ecASLKDJFrfdSJFyiji5ZS2dsfjie9LiHhlsg12=";
    //过期时间2h(ms)，超过这个时间发一个新的token
    public static final long EXPIRE_TIME = 2*60*60*1000;
    //完全失效时间2d(ms)，超过这个时间token失效且不再发新的token
    public static final long FAILURE_TIME = 2*24*60*60*1000;

    @Test
    public void test(){
        //随机生成密钥
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        //按照自己的密钥生成加密钥匙
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
        //获取加密的key
//        String enc_key = Base64.getEncoder().encodeToString(key.getEncoded());
//        System.out.println(enc_key);
        //有效时间
        Date expiresDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //生成token
        String token = Jwts.builder().setSubject("asdklfj.%^aosidfjkl").setExpiration(expiresDate).signWith(key).compact();
//        System.out.println(token);

        //比较token
        String dec = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
//        if(dec.equals("alksjdflkaj")) System.out.println(true);
        System.out.println(dec);
        System.out.println(isExpirate(token));
    }

    //根据keyword产生token
    public static String generate(String keyword){
        //按照自己的密钥生成加密钥匙
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
        //有效时间
        Date expiresDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return  Jwts.builder().setSubject(keyword).setExpiration(expiresDate).signWith(key).compact();
    }

    public static String generateFail(String keyword){
        //按照自己的密钥生成加密钥匙
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
        //有效时间
        Date expiresDate = new Date(System.currentTimeMillis() + FAILURE_TIME);
        return  Jwts.builder().setSubject(keyword).setExpiration(expiresDate).signWith(key).compact();
    }

    //判断token是否过期
    public static boolean isExpirate(String token){
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
        try{
            Date deadDate = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration();
        }catch (ExpiredJwtException e){
            return true;
        }
        Date deadDate = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration();
        Date now = new Date(System.currentTimeMillis());
        if(now.before(deadDate)) return false;
        else return true;
    }

    //解密返回明文
    public static String dec(String token){
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }
}

