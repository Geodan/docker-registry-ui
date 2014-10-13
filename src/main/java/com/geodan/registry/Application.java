package com.geodan.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.io.IOException;

/**
 * Main application that is configured with Spring Boot.
 *
 * @author alexh
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    /** Default base URL for this registry */
    private static String BASE_URL = "docker.registry.io";

    /** Default images path */
    private static String IMAGES_PATH = "registry/";

    /** The used Jedis client */
    private static Jedis REDIS_CLIENT;

    /** The default Redis URL */
    private static String REDIS_URL = "localhost";

    /**
     * Main entrypoint of this application
     *
     * @param args Optional arguments that will be disregarded...
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        if (System.getenv().containsKey("BASE_URL"))    { Application.BASE_URL = System.getenv("BASE_URL"); }
        if (System.getenv().containsKey("IMAGES_PATH")) { Application.IMAGES_PATH = System.getenv("IMAGES_PATH"); }
        if (System.getenv().containsKey("REDIS_URL"))   { Application.REDIS_URL = System.getenv("REDIS_URL"); }
    }

    /**
     * Gets the base url for the registry.
     *
     * @return Base URL
     */
    static String getBaseUrl() {
        return Application.BASE_URL;
    }

    /**
     * Gets the images path of the registry
     *
     * @return Images path
     */
    static String getImagesPath() {
        return Application.IMAGES_PATH;
    }

    /**
     * Creates and/or gets the Jedis client for connecting to Redis.
     *
     * @return Jedis client.
     */
    static Jedis getRedis() {
        if (Application.REDIS_CLIENT == null) {
            Application.REDIS_CLIENT = new Jedis(Application.REDIS_URL);
            System.out.println(Application.REDIS_CLIENT.ping());
        }

        return Application.REDIS_CLIENT;
    }

}
