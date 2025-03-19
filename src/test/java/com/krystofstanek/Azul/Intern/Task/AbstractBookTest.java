package com.krystofstanek.Azul.Intern.Task.api.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class AbstractBookTest {

  private AbstractBook createTestBook() {
    return new SimpleBook("1234567890", "Test Title", "Test Author", Genre.FICTION, BigDecimal.valueOf(19.99), 10);
  }

  @Test
  public void testValidBookCreation() {
    AbstractBook book = createTestBook();
    assertEquals("1234567890", book.getIsbn());
    assertEquals("Test Title", book.getTitle());
    assertEquals("Test Author", book.getAuthor());
    assertEquals(Genre.FICTION, book.getGenre());
    assertEquals(BigDecimal.valueOf(19.99), book.getPrice());
    assertEquals(10, book.getQuantity());
  }

  @Test
  public void testInvalidIsbn() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new SimpleBook("", "Test Title", "Test Author", Genre.FICTION, BigDecimal.valueOf(19.99), 10);
    });
    assertEquals("ISBN cannot be null or empty.", exception.getMessage());
  }

  @Test
  public void testInvalidTitle() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new SimpleBook("1234567890", "   ", "Test Author", Genre.FICTION, BigDecimal.valueOf(19.99), 10);
    });
    assertEquals("Title cannot be null or empty.", exception.getMessage());
  }

  @Test
  public void testInvalidAuthor() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new SimpleBook("1234567890", "Test Title", "", Genre.FICTION, BigDecimal.valueOf(19.99), 10);
    });
    assertEquals("Author cannot be null or empty.", exception.getMessage());
  }

  @Test
  public void testInvalidGenre() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new SimpleBook("1234567890", "Test Title", "Test Author", null, BigDecimal.valueOf(19.99), 10);
    });
    assertEquals("Genre cannot be null.", exception.getMessage());
  }

  @Test
  public void testInvalidPrice() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new SimpleBook("1234567890", "Test Title", "Test Author", Genre.FICTION, BigDecimal.valueOf(-1), 10);
    });
    assertEquals("Price cannot be null or negative.", exception.getMessage());
  }

  @Test
  public void testInvalidQuantity() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new SimpleBook("1234567890", "Test Title", "Test Author", Genre.FICTION, BigDecimal.valueOf(19.99), 0);
    });
    assertEquals("Quantity must be at least 1.", exception.getMessage());
  }

  @Test
  public void testUpdateQuantity() {
    AbstractBook book = createTestBook();
    book.updateQuantity(5);
    assertEquals(15, book.getQuantity());

    book.updateQuantity(-10);
    assertEquals(5, book.getQuantity());

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      book.updateQuantity(-10);
    });
    assertEquals("Not enough stock available.", exception.getMessage());
  }
}
