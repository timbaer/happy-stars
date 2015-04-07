package de.waschnick.happy.stars.control;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class StarNotFoundException extends RuntimeException {

	public StarNotFoundException(long id) {
		super("Could not find star '" + id + "'.");
	}
}