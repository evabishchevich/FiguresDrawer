package com.github.evabishchevich.figures.drawer;

import com.github.evabishchevich.figures.drawer.extension.LoadingPlugin;
import com.randomguy.crypto.CryptoPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CryptoLoadingPluginAdapter implements LoadingPlugin {

    private CryptoPlugin plugin;

    public CryptoLoadingPluginAdapter() {
        plugin = new CryptoPlugin();
    }

    @Override
    public String getName() {
        return "Crypto";
    }

    @Override
    public void load(InputStream from, OutputStream to) {
        InputStream decrypted = plugin.decrypt(from);
        final int BUFFER = 2048;
        byte buffer[] = new byte[BUFFER];
        try {
            int length;
            while ((length = decrypted.read(buffer)) > 0) {
                to.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                decrypted.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void save(InputStream from, OutputStream to) {
        OutputStream encrypted = plugin.encrypt(to);
        final int BUFFER = 2048;
        byte buffer[] = new byte[BUFFER];
        try {
            int length;
            while ((length = from.read(buffer)) > 0) {
                encrypted.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                encrypted.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}