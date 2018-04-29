package uk.co.service.skill.adapters.dataprovider.web;

import uk.co.service.skill.adapters.dataprovider.exceptions.BinCollectionGatewayException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Url {


    public static String buildUrl(String baseUrl, String path, String resource) {
        String builtUrl = null;
        String encodedResource = null;

        try{ encodedResource = URLEncoder.encode(resource.trim(), "UTF-8");}
        catch (UnsupportedEncodingException e){
            throw new BinCollectionGatewayException(e.getMessage());
        }
        builtUrl = new StringBuilder().append(baseUrl).append(path).append("/").append(encodedResource).toString();

        return builtUrl;
    }
}
