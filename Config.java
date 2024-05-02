package com.example;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Properties;


public class Config {
    private final Path propertiesPath;

    private boolean protectAreas = true;
    private final ArrayList<String[]> areas = new ArrayList<>();
    private boolean destroy = true;


    public boolean getProtectAreas() {
        return protectAreas;
    }
    public ArrayList<String[]> getAreas() {
        return areas;
    }
    public boolean getDestroy() {
        return destroy;
    }


    public Config(Path propertiesPath) {
        this.propertiesPath = propertiesPath;
    }

    public void initialize() throws IOException {
        load();
        save();
    }

    public void load() throws IOException {
        if (!Files.exists(propertiesPath)) {
            return;
        }

        Properties properties = new Properties();

        try (InputStream is = Files.newInputStream(propertiesPath)) {
            properties.load(is);
        }


        destroy = !"false".equals(properties.getProperty("destroy"));
        protectAreas = !"false".equals(properties.getProperty("protectAreas"));

        try {
            for (String area : properties.getProperty("areas").split(";")) {
                try {
                    String[] areaSplit = area.split(",");

                    Float.parseFloat(areaSplit[0]);
                    Float.parseFloat(areaSplit[1]);
                    Float.parseFloat(areaSplit[2]);
                    Float.parseFloat(areaSplit[3]);

                    if (areaSplit[4].equals("circle") || areaSplit[4].equals("rectangle")) {
                        areas.add(areaSplit);
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {}
    }

    public void save() throws IOException {
        Properties properties = new Properties();

        
        properties.setProperty("destroy", String.valueOf(destroy));
        properties.setProperty("protectAreas", String.valueOf(protectAreas));

        properties.setProperty("areas", "");
        for (String[] area : areas) {
            for (String detail : area) {
                properties.setProperty("areas", properties.getProperty("areas")+detail+",");
            }
            properties.setProperty("areas", properties.getProperty("areas")+";");
        }


        // NB: This uses ISO-8859-1 with unicode escapes as the encoding
        try (OutputStream os = Files.newOutputStream(propertiesPath)) {
            properties.store(os, "Anti Creeper properties");
        }
    }
}
