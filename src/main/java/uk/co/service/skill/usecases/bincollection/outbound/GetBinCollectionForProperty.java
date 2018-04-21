package uk.co.service.skill.usecases.bincollection.outbound;

import uk.co.service.skill.entities.PropertyBinCollectionSchedule;

public interface GetBinCollectionForProperty {

    PropertyBinCollectionSchedule getBinCollectionScheduleForProperty(String endPoint) throws Exception;

    String getBinCollectionScheduleEndpointForProperty(String firstLineOfAddress) throws Exception;
}
