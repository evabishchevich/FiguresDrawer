package com.randomguy.crypto;

import javax.crypto.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

// Implementation from somebody
public class CryptoPlugin {

    private static final String ALGORITHM = "AES";
    private static final byte[] PAYLOAD = new byte[]{4, 5, 6};

    private SecretKey key;

    public CryptoPlugin() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            generator.init(new SecureRandom(PAYLOAD));
            key = generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public InputStream decrypt(InputStream stream) {
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            return new CipherInputStream(stream, c);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OutputStream encrypt(OutputStream stream) {
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            return new CipherOutputStream(stream, c);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
