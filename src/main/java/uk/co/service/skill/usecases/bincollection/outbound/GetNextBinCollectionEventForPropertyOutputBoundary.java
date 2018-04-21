package uk.co.service.skill.usecases.bincollection.outbound;

import uk.co.service.skill.adapters.mvc.BinCollectionViewModel;

public interface GetNextBinCollectionEventForPropertyOutputBoundary {

    void sendErrorMessage(String errorMessage);

    void sendResult(BinCollectionResponseModel responseModel);

    BinCollectionViewModel getViewModel();
}


//    public CodecastSummariesViewModel getViewModel() {
//        return viewModel;
//    }
//
//    @Override
//    public void present(CodecastSummariesResponseModel responseModel) {
//        viewModel = new CodecastSummariesViewModel();
//        for(CodecastSummary codecastSummary : responseModel.getCodecastSummaries()) {
//            viewModel.addModel(makeViewable(codecastSummary));
//        }
//    }

