package com.viasat.wildblue.common.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


/**
 * Implementation of simple encryption/decryption
 */
public class WBEncryptor
{
    /** key values */
    private static final byte[] KEY = { 26, 93, 121, 26, 64, -45, 84, 112 };

    /** Encryption method */
    private static final String ENCRYPTION_METHOD = "DES/ECB/PKCS5Padding";

    /** keys */
    private byte[] m_key;

    /**
     * Constructor
     */
    public WBEncryptor()
    {
        this(KEY);
    }

    /**
     * Constructor
     *
     * @param  key
     */
    public WBEncryptor(byte[] key)
    {
        m_key = key;
    }

    /**
     * convenience method
     *
     * @param   orig
     *
     * @return  the decrypted string
     *
     * @throws  NoSuchAlgorithmException
     * @throws  NoSuchPaddingException
     * @throws  InvalidKeyException
     * @throws  BadPaddingException
     * @throws  IllegalBlockSizeException
     * @throws  NoSuchProviderException
     * @throws  UnsupportedEncodingException
     */
    public String decrypt(String orig) throws NoSuchAlgorithmException,
        NoSuchPaddingException, InvalidKeyException, BadPaddingException,
        IllegalBlockSizeException, UnsupportedEncodingException,
        NoSuchProviderException
    {
        byte[] origBytes = orig.getBytes("ASCII");
        byte[] decodedArray = Base64.decodeBase64(origBytes);
        byte[] decryptedBytes = decrypt(decodedArray);

        String decryptedString = new String(decryptedBytes, "ASCII");

        return decryptedString;
    }

    /**
     * decrypt the given bytes
     *
     * @param   orig
     *
     * @return  decrypted bytes
     *
     * @throws  NoSuchAlgorithmException
     * @throws  NoSuchPaddingException
     * @throws  InvalidKeyException
     * @throws  BadPaddingException
     * @throws  IllegalBlockSizeException
     * @throws  NoSuchProviderException
     */
    public byte[] decrypt(byte[] orig) throws NoSuchAlgorithmException,
        NoSuchPaddingException, InvalidKeyException, BadPaddingException,
        IllegalBlockSizeException, NoSuchProviderException
    {
        byte[] answer = null;

        //Cipher desCipher = Cipher.getInstance(ENCRYPTION_METHOD);
        Cipher desCipher = Cipher.getInstance(ENCRYPTION_METHOD, "SunJCE");

        desCipher.init(Cipher.DECRYPT_MODE, getKey());

        answer = desCipher.doFinal(orig);

        return answer;
    }

    /**
     * convenience method for encrypting Strings
     *
     * @param   orig
     *
     * @return  the encrypted string
     *
     * @throws  NoSuchAlgorithmException
     * @throws  NoSuchPaddingException
     * @throws  InvalidKeyException
     * @throws  BadPaddingException
     * @throws  IllegalBlockSizeException
     * @throws  NoSuchProviderException
     * @throws  UnsupportedEncodingException
     */
    public String encrypt(String orig) throws NoSuchAlgorithmException,
        NoSuchPaddingException, InvalidKeyException, BadPaddingException,
        IllegalBlockSizeException, UnsupportedEncodingException,
        NoSuchProviderException
    {
        return encrypt(orig, false);
    }

    /**
     * encrypt the given bytes
     *
     * @param   orig
     *
     * @return  encrypted
     *
     * @throws  NoSuchAlgorithmException
     * @throws  NoSuchPaddingException
     * @throws  InvalidKeyException
     * @throws  BadPaddingException
     * @throws  IllegalBlockSizeException
     * @throws  NoSuchProviderException
     */
    public byte[] encrypt(byte[] orig) throws NoSuchAlgorithmException,
        NoSuchPaddingException, InvalidKeyException, BadPaddingException,
        IllegalBlockSizeException, NoSuchProviderException
    {
        byte[] answer = null;

        //Cipher desCipher = Cipher.getInstance(ENCRYPTION_METHOD);
        Cipher desCipher = Cipher.getInstance(ENCRYPTION_METHOD, "SunJCE");

        desCipher.init(Cipher.ENCRYPT_MODE, getKey());

        answer = desCipher.doFinal(orig);

        return answer;
    }

    /**
     * Convenience method for encrypting Strings.
     *
     * @param   orig     The original string to encrypt.
     * @param   urlSafe  If <code>true</code> then the returned String is safe
     *                   to put in a URL (e.g. slashes and plus signs will be
     *                   replaced), otherwise perform "normal" encryption.
     *
     * @return  The encrypted String.
     *
     * @throws  NoSuchProviderException
     * @throws  IllegalBlockSizeException
     * @throws  BadPaddingException
     * @throws  NoSuchPaddingException
     * @throws  NoSuchAlgorithmException
     * @throws  InvalidKeyException
     * @throws  UnsupportedEncodingException
     */
    public String encrypt(String orig, boolean urlSafe)
        throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, BadPaddingException,
            IllegalBlockSizeException, NoSuchProviderException,
            UnsupportedEncodingException
    {
        byte[] origBytes = orig.getBytes("ASCII");
        byte[] encryptedBytes = encrypt(origBytes);
        byte[] encodedArray = Base64.encodeBase64(encryptedBytes, false,
                urlSafe);

        String encodedString = new String(encodedArray, "ASCII");

        return encodedString;
    }

    /**
     * @return  the Key to use when encrypting or decrypting
     */
    protected Key getKey()
    {
        return new SecretKeySpec(m_key, "DES");
    }
}
