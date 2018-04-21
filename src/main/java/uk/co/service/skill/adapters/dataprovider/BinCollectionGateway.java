package uk.co.service.skill.adapters.dataprovider;

import uk.co.service.skill.entities.PropertyBinCollectionSchedule;
import uk.co.service.skill.usecases.bincollection.outbound.GetBinCollectionScheduleEndpointForProperty;
import uk.co.service.skill.usecases.bincollection.outbound.GetBinCollectionForProperty;

public class BinCollectionGateway implements GetBinCollectionForProperty, GetBinCollectionScheduleEndpointForProperty {
    public String getBinCollectionScheduleEndpointForProperty(String firstLineOfAddress) {
        return "bin collection endpoint - http://www." + firstLineOfAddress + ".com";
    }

    public PropertyBinCollectionSchedule getBinCollectionScheduleForProperty(String endPoint) {
        PropertyBinCollectionSchedule binSchedule = new PropertyBinCollectionSchedule();
        binSchedule.setTestVal("bin collection schedule (taken from " + endPoint);

        return binSchedule;
    }
}
