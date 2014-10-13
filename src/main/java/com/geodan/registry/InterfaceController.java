package com.geodan.registry;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Default controller that is used to display our interface for the web application.
 *
 * @author alexh
 */
@Controller
public class InterfaceController {

    /** Default run key that is used to store in the Redis database */
    private static final String RUN_KEY = "run";

    /**
     * Default method that retrieves the index page of our application.
     *
     * @param model Thymeleaf model
     * @return      Page to render in Thymeleaf
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("baseUrl", Application.getBaseUrl());
        return "index";
    }

    /**
     * Retrieves the run command for a specific application.
     *
     * @param field Application to retrieve the run command from
     * @return      Run command for that application
     */
    @RequestMapping(value = "/run/{field}", method = RequestMethod.GET)
    @ResponseBody
    public String getRunCommand(@PathVariable("field") String field) {
        String cmd = Application.getRedis().hget(InterfaceController.RUN_KEY, field);

        if (cmd == null || cmd.equalsIgnoreCase("")) { return ""; }

        return cmd;
    }

    /**
     * Stores the run command for a specific application.
     *
     * @param field   Application which to store the run command for
     * @param command Run command to store
     * @return        Always OK, 200.
     */
    @RequestMapping(value = "/run/{field}", params = "command", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> setRunCommand(@PathVariable("field") String field, @RequestParam("command") String command) {
        Application.getRedis().hset(InterfaceController.RUN_KEY, field, command);

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    /**
     * Gets the images and tags which could be found in the file system.
     *
     * @return All the images and tags.
     */
    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<String>> index() {
        return InterfaceController.readImages();
    }

    /**
     * Reads the images on the file system in a non-blocking way.
     *
     * @return Map of images, and associated tags
     */
    private static Map<String, List<String>> readImages() {
        Map<String, List<String>> images = new TreeMap<>();

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get(Application.getImagesPath()))) {
            for (Path p : ds) {
                if (Files.isReadable(p) && Files.isDirectory(p)) {
                    images.put(p.getFileName().toString().toLowerCase(), InterfaceController.readTags(p));
                }
            }
        } catch (IOException e) {
            System.err.println("Cannot find path: " + e.getMessage());
        }

        return images;
    }

    /**
     * Reads all the tags from the given image path
     *
     * @param path Path to retrieve the tags from
     * @return     List of tags
     */
    private static List<String> readTags(Path path) {
        List<String> tags = new ArrayList<>();

        try (DirectoryStream<Path> ps = Files.newDirectoryStream(path)) {
            for (Path p : ps) {
                if (p.getFileName().toString().startsWith("tag_") && !p.getFileName().toString().endsWith("json")) {
                    tags.add(p.getFileName().toString().replace("tag_", ""));
                }
            }
        } catch (IOException e) {
            System.err.println("Cannot find path: " + e.getMessage());
        }

        return tags;
    }

}
