package uk.co.service.skill.adapters.dataprovider.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Url {


    public static String buildUrl(String baseUrl, String path, String resource) throws UnsupportedEncodingException {
        String builtUrl = null;

        String encodedResource = URLEncoder.encode(resource.trim(), "UTF-8");
        builtUrl = new StringBuilder().append(baseUrl).append(path).append("/").append(encodedResource).toString();

        return builtUrl;
    }
}
