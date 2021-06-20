package com.alphabet.abstractor.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class Saver {

    public static Map<String, byte[]> processNodes(Map<String, MethodModifier> replacerMap) {
        Map<String, byte[]> out = new HashMap<>();

        for (Map.Entry<String, MethodModifier> entry : replacerMap.entrySet())
            out.put(entry.getKey(), entry.getValue().toByteArray());

        return out;
    }

    public static void saveAsJar(Map<String, byte[]> outBytes, String fileName) {
        try (JarOutputStream out = new JarOutputStream(new FileOutputStream(fileName))) {
            for (String entry : outBytes.keySet()) {
                out.putNextEntry(new ZipEntry(entry));
                out.write(outBytes.get(entry));
                out.closeEntry();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
