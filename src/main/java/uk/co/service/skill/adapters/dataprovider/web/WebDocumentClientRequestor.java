package uk.co.service.skill.adapters.dataprovider.web;

import org.jsoup.Jsoup;

public class WebDocumentClientRequestor {

    public String getJsonWebDocument (String url) {
        String result = null;
        try {
            result = Jsoup.connect(url).ignoreContentType(true).execute().body();
        } catch (Exception e) {

        }
        return result;
    }

    public String getHtmlWebDocument (String url)  {
        String result = null;
        try{
                result = Jsoup.connect(url).get().toString();
            }
            catch( Exception e){

            }
        return result;
        }
}
