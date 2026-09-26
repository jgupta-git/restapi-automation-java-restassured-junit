package com.automationexercise.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ApiConfig {

    private static final Properties props = new Properties();
    private static final Properties dummyJsonProps = new Properties();

    static {
        try (InputStream in = ApiConfig.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
        try (InputStream in = ApiConfig.class.getClassLoader()
                .getResourceAsStream("dummyjson.properties")) {
            dummyJsonProps.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load dummyjson.properties", e);
        }
    }

    public static String baseUrl() {
        return props.getProperty("base.url");
    }

    public static String dummyJsonBaseUrl() {
        return dummyJsonProps.getProperty("base.url");
    }

    private ApiConfig() {}
}
