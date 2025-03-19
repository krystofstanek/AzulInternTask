package com.krystofstanek.Azul.Intern.Task.api.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 * Represents a simple type of book with basic attributes.
 * This entity uses single table inheritance and is identified with the discriminator value
 * "SIMPLE".
 */
@Entity
@DiscriminatorValue("SIMPLE")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleBook extends AbstractBook {

  /**
   * Constructs a new {@code SimpleBook} with the specified attributes.
   *
   * @param isbn     the International Standard Book Number (must not be null or blank)
   * @param title    the title of the book (must not be null or blank)
   * @param author   the author of the book (must not be null or blank)
   * @param genre    the genre of the book (must not be null)
   * @param price    the price of the book (must not be null or negative)
   * @param quantity the quantity in stock (must be at least 1)
   */
  public SimpleBook(String isbn,
                    String title,
                    String author,
                    Genre genre,
                    BigDecimal price,
                    int quantity) {
    super(isbn, title, author, genre, price, quantity);
  }
}
