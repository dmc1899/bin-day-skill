package uk.co.service.skill.adapters.dataprovider;

import uk.co.service.skill.LoggingFacade;
import uk.co.service.skill.adapters.dataprovider.exceptions.PropertyNotFoundException;
import uk.co.service.skill.adapters.dataprovider.web.AddressPageParser;
import uk.co.service.skill.adapters.dataprovider.web.BasicWebDocumentClient;
import uk.co.service.skill.adapters.dataprovider.web.Url;
import uk.co.service.skill.adapters.dataprovider.web.WebDocumentClient;
import uk.co.service.skill.entities.PropertyBinCollectionSchedule;
import uk.co.service.skill.adapters.dataprovider.exceptions.*;


/**
 *  Lisburn & Castlereagh City Council implementation of Bin Collection Schedule Gateway.
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
     * Attempts to identify the Bin Collection Schedule URL for the
     * specified address.  Performs a search against an Address-URL
     * service and returns a valid URL if found.
     *
     * @param  firstLineOfAddress The first line of the address used as
     *                            the free text search term.
     * @throws ServiceProviderUnavailableException (optional) If the service provider
     *                                              cannot be reached.
     *
     * @throws BinCollectionGatewayException (optional) If the returned URL cannot be handled.
     * @throws PropertyNotFoundException (optional) If the request to address index returns no hits.
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
