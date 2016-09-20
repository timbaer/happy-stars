package de.waschnick.happy.stars.business.universe.control;

import de.waschnick.happy.stars.business.universe.entity.UniverseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ToManyStarsForUniverseException extends RuntimeException {

    public ToManyStarsForUniverseException(UniverseEntity entity) {
        super("Could not save star to universe '" + entity.getId() + "' because it can only handle a maximum of '" + entity.getMaxSize() + "' stars.");
    }
}