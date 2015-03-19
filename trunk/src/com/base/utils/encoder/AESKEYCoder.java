package com.base.utils.encoder;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.litesuits.common.assist.Base64;
import com.mvp.R;
import org.apache.commons.lang.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by aa on 2014/10/24.
 */
public class AESKEYCoder {

    /**
     * AES对称加密算法
     *
     * @ 这里演示的是其Java6.0的实现,理所当然的BouncyCastle也支持AES对称加密算法
     * 另外,我们也可以以AES算法实现为参考,完成RC2,RC4和Blowfish算法的实现
     * @see ===========================================================================================================
     * @see ===========================================================================================================
     * 由于DES的不安全性以及DESede算法的低效,于是催生了AES算法(Advanced Encryption_ Standard)
     * 该算法比DES要快,安全性高,密钥建立时间短,灵敏性好,内存需求低,在各个领域应用广泛
     * 目前,AES算法通常用于移动通信系统以及一些软件的安全外壳,还有一些无线路由器中也是用AES算法构建加密协议
     * ===========================================================================================================
     * 由于Java6.0支持大部分的算法,但受到出口限制,其密钥长度不能满足需求
     * 所以特别需要注意的是:如果使用256位的密钥,则需要无政策限制文件(Unlimited Strength Jurisdiction Policy Files)
     * 不过Sun是通过权限文件local_poblicy.jar和US_export_policy.jar做的相应限制,我们可以在Sun官网下载替换文件,减少相关限制
     * 网址为http://www.oracle.com/technetwork/java/javase/downloads/index.html
     * 在该页面的最下方找到Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 6,点击下载
     * http://download.oracle.com/otn-pub/java/jce_policy/6/jce_policy-6.zip
     * http://download.oracle.com/otn-pub/java/jce/7/UnlimitedJCEPolicyJDK7.zip
     * 然后覆盖本地JDK目录和JRE目录下的security目录下的文件即可
     * ===========================================================================================================
     * 关于AES的更多详细介绍,可以参考此爷的博客http://blog.csdn.net/kongqz/article/category/800296
     */

    //密钥算法
    public static final String KEY_ALGORITHM = "AES";

    //加解密算法/工作模式/填充方式,Java6.0支持PKCS5Padding填充方式,BouncyCastle支持PKCS7Padding填充方式
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    //key  解密私钥，长度不能够小于16位
    private static String key = "IlRzRG112";
    //真实的密钥是:IzRxRlhFpVVzI8y4bR1Ggg==  我这里做了个假的key  这个类反编译可以反出来  可以获得key

    /**
     * 生成密钥
     */
    public static String initkey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM); //实例化密钥生成器
        kg.init(128);                                              //初始化密钥生成器:AES要求密钥长度为128,192,256位
        SecretKey secretKey = kg.generateKey();                    //生成密钥
        return Base64.encodeToString(secretKey.getEncoded(),Base64.DEFAULT);  //获取二进制密钥编码形式
    }


    /**
     * 转换密钥
     */
    public static Key toKey(byte[] key) throws Exception {
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }


    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密后的数据
     */
    public static String encrypt(String data, String key) throws Exception {
        Key k = toKey(Base64.decode(key.getBytes(),Base64.DEFAULT));                           //还原密钥
        //使用PKCS7Padding填充方式,这里就得这么写了(即调用BouncyCastle组件实现)
        //Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);              //实例化Cipher对象，它用于完成实际的加密操作
        cipher.init(Cipher.ENCRYPT_MODE, k);                               //初始化Cipher对象，设置为加密模式
        return Base64.encodeToString(cipher.doFinal(data.getBytes()),Base64.DEFAULT); //执行加密操作。加密后的结果通常都会用Base64编码进行传输
    }

    public static String encrypt(String data) {
        if(StringUtils.isEmpty(data))
        {
            return "";
        }
        Key k = null;                           //还原密钥
        //使用PKCS7Padding填充方式,这里就得这么写了(即调用BouncyCastle组件实现)
        //Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        Cipher cipher = null;              //实例化Cipher对象，它用于完成实际的加密操作
        try {
            k = toKey(Base64.decode(key.getBytes(),Base64.DEFAULT));
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, k);          //初始化Cipher对象，设置为加密模式
            return Base64.encodeToString(cipher.doFinal(data.getBytes()),Base64.DEFAULT); //执行加密操作。加密后的结果通常都会用Base64编码进行传输
        }
        catch(InvalidKeyException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 解密后的数据
     */
    public static String decrypt(String data, String key) throws Exception {
        Key k = toKey(Base64.decode(key.getBytes(),Base64.DEFAULT));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);                          //初始化Cipher对象，设置为解密模式
        return new String(cipher.doFinal(Base64.decode(data,Base64.DEFAULT))); //执行解密操作
    }

    public static String decrypt(String data) {
        if(StringUtils.isBlank(data))
        {
            return "";
        }
        try {
            Key k = toKey(Base64.decode(key.getBytes(),Base64.DEFAULT));
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, k);                          //初始化Cipher对象，设置为解密模式
            return new String(cipher.doFinal(Base64.decode(data,Base64.DEFAULT))); //执行解密操作
        }
        catch(InvalidKeyException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //得到真实的key
    public static void getRealKey(Context context)
    {
        final Resources res = context.getResources();
        key = res.getString(R.string.DES_Key);
    }

    //测试加密运行所需要消耗的时间
    public static void testNeedTime(String encodeRes){
        if (StringUtils.isBlank(encodeRes))
            return;

        long start = System.currentTimeMillis();
        String encryptData = null;
        encryptData = encrypt(encodeRes);
        System.out.println("加密：" + encryptData);

        long end = System.currentTimeMillis();
        Log.d("加密耗时:", (end - start) + "");

        String decryptData = decrypt(encryptData);
        System.out.println("解密: " + decryptData);

        end = System.currentTimeMillis();

        Log.d("解密耗时:", (end - start) + "");
    }


}
