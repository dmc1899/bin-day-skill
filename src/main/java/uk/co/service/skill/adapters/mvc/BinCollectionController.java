package uk.co.service.skill.adapters.mvc;

import uk.co.service.skill.usecases.bincollection.inbound.GetNextBinCollectionEventForPropertyInputBoundary;
import uk.co.service.skill.usecases.bincollection.outbound.GetNextBinCollectionEventForPropertyOutputBoundary;
import uk.co.service.skill.usecases.bincollection.inbound.BinCollectionRequestModel;

public class BinCollectionController {

    private GetNextBinCollectionEventForPropertyInputBoundary getNextBinCollectionEventForPropertyInputBoundary;
    private GetNextBinCollectionEventForPropertyOutputBoundary getNextBinCollectionEventForPropertyOutputBoundary;
    private GetNextBinCollectionView getNextBinCollectionView;

    public BinCollectionController(GetNextBinCollectionEventForPropertyInputBoundary getNextBinCollectionEventForPropertyInputBoundary,GetNextBinCollectionEventForPropertyOutputBoundary getNextBinCollectionEventForPropertyOutputBoundary,GetNextBinCollectionView getNextBinCollectionView ){
        this.getNextBinCollectionEventForPropertyInputBoundary = getNextBinCollectionEventForPropertyInputBoundary;
        this.getNextBinCollectionEventForPropertyOutputBoundary = getNextBinCollectionEventForPropertyOutputBoundary;
        this.getNextBinCollectionView = getNextBinCollectionView;
    }

    public String executeUseCase(String receivedAddress){

        BinCollectionRequestModel binCollectionRequestModel = new BinCollectionRequestModel();
        binCollectionRequestModel.firstLineOfAddress = receivedAddress;
        getNextBinCollectionEventForPropertyInputBoundary.execute(binCollectionRequestModel, getNextBinCollectionEventForPropertyOutputBoundary);

        return getNextBinCollectionEventForPropertyOutputBoundary.getViewModel().collectionItemCapitalised.toString();
    }
}
