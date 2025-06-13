package com.api.utils;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class ConfigReader {

    public static Map<String, Object> readYamlConfig(String filePath) throws IOException {
        Yaml yaml = new Yaml();
        FileInputStream inputStream = new FileInputStream(filePath);
        return yaml.load(inputStream);
    }
}
