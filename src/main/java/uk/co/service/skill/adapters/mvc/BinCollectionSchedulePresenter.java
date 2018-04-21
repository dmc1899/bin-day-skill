package uk.co.service.skill.adapters.mvc;

import uk.co.service.skill.usecases.bincollection.outbound.GetNextBinCollectionEventForPropertyOutputBoundary;
import uk.co.service.skill.usecases.bincollection.outbound.BinCollectionResponseModel;

public class BinCollectionSchedulePresenter implements GetNextBinCollectionEventForPropertyOutputBoundary {

    String errorMessage;
    BinCollectionResponseModel responseModel;  //We probably don't need to persist this.
    BinCollectionViewModel viewModel;


    public void sendErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void sendResult(BinCollectionResponseModel responseModel) {
    this.responseModel = responseModel;
    this.viewModel = new BinCollectionViewModel();
    this.viewModel.collectionItemCapitalised = responseModel.binCollectionEvent.toUpperCase();
    }

    public BinCollectionViewModel getViewModel() {
        return viewModel;
    }
}
