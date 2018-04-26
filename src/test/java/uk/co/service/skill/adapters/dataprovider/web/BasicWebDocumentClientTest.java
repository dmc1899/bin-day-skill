package uk.co.service.skill.adapters.dataprovider.web;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BasicWebDocumentClientTest {

    private final WebDocumentClient webDocumentClient = new BasicWebDocumentClient();

    @Test
    public void getHttpStatusException() throws Exception{

        String result = null;
        String expectedJsonResult = "http://www.google.comnewpage/this+is+a+string+with+a+lot+of+%5E%26*%29%24%29%C2%A3%27%27%27%3F%3F+characters";

        WebDocumentClient webDocumentClient = new BasicWebDocumentClient();
        String actualJsonResult = webDocumentClient.getHtml("https://httpstat.us/404");

        assertEquals(expectedJsonResult, actualJsonResult);
    }

//    @Test
//    public void shouldThrowScraperExceptionIfCannotLoadHtml() {
//        try {
//            webDocumentClient.getHtml("invalid");
//            fail();
//        } catch (ScraperException e) {
//            assertThat(e.getMessage()).isEqualTo("could not load html from url: invalid");
//        }
//    }
//
//    //should load valid JSON from https://api.github.com/users/mralexgray/repos
//    //should load valid HTML from https://www.google.com/
//    @Test
//    public void shouldLoadHtml() {
//        String html = htmlGetter.getHtml("http://www.google.com");
//        Document document = Jsoup.parse(html);
//        assertThat(document.title()).isEqualTo("Google");
//    }
}
