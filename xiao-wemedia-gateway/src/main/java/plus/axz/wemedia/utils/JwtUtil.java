package plus.axz.wemedia.utils;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * @author xiaoxiang
 * description  加密工具类
 */
public class JwtUtil {
    // TOKEN的有效期一天（S）
    private static final int TOKEN_TIME_OUT = 86400;
    // 加密KEY
    private static final String TOKEN_ENCRY_KEY = "AxzXiAo9ZNIoJiUXiaNg1PLdi6NI";
    // 最小刷新间隔(S)
    private static final int REFRESH_TIME = 300;

    // 生产ID
    public static String getToken(Long id) {
        Map<String, Object> claimMaps = new HashMap<>();
        claimMaps.put("id", id);
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                //设置id
                .setId(UUID.randomUUID().toString())
                //签发时间
                .setIssuedAt(new Date(currentTime))
                //说明
                .setSubject("system")
                //签发者信息
                .setIssuer("axz")
                //接收用户
                .setAudience("app")
                //数据压缩方式
                .compressWith(CompressionCodecs.GZIP)
                //加密方式
                .signWith(SignatureAlgorithm.HS512, generalKey())
                //过期时间戳
                .setExpiration(new Date(currentTime + TOKEN_TIME_OUT * 1000))
                //cla信息(手动设置的内容)
                .addClaims(claimMaps)
                .compact();
    }

    /**
     * 获取token中的claims信息
     */
    private static Jws<Claims> getJws(String token) {
        return Jwts.parser()
                .setSigningKey(generalKey())
                .parseClaimsJws(token);
    }

    /**
     * 获取payload body信息
     */
    public static Claims getClaimsBody(String token) {
        try {
            return getJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }
    }

    /**
     * 获取hearder body信息
     */
    public static JwsHeader getHeaderBody(String token) {
        return getJws(token).getHeader();
    }

    /**
     * 是否过期
     *
     * @return -1：有效，0：有效，1：过期，2：过期
     */
    public static int verifyToken(Claims claims) {
        if (claims == null) {
            return 1;
        }
        try {
            claims.getExpiration()
                    .before(new Date());
            // 需要自动刷新TOKEN
            if ((claims.getExpiration().getTime() - System.currentTimeMillis()) > REFRESH_TIME * 1000) {
                return -1;
            } else {
                return 0;
            }
        } catch (ExpiredJwtException ex) {
            return 1;
        } catch (Exception e) {
            return 2;
        }
    }

    /**
     * 由字符串生成加密key
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(TOKEN_ENCRY_KEY.getBytes());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }


    //测试
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("id", "1");
        //生成带id的jwt字符串
        System.out.println(JwtUtil.getToken(1L));
        // 解析生成的jwt字符串
        Jws<Claims> jws = JwtUtil.getJws("eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAADWL0QrDIAwA_yXPFWLULO3f2NmChYIQC93G_n3xYW93HPeBo1dYAHcWPyM5eabiYgrFzeuDHUaiIJ444Q4T1Nxh8RwFKRDhBHqtdutL-3aOrmqa77dxvsrg1oy3u_0_lvFVa_77A9NXz4p-AAAA.99bgkOpc9Kt8bzIt7Mm2uW1Vb3j4W4dlg6rkS8DwrzfiRD1_711sWobi7fkR-kD1ZYriJE-RS7oUi7-LqdJVyg");
        //从jws中，获取自定义的clqims
        Claims claims = jws.getBody();
        //{jti=7973c09a-091a-407b-9ba6-3b31f0ed1b37, iat=1639293325, sub=system, iss=axz, aud=app, exp=1639296925, id=1}
        System.out.println(claims);
        System.out.println(claims.get("id"));

    }
}
