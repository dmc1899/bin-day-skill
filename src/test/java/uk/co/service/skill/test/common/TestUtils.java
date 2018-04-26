package uk.co.service.skill.test.common;

import uk.co.service.skill.adapters.dataprovider.BinCollectionGatewayTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class TestUtils {

    public static String getResourceContentsAsString(String pathToResource) {
        URL url = BinCollectionGatewayTest.class.getResource(pathToResource);
        Path resPath = null;
        String resourceContents = null;
        try {
            resPath = java.nio.file.Paths.get(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            resourceContents = new String(java.nio.file.Files.readAllBytes(resPath), "UTF8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resourceContents;
    }
}
