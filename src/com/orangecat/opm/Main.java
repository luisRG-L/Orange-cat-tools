package com.orangecat.opm;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Type 'opm --help' to see all the commands");
        }

        String command = args[0];

        String route = System.getProperty("user.dir");

        switch(command) {
            case "install" -> {
                if (args.length < 2) {
                    System.out.println("Type 'opm --help' to see all the commands");
                }
                String lib = args[1];


            }
            case "init" -> {
                Scanner scanner = new Scanner(System.in);

                System.out.print("Package name: ");
                String packName = scanner.next();
                System.out.println();

                System.out.print("Package version: ");
                String packVersion = scanner.next();
                System.out.println();

                createFolder(route, packName);
                createFolder(route, packName+".ocat");
                String projectOCMNContent = """
                        
                        <project>
                            <version>
                        """+packVersion+"""
                            <version/>
                            <main>
                                src/main.ocat
                            <main/>
                            <sourcePath>
                                src
                            <sourcePath/>
                            <libraries>
                                <lib>
                                    <import>
                                        global
                                    <import/>
                                    <sourceID>
                                        examples
                                    <sourceID/>
                                    <orangeID>
                                        OC-HXZWY82VR
                                    <orangeID/>
                                <lib/>
                            <libraries/>
                            <build>
                                <source>
                                    global
                                <source/>
                                <target>
                                    .ocat/builds
                                <target/>
                                <name>
                            """+packName+"""
                                <name/>
                                <exports>
                                    ocf
                                <exports/>
                            <build/>
                        <project/>
                        """;
            }
        }
    }

    public static void createFolder(String pathn, String name ) {
        Path path = Paths.get(pathn, name);

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Folder created sucessfully: " +path.toString());
            } else {
                System.out.println("The folder already exists in: " + path.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFile(String pathn, String name, String content) {
        Path path = Paths.get(pathn, name);

        try {
            if (!Files.exists(path)) {
                Files.write(path, content.getBytes(StandardCharsets.UTF_8));
                System.out.println("File created sucessfully: " +path.toString());
            } else {
                System.out.println("The file already exists in: " + path.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
