package com.github.evabishchevich.figures.drawer;

import com.github.evabishchevich.figures.drawer.drawer.FxDrawer;
import com.github.evabishchevich.figures.drawer.extension.LoadingPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DrawersSerializer {


    void saveToFile(String fileName, List<FxDrawer> drawers, LoadingPlugin loadingPlugin) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(drawers);
            out.close();
            OutputStream fileOut = new FileOutputStream(fileName);
            ByteArrayInputStream bios = new ByteArrayInputStream(baos.toByteArray());
            if (loadingPlugin != null) {
                loadingPlugin.save(bios, fileOut);
            } else {
                copyStreams(bios, fileOut);
            }
            baos.close();
            bios.close();
            fileOut.close();
            System.out.println("Figures saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
        }
    }

    List<FxDrawer> loadFromFile(String fileName, LoadingPlugin loadingPlugin) {
        InputStream fileIn = null;
        ObjectInputStream in = null;
        try {
            fileIn = new FileInputStream(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (loadingPlugin != null) {
                loadingPlugin.load(fileIn, baos);
            } else {
                copyStreams(fileIn, baos);
            }
            ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());
            in = new ObjectInputStream(bis);
            System.out.println("Figures read from " + fileName);
            return (ArrayList<FxDrawer>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.toString());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fileIn != null) {
                    fileIn.close();
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.toString());
            }
        }
        return new ArrayList<>();
    }

    private void copyStreams(InputStream from, OutputStream to) {
        int length;
        final int BUFFER = 2048;
        byte buffer[] = new byte[BUFFER];
        try {
            while ((length = from.read(buffer)) > 0) {
                to.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
