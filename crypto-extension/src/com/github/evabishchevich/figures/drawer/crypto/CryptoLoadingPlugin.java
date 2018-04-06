package com.github.evabishchevich.figures.drawer.crypto;

import com.github.evabishchevich.figures.drawer.extension.LoadingPlugin;

import javax.crypto.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CryptoLoadingPlugin implements LoadingPlugin {

    private static final String ALGORITHM = "AES";
    private static final byte[] PAYLOAD = new byte[]{4, 5, 6};

    private SecretKey key;

    public CryptoLoadingPlugin() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            generator.init(new SecureRandom(PAYLOAD));
            key = generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "Crypto";
    }

    @Override
    public void load(InputStream from, OutputStream to) {
        Cipher c = null;
        try {
            c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        final int BUFFER = 2048;
        byte buffer[] = new byte[BUFFER];
        CipherInputStream cis = new CipherInputStream(from, c);
        try {
            int length;
            while ((length = cis.read(buffer)) > 0) {
                to.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                cis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void save(InputStream from, OutputStream to) {
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            final int BUFFER = 2048;
            byte buffer[] = new byte[BUFFER];
            CipherOutputStream cos = new CipherOutputStream(to, c);
            try {
                int length;
                while ((length = from.read(buffer)) > 0) {
                    cos.write(buffer, 0, length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    cos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (NoSuchAlgorithmException | InvalidKeyException |
                NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }
}
