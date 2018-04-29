package uk.co.service.skill.usecases.bincollection.outbound;

import uk.co.service.skill.adapters.dataprovider.exceptions.BinCollectionGatewayException;
import uk.co.service.skill.adapters.dataprovider.exceptions.PropertyNotFoundException;
import uk.co.service.skill.entities.PropertyBinCollectionSchedule;

public interface GetBinCollectionScheduleGateway {

    PropertyBinCollectionSchedule getBinCollectionScheduleForProperty(String endPoint) throws BinCollectionGatewayException;

    String getBinCollectionScheduleUrlForProperty(String firstLineOfAddress) throws BinCollectionGatewayException, PropertyNotFoundException;
}
