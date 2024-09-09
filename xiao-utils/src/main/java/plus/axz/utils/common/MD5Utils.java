package plus.axz.utils.common;

import lombok.extern.log4j.Log4j2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author xiaoxiang
 * description MD5加密工具类
 */
@Log4j2
public class MD5Utils {

    /**
     * MD5加密
     * @param str 待加密字符串
     * @return 加密后的字符串
     */
    public static String encode(String str) {
        try {
            //创建具有指定算法名称的摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要
            md.update(str.getBytes());
            //进行哈希计算并返回一个字节数组
            byte[] mdBytes = md.digest();
            StringBuilder hash = new StringBuilder();
            //循环字节数组
            for (byte mdByte : mdBytes) {
                int temp;
                //如果有小于0的字节,则转换为正数
                if (mdByte < 0) {
                    temp = 256 + mdByte;
                } else {
                    temp = mdByte;
                }
                if (temp < 16) {
                    hash.append("0");
                }
                //将字节转换为16进制后，转换为字符串
                hash.append(Integer.toString(temp, 16));
            }
            return hash.toString();
        } catch (NoSuchAlgorithmException e) {
          log.error("MD5加密失败", e);
        }
        return "";
    }

    public static String encodeWithSalt(String numStr, String salt) {
        return encode(encode(numStr) + salt);
    }

    public static void main(String[] args) {
        //098f6bcd4621d373cade4e832627b4f6
        System.out.println(encode("test"));
        //5f1d7a84db00d2fce00b31a7fc73224f
        System.out.println(encodeWithSalt("123456","123456"));
    }
}
