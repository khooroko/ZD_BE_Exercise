package main.java.utils;

import java.io.InputStream;

public class ResourceResolver {
    static ResourceResolver rs = new ResourceResolver();
    public static InputStream resolveResource(String path) {
        return rs.getClass().getResourceAsStream(path);
    }
}
