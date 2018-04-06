package com.github.evabishchevich.figures.drawer.zip;

import com.github.evabishchevich.figures.drawer.extension.LoadingPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipLoadingPlugin implements LoadingPlugin {

    private static final String ZIP_ENTRY = "figures";

    @Override
    public String getName() {
        return "Zip";
    }

    @Override
    public void load(InputStream from, OutputStream to) {
        ZipInputStream zin = new ZipInputStream(from);
        ZipEntry entry;
        try {
            while ((entry = zin.getNextEntry()) != null) {
                if (entry.getName().equals(ZIP_ENTRY)) {
                    final int BUFFER = 2048;
                    byte buffer[] = new byte[BUFFER];
                    int size;
                    while ((size = zin.read(buffer, 0, buffer.length)) != -1) {
                        to.write(buffer, 0, size);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                zin.closeEntry();
                zin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void save(InputStream from, OutputStream to) {
        final int BUFFER = 2048;
        byte buffer[] = new byte[BUFFER];
        ZipOutputStream zos = new ZipOutputStream(to);
        try {
            zos.putNextEntry(new ZipEntry(ZIP_ENTRY));
            int length;
            while ((length = from.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                zos.closeEntry();
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
