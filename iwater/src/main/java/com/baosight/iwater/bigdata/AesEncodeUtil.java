package com.baosight.iwater.bigdata;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

public class AesEncodeUtil {
	static Cipher cipher;

	static SecretKey secretKey;

	// 初始向量
	public static final String VIPARA = "aed@ss_c%dja#X21"; // AES 为16bytes. DES
															// 为8bytes

	// 编码方式
	public static final String bm = "UTF-8";

	// 私钥
	private static final String ASE_KEY = "aabbccddeeffgghh"; // AES固定格式为128/192/256
																// bits.即：16/24/32bytes。DES固定格式为128bits，即8bytes。

	private static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";

	private static String KEY_ALGORITHM = "AES";

	/** 
	 * 加密 
	 *  
	 * @param content 需要加密的内容 
	 * @param password  加密密码 
	 * @return 
	 */  
	public static byte[] encrypt(String content, String password) {  
	        try {             
	                KeyGenerator kgen = KeyGenerator.getInstance("AES");  
	                kgen.init(128, new SecureRandom(password.getBytes()));  
	                SecretKey secretKey = kgen.generateKey();  
	                byte[] enCodeFormat = secretKey.getEncoded();  
	                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
	                Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
	                byte[] byteContent = content.getBytes("utf-8");  
	                cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
	                byte[] result = cipher.doFinal(byteContent);  
	                return result; // 加密  
	        } catch (NoSuchAlgorithmException e) {  
	                e.printStackTrace();  
	        } catch (NoSuchPaddingException e) {  
	                e.printStackTrace();  
	        } catch (InvalidKeyException e) {  
	                e.printStackTrace();  
	        } catch (UnsupportedEncodingException e) {  
	                e.printStackTrace();  
	        } catch (IllegalBlockSizeException e) {  
	                e.printStackTrace();  
	        } catch (BadPaddingException e) {  
	                e.printStackTrace();  
	        }  
	        return null;  
	}  

	/**解密 
	 * @param content  待解密内容 
	 * @param password 解密密钥 
	 * @return 
	 */  
	public static String decrypt(byte[] content, String password) {  
	        try {  
	                 KeyGenerator kgen = KeyGenerator.getInstance("AES");  
	                 kgen.init(128, new SecureRandom(password.getBytes()));  
	                 SecretKey secretKey = kgen.generateKey();  
	                 byte[] enCodeFormat = secretKey.getEncoded();  
	                 SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");              
	                 Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
	                cipher.init(Cipher.DECRYPT_MODE, key);// 初始化  
	                byte[] result = cipher.doFinal(content);  
	                return new String(result,bm); // 加密  
	        } catch (NoSuchAlgorithmException e) {  
	                e.printStackTrace();  
	        } catch (NoSuchPaddingException e) {  
	                e.printStackTrace();  
	        } catch (InvalidKeyException e) {  
	                e.printStackTrace();  
	        } catch (IllegalBlockSizeException e) {  
	                e.printStackTrace();  
	        } catch (BadPaddingException e) {  
	                e.printStackTrace();  
	        } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        return null;  
	}  

	/**
	 * 加密
	 * 
	 * @param cleartext
	 * @return
	 */
	public static byte[] ACPEncrypt(String cleartext) {
		// 加密方式： AES128(CBC/PKCS5Padding) + Base64, 私钥：aabbccddeeffgghh
		try {
			IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
			// 两个参数，第一个为私钥字节数组， 第二个为加密方式 AES或者DES
			SecretKeySpec key = new SecretKeySpec(ASE_KEY.getBytes(), "AES");
			// 实例化加密类，参数为加密方式，要写全
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // PKCS5Padding比PKCS7Padding效率高，PKCS7Padding可支持IOS加解密
			// 初始化，此方法可以采用三种方式，按加密算法要求来添加。（1）无第三个参数（2）第三个参数为SecureRandom random
			// = new
			// SecureRandom();中random对象，随机数。(AES不可采用这种方法)（3）采用此代码中的IVParameterSpec
			cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
			// 加密操作,返回加密后的字节数组，然后需要编码。主要编解码方式有Base64, HEX,
			// UUE,7bit等等。此处看服务器需要什么编码方式
			byte[] encryptedData = cipher.doFinal(cleartext.getBytes(bm));

			return encryptedData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解密
	 * 
	 * @param encrypted
	 * @return
	 */
	public static String ACPDecrypt(byte[] encrypted) {
		try {
			byte[] byteMi = encrypted;
			IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
			SecretKeySpec key = new SecretKeySpec(ASE_KEY.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			// 与加密时不同MODE:Cipher.DECRYPT_MODE
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			byte[] decryptedData = cipher.doFinal(byteMi);
			return new String(decryptedData, bm);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 测试
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String content = "98.5674";

		// 加密
		System.out.println("加密前：" + content);
		byte[] encryptResult = encrypt(content,"qwyqiqw");

		System.out.println("加密后：" + new BASE64Encoder().encode(encryptResult));
		// 解密
		String decryptResult = decrypt(encryptResult,"qwyqiqw");
		System.out.println("解密后：" + new String(decryptResult));

	}
}
