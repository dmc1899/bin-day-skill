package uk.co.service.skill.adapters.dataprovider.exceptions;

/**
 * An unchecked exception used to signify that the target service provider web
 * end points are unavailable.
 */
public class ServiceProviderUnavailableException extends RuntimeException{
    public ServiceProviderUnavailableException(String message) {
        super(message);
    }
}
