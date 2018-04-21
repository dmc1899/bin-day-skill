package uk.co.service.skill.usecases.bincollection;

import uk.co.service.skill.entities.PropertyBinCollectionSchedule;
import uk.co.service.skill.usecases.bincollection.inbound.BinCollectionRequestModel;
import uk.co.service.skill.usecases.bincollection.inbound.GetNextBinCollectionEventForPropertyInputBoundary;
import uk.co.service.skill.usecases.bincollection.outbound.BinCollectionResponseModel;
import uk.co.service.skill.usecases.bincollection.outbound.GetBinCollectionForProperty;
import uk.co.service.skill.usecases.bincollection.outbound.GetNextBinCollectionEventForPropertyOutputBoundary;

public class GetNextBinCollectionEventForPropertyImpl implements GetNextBinCollectionEventForPropertyInputBoundary {

    private final GetBinCollectionForProperty getBinCollectionForProperty;
    //private BinCollectionRequestModel binCollectionRequestModel;
    //private GetNextBinCollectionEventForPropertyOutputBoundary binCollectionEventForPropertyOutputBoundary;

    public GetNextBinCollectionEventForPropertyImpl(GetBinCollectionForProperty getBinCollectionForProperty ) {
        this.getBinCollectionForProperty = getBinCollectionForProperty;
    }

    public void execute(BinCollectionRequestModel binCollectionRequestModel, GetNextBinCollectionEventForPropertyOutputBoundary getNextBinCollectionEventForPropertyOutputBoundary) {
        System.out.println("Executing GetNextBinCollectionEventForProperty");

        String endPointForAddress = getBinCollectionForProperty.getBinCollectionScheduleEndpointForProperty(binCollectionRequestModel.firstLineOfAddress);
        PropertyBinCollectionSchedule nextCollectionForAddress = getBinCollectionForProperty.getBinCollectionScheduleForProperty(endPointForAddress);

        BinCollectionResponseModel binCollectionResponseModel = new BinCollectionResponseModel();
        binCollectionResponseModel.binCollectionEvent = nextCollectionForAddress.getTestVal().toString();

        getNextBinCollectionEventForPropertyOutputBoundary.sendResult(binCollectionResponseModel);

    }
}
