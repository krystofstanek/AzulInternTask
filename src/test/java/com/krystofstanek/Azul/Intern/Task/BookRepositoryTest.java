package com.krystofstanek.Azul.Intern.Task.api.repository;

import com.krystofstanek.Azul.Intern.Task.api.model.AbstractBook;
import com.krystofstanek.Azul.Intern.Task.api.model.Genre;
import com.krystofstanek.Azul.Intern.Task.api.model.SimpleBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookRepositoryTest {

  @Autowired
  private BookRepository bookRepository;

  private AbstractBook createTestBook(String isbn) {
    return new SimpleBook(isbn, "Test Title", "Test Author", Genre.FICTION, BigDecimal.valueOf(19.99), 10);
  }


  @Test
  public void testFindByTitle() {
    AbstractBook book = createTestBook("ISBN002");
    bookRepository.save(book);

    Page<AbstractBook> page = bookRepository.findByTitle("Test Title", PageRequest.of(0, 10));
    assertEquals(1, page.getTotalElements());
  }

  @Test
  public void testFindByAuthor() {
    AbstractBook book = createTestBook("ISBN003");
    bookRepository.save(book);

    Page<AbstractBook> page = bookRepository.findByAuthor("Test Author", PageRequest.of(0, 10));
    assertEquals(1, page.getTotalElements());
  }

  @Test
  public void testFindByGenre() {
    AbstractBook book = createTestBook("ISBN004");
    bookRepository.save(book);

    Page<AbstractBook> page = bookRepository.findByGenre(Genre.FICTION, PageRequest.of(0, 10));
    assertEquals(1, page.getTotalElements());
  }

  @Test
  public void testFindByPriceRange() {
    AbstractBook book = createTestBook("ISBN005");
    bookRepository.save(book);

    Page<AbstractBook> page = bookRepository.findByPriceRange(
            BigDecimal.valueOf(10), BigDecimal.valueOf(30), PageRequest.of(0, 10));
    assertEquals(1, page.getTotalElements());
  }


  @Test
  public void testSaveAndFindByIsbn() {
    AbstractBook book = createTestBook("ISBN001");
    bookRepository.save(book);

    Optional<AbstractBook> found = bookRepository.findById("ISBN001");
    assertTrue(found.isPresent());
    assertEquals("Test Title", found.get().getTitle());
  }

  @Test
  public void testFindByIsbnNotFound() {
    assertFalse(bookRepository.findById("NON_EXISTENT").isPresent());
  }

  @Test
  public void testFindByTitleNoMatch() {
    Page<AbstractBook> page = bookRepository.findByTitle("No Title", PageRequest.of(0, 10));
    assertTrue(page.isEmpty());
  }

  @Test
  public void testFindByPriceRangeBoundaries() {
    AbstractBook lower = createTestBook("ISBN006");
    lower.setPrice(BigDecimal.valueOf(10.00));
    bookRepository.save(lower);

    AbstractBook upper = createTestBook("ISBN007");
    upper.setPrice(BigDecimal.valueOf(30.00));
    bookRepository.save(upper);

    Page<AbstractBook> page = bookRepository.findByPriceRange(
            BigDecimal.valueOf(10), BigDecimal.valueOf(30), PageRequest.of(0, 10)
    );
    assertEquals(2, page.getTotalElements());
  }

  @Test
  public void testFindByPriceRangeNoMatch() {
    Page<AbstractBook> page = bookRepository.findByPriceRange(
            BigDecimal.valueOf(100), BigDecimal.valueOf(200), PageRequest.of(0, 10)
    );
    assertTrue(page.isEmpty());
  }
}
