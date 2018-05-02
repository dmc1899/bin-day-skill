package uk.co.service.skill.adapters.dataprovider;

import uk.co.service.skill.LoggingFacade;
import uk.co.service.skill.adapters.dataprovider.exceptions.PropertyNotFoundException;
import uk.co.service.skill.adapters.dataprovider.web.*;
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
     * Trys to identify the Bin Collection Schedule URL for the
     * specified address.  Performs a search against an Address-URL
     * service and returns a valid URL if found.
     *
     * @param  firstLineOfAddress   The first line of the address used as
     *                              the free text search term.
     * @return                      The URL to the schedule for the given priority
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

    /**
     * Retrieves the HTML document from the provided URL. This document
     * is marshalled into a {@see PropertyBinCollectionSchedule} object.
     *
     * @param  binCollectionScheduleUrl         The URL for the bin schedule for this property.
     * @return PropertyBinCollectionSchedule    The URL to the schedule for the given priority
     * @see     PropertyBinCollectionSchedule
     * @throws ServiceProviderUnavailableException (optional) If the service provider
     * @throws BinCollectionGatewayException (optional) If the returned URL cannot be handled.
     */
    public PropertyBinCollectionSchedule getBinCollectionScheduleForProperty(String binCollectionScheduleUrl) {

        String scheduleHtml = webDocumentClient.getWebDocument(binCollectionScheduleUrl);

        SchedulePageParser schedulePageParser = new SchedulePageParser(scheduleHtml);

        //Get the items.
        PropertyBinCollectionSchedule propertyBinCollectionSchedule = new PropertyBinCollectionSchedule();
        propertyBinCollectionSchedule.setTestVal("bin collection schedule (taken from ");

        return propertyBinCollectionSchedule;
    }
}
