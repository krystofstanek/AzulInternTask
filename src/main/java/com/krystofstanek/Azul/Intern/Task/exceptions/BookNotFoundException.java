package com.krystofstanek.Azul.Intern.Task.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a book is not found.
 * This exception results in a 404 NOT FOUND HTTP status.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

  /**
   * Constructs a new {@code BookNotFoundException} with the specified detail message.
   *
   * @param message the detail message
   */
  public BookNotFoundException(String message) {
    super(message);
  }
}
