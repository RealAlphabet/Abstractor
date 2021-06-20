package com.alphabet.abstractor.utils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

public class Loader {

    public static Map<String, MethodModifier> loadClasses(File jarFile) throws IOException {
        Map<String, MethodModifier> classes = new HashMap<>();

        try (JarFile jar = new JarFile(jarFile); Stream<JarEntry> str = jar.stream()) {
            str.forEach(entry -> readJar(jar, entry, classes));
        }

        return classes;
    }

    private static Map<String, MethodModifier> readJar(JarFile jar, JarEntry entry, Map<String, MethodModifier> classes) {
        String name = entry.getName();

        try (InputStream is = jar.getInputStream(entry)) {
            if (name.endsWith(".class")) {
                try {
                    MethodModifier methodReplacer = getReplacer(is);

                    if (methodReplacer.shouldIgnore)
                        return classes;

                    classes.put(name, methodReplacer);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    private static MethodModifier getReplacer(InputStream stream) throws IOException {
        ClassReader classReader = new ClassReader(stream);
        ClassWriter classWriter = new ClassWriter(0);
        MethodModifier methodReplacer = new MethodModifier(classWriter);

        classReader.accept(methodReplacer, 0);
        return methodReplacer;
    }
}
