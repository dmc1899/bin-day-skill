package uk.co.service.skill.usecases.bincollection;

import uk.co.service.skill.adapters.dataprovider.exceptions.BinCollectionGatewayException;
import uk.co.service.skill.adapters.dataprovider.exceptions.PropertyNotFoundException;
import uk.co.service.skill.usecases.bincollection.inbound.BinCollectionRequestModel;
import uk.co.service.skill.usecases.bincollection.inbound.GetNextBinCollectionEventForPropertyInputBoundary;
import uk.co.service.skill.usecases.bincollection.outbound.BinCollectionResponseModel;
import uk.co.service.skill.usecases.bincollection.outbound.GetBinCollectionScheduleGateway;
import uk.co.service.skill.usecases.bincollection.outbound.GetNextBinCollectionEventForPropertyOutputBoundary;

public class GetNextBinCollectionEventForPropertyImpl implements GetNextBinCollectionEventForPropertyInputBoundary {

    private final GetBinCollectionScheduleGateway getBinCollectionScheduleGateway;
    //private BinCollectionRequestModel binCollectionRequestModel;
    //private GetNextBinCollectionEventForPropertyOutputBoundary binCollectionEventForPropertyOutputBoundary;

    public GetNextBinCollectionEventForPropertyImpl(GetBinCollectionScheduleGateway getBinCollectionScheduleGateway) {
        this.getBinCollectionScheduleGateway = getBinCollectionScheduleGateway;
    }

    public void execute(BinCollectionRequestModel binCollectionRequestModel, GetNextBinCollectionEventForPropertyOutputBoundary getNextBinCollectionEventForPropertyOutputBoundary) {
        System.out.println("Executing GetNextBinCollectionEventForProperty");

        String endPointForAddress;
        uk.co.service.skill.entities.PropertyBinCollectionSchedule nextCollectionForAddress = null;

        try{
            endPointForAddress = getBinCollectionScheduleGateway.getBinCollectionScheduleUrlForProperty(binCollectionRequestModel.firstLineOfAddress);
            nextCollectionForAddress = getBinCollectionScheduleGateway.getBinCollectionScheduleForProperty(endPointForAddress);
        }
        catch (BinCollectionGatewayException ex)
        {

        } catch (PropertyNotFoundException e) {
            e.printStackTrace();
        }

        BinCollectionResponseModel binCollectionResponseModel = new BinCollectionResponseModel();
        binCollectionResponseModel.binCollectionEvent = nextCollectionForAddress.getTestVal().toString();

        getNextBinCollectionEventForPropertyOutputBoundary.sendResult(binCollectionResponseModel);

    }
}
