package com.base.utils.encoder;

/**
 * Created by aa on 2014/10/24.
 */

import com.base.config.GlobalConfig;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * DESEncryption实现了DES对称加密
 *
 * @author PacoYang
 *
 */
public class DESEncryption {
    /**
     * 创建秘钥函数,并将秘钥保存在文件key.txt中
     *
     */
    public static void setKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("AES");// 设定加密类型为三重DES加密，这里可以改为DES或AES加密，请大家看API文档
        kg.init(168);// 设定秘钥长度为112，三重DES的秘钥长度可选112或168
        SecretKey k = kg.generateKey();// 生成秘钥
        // 下面保存生成好的秘钥
        FileOutputStream f = new FileOutputStream(GlobalConfig.dataPath+"key1.txt");
        ObjectOutputStream ops = new ObjectOutputStream(f);
        ops.writeObject(k);

    }



    /**
     * 生成密钥
     * 自动生成AES128位密钥
     * 传入保存密钥文件路径
     * filePath 表示文件存储路径加文件名；例如d:\aes.txt
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.IOException
     */
    public static void getAutoCreateAESKey() throws NoSuchAlgorithmException, IOException {
        String filePath=GlobalConfig.dataPath+"key2.txt";
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
        SecretKey sk = kg.generateKey();
        byte[] b = sk.getEncoded();
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(b);
        fos.flush();
        fos.close();
    }

    /**
     * 获取秘钥函数，根据URL路径读取文件中的秘钥
     * @param url 秘钥路径
     * @return 返回秘钥
     */
    public static Key getKey(String url) throws Exception {
        FileInputStream f = new FileInputStream(url);
        ObjectInputStream ips = new ObjectInputStream(f);
        Key k = (Key) ips.readObject();
        return k;
    }

    /**
     * 加密函数
     * @param k 秘钥
     * @param code 要加密的代码
     * @return 返回加密后的内容
     */
    public static String encrypt(Key k, String code) throws Exception {
        Cipher cp = Cipher.getInstance("AES");
        cp.init(Cipher.ENCRYPT_MODE, k);
        byte codebyte[] = code.getBytes("UTF8");
        byte encode[] = cp.doFinal(codebyte);
        return parseByte2HexStr(encode);
    }

    /**
     * 解密函数
     * @param k 秘钥
     * @param code 要解密的代码
     * @return 返回解密后的内容
     */
    public static String decrypt(Key k, String code) throws Exception {
        Cipher cp = Cipher.getInstance("AES");
        cp.init(Cipher.DECRYPT_MODE, k);
        byte codebyte[] = parseHexStr2Byte(code);
        byte decode[] = cp.doFinal(codebyte);
        return new String(decode);
    }


    // main函数用于测试
    public static void main(String[] args) throws Exception {
        String s = "abc123abc321";
        DESEncryption.setKey();
        Key k = DESEncryption.getKey("key.txt");
        String encode = DESEncryption.encrypt(k,s);
        System.out.println(encode);
        String decode = DESEncryption.decrypt(k,encode);
        System.out.println(decode);
    }


    /**
     * 将二进制转换成16进值制 ，防止byte[]数字转换成string类型时造成的数据损失
     * @param buf
     * @return 返回16进制转换成的string
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();

    }

    /**
     * 将16进制转换为二进制
     * @param hexStr 16进制的数组转换成String类型再传过来的参数
     * @return 转换回来的二进制数组
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}