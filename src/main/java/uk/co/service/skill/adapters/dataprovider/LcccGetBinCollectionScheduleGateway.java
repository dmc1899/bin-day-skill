package uk.co.service.skill.adapters.dataprovider;

import uk.co.service.skill.LoggingFacade;
import uk.co.service.skill.adapters.dataprovider.exceptions.PropertyNotFoundException;
import uk.co.service.skill.adapters.dataprovider.web.AddressPageParser;
import uk.co.service.skill.adapters.dataprovider.web.BasicWebDocumentClient;
import uk.co.service.skill.adapters.dataprovider.web.Url;
import uk.co.service.skill.adapters.dataprovider.web.WebDocumentClient;
import uk.co.service.skill.entities.PropertyBinCollectionSchedule;

import java.io.IOException;

/**

 */
public class LcccGetBinCollectionScheduleGateway implements uk.co.service.skill.usecases.bincollection.outbound.GetBinCollectionScheduleGateway, LoggingFacade {

    private final String encoding = "UTF-8";
    private String addressNotFoundResponseText = "No results found for the search text provided";
    private String serviceProviderUrlBase = "https://lisburn.isl-fusion.com";
    private String serviceProviderUrlAddressPath = "/address";
    private String serviceProviderUrlCollectionPath = "/view";
    private WebDocumentClient webDocumentClient = new BasicWebDocumentClient();

    public LcccGetBinCollectionScheduleGateway(){};

    public LcccGetBinCollectionScheduleGateway(String serviceProviderUrlBase, String serviceProviderUrlAddressPath, String serviceProviderUrlCollectionPath, String addressNotFoundResponseText, WebDocumentClient webDocumentClient){
        this.serviceProviderUrlBase = serviceProviderUrlBase;
        this.serviceProviderUrlAddressPath = serviceProviderUrlAddressPath;
        this.serviceProviderUrlCollectionPath = serviceProviderUrlCollectionPath;
        this.addressNotFoundResponseText = addressNotFoundResponseText;
        this.webDocumentClient = webDocumentClient;
    }

    /**
     * Extracts an HTML document from a source JSON document.
     * The source JSON document is retrieved from a
     * service provider endpoint.
     *
     * @param  firstLineOfAddress The first line of the address used as
     *                 input to the service provider's address index.
     * @throws IOException (optional) from getWebDocument
     * @throws PropertyNotFoundException (optional) if the request to address index retusn
     */
    public String getBinCollectionScheduleUrlForProperty(String firstLineOfAddress) throws PropertyNotFoundException{

        String addressSearchResponseJson = webDocumentClient.getWebDocument(firstLineOfAddress);

        AddressPageParser addressPageParser = new AddressPageParser(addressSearchResponseJson);
        String addressUrlPart = addressPageParser.getAddressUrl();

        return Url.buildUrl(serviceProviderUrlBase, serviceProviderUrlCollectionPath, addressUrlPart);

    }


    public PropertyBinCollectionSchedule getBinCollectionScheduleForProperty(String endPoint) {
        uk.co.service.skill.entities.PropertyBinCollectionSchedule binSchedule = new uk.co.service.skill.entities.PropertyBinCollectionSchedule();
        binSchedule.setTestVal("bin collection schedule (taken from " + endPoint);

        return binSchedule;
    }

}
