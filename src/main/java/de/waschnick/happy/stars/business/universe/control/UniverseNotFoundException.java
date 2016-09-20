package de.waschnick.happy.stars.business.universe.control;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UniverseNotFoundException extends RuntimeException {

	public UniverseNotFoundException(long id) {
		super("Could not find universe '" + id + "'.");
	}
}