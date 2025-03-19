package com.krystofstanek.Azul.Intern.Task.api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;


/**
 * AbstractBook is the abstract base class for all book types.
 * It provides common fields and validations for book attributes.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "book_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@EqualsAndHashCode(of = "isbn")
public abstract class AbstractBook implements Book {



  @Id
  private String isbn;

  private String title;
  private String author;

  @Enumerated(EnumType.STRING)
  private Genre genre;

  private BigDecimal price;
  private int quantity;

  /**
   * Default constructor for JPA.
   */
  protected AbstractBook() {
  }

  /**
   * Constructs a new AbstractBook with the specified attributes.
   *
   * @param isbn the International Standard Book Number (must not be null or blank)
   * @param title the title of the book (must not be null or blank)
   * @param author the author of the book (must not be null or blank)
   * @param genre the genre of the book (must not be null)
   * @param price the price of the book (must not be null or negative)
   * @param quantity the quantity in stock (must be at least 1)
   * @throws IllegalArgumentException if any argument is invalid
   */
  public  AbstractBook(String isbn,
                      String title, String author,
                      Genre genre,
                      BigDecimal price,
                      int quantity) {
    if (isbn == null || isbn.isBlank()) {
      throw new IllegalArgumentException("ISBN cannot be null or empty.");
    }
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException("Title cannot be null or empty.");
    }
    if (author == null || author.isBlank()) {
      throw new IllegalArgumentException("Author cannot be null or empty.");
    }
    if (genre == null) {
      throw new IllegalArgumentException("Genre cannot be null.");
    }
    if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Price cannot be null or negative.");
    }
    if (quantity < 1) {
      throw new IllegalArgumentException("Quantity must be at least 1.");
    }

    this.isbn = isbn;
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.price = price;
    this.quantity = quantity;
  }



  /**
   * Updates the quantity of the book by the specified amount.
   *
   * @param amount the amount to adjust the stock by (can be negative)
   * @throws IllegalArgumentException if the update results in a negative stock quantity
   */
  @Override
  public void updateQuantity(int amount) {
    if (this.quantity + amount < 0) {
      throw new IllegalArgumentException("Not enough stock available.");
    }
    this.quantity += amount;
  }
}
