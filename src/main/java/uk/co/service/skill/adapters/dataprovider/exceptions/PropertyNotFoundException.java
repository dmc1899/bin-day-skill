package uk.co.service.skill.adapters.dataprovider.exceptions;

/**
 *
 * An checked exception used to signify that the address of the
 * property used to perform a search against the service provider's
 * list of Collection Schedules, returned zero hits.
 */
public class PropertyNotFoundException extends Exception{
    public PropertyNotFoundException(String message) {
        super(message);
    }
}
