package uk.co.service.skill.adapters.dataprovider.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.function.Function;

public class TestLambda {

    interface Printer {
        void print(String val);
    }

    interface WebDocumentClientRequestor {
        String requestDocument(String url) throws IOException;
    }


    public String getWebDocument(String url, WebDocumentClientRequestor webDocumentClientRequestor){

        String webDocument = null;
        try {
            webDocument = webDocumentClientRequestor.requestDocument(url);}
        catch (IOException e){

        }
        return webDocument;
    }

    public void printSomething(String something, Printer printer) {
        printer.print(something);
    }

    public static void main(String args[]) {

        TestLambda demo = new TestLambda();

        String url = "http://www.google.com";

        WebDocumentClientRequestor webDocumentClientRequestor = (String link) -> {
            String result = null;
            try {
                result = Jsoup.connect(link).ignoreContentType(true).execute().body();
                Document document = Jsoup.parse(result);
                String title = document.title();
                //assertThat(document.title()).isEqualTo("Google");
                System.out.println(title);
                }
        catch(Exception e){
                throw new IOException("Failed");

            } return result;
        };

        String result = demo.getWebDocument(url, webDocumentClientRequestor);
        System.out.println(result);

//        String something = "I am learning Lambda";
//        /**/
//        Printer printer = (String toPrint) -> {
//            System.out.println(toPrint);
//        };
//        /**/
//
//        demo.test(printer);
//        demo.printSomething(something, printer);
    }


    public  void test(Printer printer){
         printer.print("fat");
    }
}
//        //Consumer<String> c = s -> System.out.println(s);
//
//        BasicWebDocumentClient b = new BasicWebDocumentClient();
//        Integer result = b.test(b::method1);
//        System.out.println(result);
//        result = b.test(b::method2);
//        System.out.println(result);
//    }
//}
