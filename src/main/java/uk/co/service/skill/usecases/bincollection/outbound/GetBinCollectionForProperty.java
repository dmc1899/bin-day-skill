package uk.co.service.skill.usecases.bincollection.outbound;

import uk.co.service.skill.adapters.dataprovider.BinCollectionGatewayException;
import uk.co.service.skill.adapters.dataprovider.PropertyNotFoundException;
import uk.co.service.skill.entities.PropertyBinCollectionSchedule;

public interface GetBinCollectionForProperty {

    PropertyBinCollectionSchedule getBinCollectionScheduleForProperty(String endPoint) throws BinCollectionGatewayException;

    String getBinCollectionScheduleEndpointForProperty(String firstLineOfAddress) throws BinCollectionGatewayException, PropertyNotFoundException;
}
