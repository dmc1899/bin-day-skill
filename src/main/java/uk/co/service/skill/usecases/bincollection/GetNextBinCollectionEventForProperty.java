package uk.co.service.skill.usecases.bincollection;

import uk.co.service.skill.LoggingFacade;
import uk.co.service.skill.adapters.dataprovider.exceptions.BinCollectionGatewayException;
import uk.co.service.skill.adapters.dataprovider.exceptions.PropertyNotFoundException;
import uk.co.service.skill.adapters.dataprovider.exceptions.ServiceProviderUnavailableException;
import uk.co.service.skill.entities.PropertyBinCollectionSchedule;
import uk.co.service.skill.usecases.bincollection.inbound.BinCollectionRequestModel;
import uk.co.service.skill.usecases.bincollection.inbound.GetNextBinCollectionEventForPropertyInputBoundary;
import uk.co.service.skill.usecases.bincollection.outbound.BinCollectionResponseModel;
import uk.co.service.skill.usecases.bincollection.outbound.GetBinCollectionScheduleGateway;
import uk.co.service.skill.usecases.bincollection.outbound.GetNextBinCollectionEventForPropertyOutputBoundary;

public class GetNextBinCollectionEventForProperty implements GetNextBinCollectionEventForPropertyInputBoundary, LoggingFacade {

    private final GetBinCollectionScheduleGateway getBinCollectionScheduleGateway;
    //private BinCollectionRequestModel binCollectionRequestModel;
    //private GetNextBinCollectionEventForPropertyOutputBoundary binCollectionEventForPropertyOutputBoundary;

    public GetNextBinCollectionEventForProperty(GetBinCollectionScheduleGateway getBinCollectionScheduleGateway) {
        this.getBinCollectionScheduleGateway = getBinCollectionScheduleGateway;
    }

    public void execute(BinCollectionRequestModel binCollectionRequestModel, GetNextBinCollectionEventForPropertyOutputBoundary getNextBinCollectionEventForPropertyOutputBoundary) {
        logger().debug("Executing Get Next Bin Collection Event");

        String collectionScheduleUrlForAddress = null;
        PropertyBinCollectionSchedule propertyBinCollectionSchedule = null;

        try{
            collectionScheduleUrlForAddress = getBinCollectionScheduleGateway.getBinCollectionScheduleUrlForProperty(binCollectionRequestModel.firstLineOfAddress);
        }
        catch (PropertyNotFoundException pex)
        {
            pex.printStackTrace();
            // TODO We will wannt to introduce some intelligence here in potentially finding
            // a nearby address if an exact match is not found with the given search string.

        }

        catch (ServiceProviderUnavailableException sue) {
            sue.printStackTrace();
        }


        propertyBinCollectionSchedule = getBinCollectionScheduleGateway.getBinCollectionScheduleForProperty(collectionScheduleUrlForAddress);

        BinCollectionResponseModel binCollectionResponseModel = new BinCollectionResponseModel();
        binCollectionResponseModel.binCollectionEvent = propertyBinCollectionSchedule.getTestVal().toString();

        getNextBinCollectionEventForPropertyOutputBoundary.sendResult(binCollectionResponseModel);

    }
}
