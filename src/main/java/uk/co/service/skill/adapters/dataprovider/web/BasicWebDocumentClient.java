package uk.co.service.skill.adapters.dataprovider.web;

import org.jsoup.Jsoup;
import uk.co.service.skill.LoggingFacade;
import uk.co.service.skill.adapters.dataprovider.exceptions.BinCollectionGatewayException;
import uk.co.service.skill.adapters.dataprovider.exceptions.ServiceProviderUnavailableException;

import java.io.IOException;

public class BasicWebDocumentClient implements WebDocumentClient, LoggingFacade {

    @Override
    public String getWebDocument (String url){

        final Integer retryLimit = 5;
        final Integer baseWaitPeriodMs = 1000;
        String webDocument = null;
        Integer attemptCount = 0;
        boolean succeeded = false;

        while ((!succeeded) && (attemptCount < retryLimit)) {
            try {
                attemptCount += 1;
                webDocument = Jsoup.connect(url).ignoreContentType(true).execute().body();
                succeeded = true;

            } catch (IOException | IllegalArgumentException ex) {
                logger().debug("Failed to connect to " + url + " - attempt " + attemptCount.toString() + " of " + retryLimit.toString());

                if (attemptCount == retryLimit) {
                    throw new ServiceProviderUnavailableException("Failed to connect to " + url + " after " + attemptCount.toString() + " attempts due to: " + ex.getMessage());
                }

                try {
                    Integer delayPeriod = ((baseWaitPeriodMs * attemptCount)/1000);
                    logger().debug("Delaying for " + delayPeriod.toString() + " seconds before retrying.");
                    Thread.sleep(baseWaitPeriodMs * attemptCount);
                } catch (InterruptedException e) {
                    throw new BinCollectionGatewayException("Failed to connect to " + url + " after trying to delay and retry. Primary cause: " + e.getMessage());
                }
            }
        }
        return webDocument;
    }
}