package uk.co.service.skill.adapters.dataprovider.web;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UrlTest {

    @Test
    public void buildValidUrl() throws Exception{

        String result = null;
        String expectedUrl = "http://www.google.comnewpage/this+is+a+string+with+a+lot+of+%5E%26*%29%24%29%C2%A3%27%27%27%3F%3F+characters";

        result = Url.buildUrl("http://www.google.com", "newpage", "this is a string with a lot of ^&*)$)£'''?? characters");
        System.out.println(result);

        assertEquals(expectedUrl, result);
    }

    @Test
    public void buildValidComplexUrl() throws Exception{

        String result = null;
        String expectedUrl = "http://www.google.comnewpage/%C2%A7%C2%B11%212%40%E2%82%AC3%C2%A3%E2%80%B9%C2%A2%E2%88%9E%C2%A7%C2%B6%E2%80%A2%C2%AA%C2%BA%C2%BA0%C3%A6%E2%80%A6%E2%80%98%E2%80%9C%C2%AB%C2%AB%C2%AA%C2%BA%C2%BA0%C3%A6";

        result = Url.buildUrl("http://www.google.com", "newpage", "§±1!2@€3£‹¢∞§¶•ªºº0æ…‘“««ªºº0æ");
        System.out.println(result);

        assertEquals(expectedUrl, result);
    }
}
