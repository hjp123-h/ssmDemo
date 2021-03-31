package com.hjp.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


public class JwtUtils {

    /**
     * @param EXPIRE 过期时间
     * @param APP_SECRET 密钥
     * @author: Hjp
     */

    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    public static final String APP_SECRET = "ukc8PDbRigUDwY6pSFfWus2jZWLPHO";

    /**
     * @description: 生成Token字符串
     * @param id 用户id
     * @param nickname 用户昵称
     * @return: Token字符串
     * @author: Hjp
     */

    public static String getJwtToken(String id, String nickname){

        String JwtToken = Jwts.builder()
                //设置Token头信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                //设置分类
                .setSubject("guli-user")
                //获取当前时间
                .setIssuedAt(new Date())
                //设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))

                //设置用户id
                .claim("id", id)
                //设置用户昵称
                .claim("nickname", nickname)

                //哈希签名
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) {
                return false;
            }
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        //获取前端cookie
        Cookie[] cookies = request.getCookies();
        //判断cookies是否为空
        if (cookies != null){
            //遍历这个cookie
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals("token")){
                    //获取这个cookie中的id
                    String value = cookie.getValue();
                    Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(value);
                    Claims claims = claimsJws.getBody();
                    return (String)claims.get("id");
                }

            }
        }
        return "";
    }
}
