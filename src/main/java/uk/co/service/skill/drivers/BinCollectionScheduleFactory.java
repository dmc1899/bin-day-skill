package uk.co.service.skill.drivers;

import uk.co.service.skill.adapters.dataprovider.LcccGetBinCollectionScheduleGateway;
import uk.co.service.skill.adapters.dataprovider.web.BasicWebDocumentClient;
import uk.co.service.skill.adapters.mvc.BinCollectionController;
import uk.co.service.skill.adapters.mvc.BinCollectionSchedulePresenter;
import uk.co.service.skill.adapters.mvc.GetNextBinCollectionView;
import uk.co.service.skill.usecases.bincollection.inbound.GetNextBinCollectionEventForPropertyInputBoundary;
import uk.co.service.skill.usecases.bincollection.outbound.GetNextBinCollectionEventForPropertyOutputBoundary;
import uk.co.service.skill.usecases.bincollection.outbound.GetBinCollectionScheduleGateway;
import uk.co.service.skill.usecases.bincollection.*;

public class BinCollectionScheduleFactory {
    public BinCollectionController createBinCollectionController() {

        //Wire up the system.

        //1. Gateway to website
        GetBinCollectionScheduleGateway myBinCollectionForProperty = new LcccGetBinCollectionScheduleGateway("https://lisburn.isl-fusion.com","/address","/view","No results found for the search text provided", new BasicWebDocumentClient());

        //2 Create the use Case
        GetNextBinCollectionEventForPropertyInputBoundary useCase = new GetNextBinCollectionEventForPropertyImpl(myBinCollectionForProperty);

        //3. Create the output boundary
        GetNextBinCollectionEventForPropertyOutputBoundary outputBoundary = new BinCollectionSchedulePresenter();

        //4. Create the view
        GetNextBinCollectionView myView = new GetNextBinCollectionView();

        //5. Create the controller
        return new BinCollectionController(useCase,outputBoundary,myView);
    }
}
