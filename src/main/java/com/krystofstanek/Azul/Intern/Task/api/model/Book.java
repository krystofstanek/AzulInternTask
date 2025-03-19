package com.krystofstanek.Azul.Intern.Task.api.model;

import java.math.BigDecimal;

/**
 * Represents a book with basic operations and details.
 */
public interface Book {
  /**
   * Returns the International Standard Book Number (ISBN) of the book.
   *
   * @return the ISBN
   */
  String getIsbn();

  /**
   * Returns the available quantity of the book in stock.
   *
   * @return the current stock quantity
   */
  int getQuantity();

  /**
   * Updates the quantity of the book by the specified amount.
   *
   * @param amount the amount to adjust the stock by, can be negative
   */
  void updateQuantity(int amount);

  /**
   * Returns the title of the book.
   *
   * @return the title
   */
  String getTitle();

  /**
   * Returns the author of the book.
   *
   * @return the author
   */
  String getAuthor();

  /**
   * Returns the genre of the book.
   *
   * @return the genre
   */
  Genre getGenre();

  /**
   * Returns the price of the book.
   *
   * @return the price
   */
  BigDecimal getPrice();
}
