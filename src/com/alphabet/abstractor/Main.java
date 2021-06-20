package com.alphabet.abstractor;

import com.alphabet.abstractor.utils.Loader;
import com.alphabet.abstractor.utils.MethodModifier;
import com.alphabet.abstractor.utils.Saver;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 1)
            System.out.println("java -jar abstractor.jar [jar]");

        else {
            Map<String, MethodModifier> classesMap = Loader.loadClasses(new File(args[0]));
            Map<String, byte[]> bytesMap = Saver.processNodes(classesMap);
            Saver.saveAsJar(bytesMap, "output.jar");
        }
    }
}
