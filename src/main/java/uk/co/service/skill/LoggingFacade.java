package uk.co.service.skill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface LoggingFacade {

    default org.slf4j.Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
