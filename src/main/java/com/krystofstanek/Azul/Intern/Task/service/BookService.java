package com.krystofstanek.Azul.Intern.Task.service;

import com.krystofstanek.Azul.Intern.Task.api.model.AbstractBook;
import com.krystofstanek.Azul.Intern.Task.api.model.Genre;
import com.krystofstanek.Azul.Intern.Task.api.repository.BookRepository;
import com.krystofstanek.Azul.Intern.Task.exceptions.BookNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * Service class for managing book operations.
 * Provides methods to add, remove, update, and retrieve books based on various attributes.
 */
@Service
public class BookService {

  private final BookRepository bookRepository;

  /**
   * Constructs a new {@code BookService} with the specified {@link BookRepository}.
   *
   * @param bookRepository the repository for book data
   */
  @Autowired
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  /**
   * Adds a book to the repository.
   * If a book with the same ISBN already exists,
   * increments its quantity by the quantity of the new book.
   * Otherwise, saves the new book.
   *
   * @param book the book to add
   * @return the saved {@link AbstractBook} instance
   */
  @Transactional
  public AbstractBook addBook(AbstractBook book) {
    Optional<AbstractBook> existingBook = bookRepository.findById(book.getIsbn());
    if (existingBook.isPresent()) {
      AbstractBook databaseBook = existingBook.get();
      databaseBook.updateQuantity(book.getQuantity());
      return bookRepository.save(databaseBook);
    } else {
      return bookRepository.save(book);
    }
  }

  /**
   * Removes a specified amount of a book's quantity from the repository.
   * If the removal amount results in a quantity of zero, the book is deleted from the repository.
   * If the book is not found, a {@link BookNotFoundException} is thrown.
   *
   * @param isbn           the ISBN of the book to remove; must not be null or blank
   * @param amountToRemove the amount to remove from the book's quantity; must be greater than zero
   * @return an {@link Optional} containing the updated {@link AbstractBook} if it still exists, or empty if it was removed
   * @throws IllegalArgumentException if the ISBN is null or blank, or if the removal amount is less than or equal to zero
   * @throws BookNotFoundException    if no book with the specified ISBN is found
   */
  @Transactional
  public Optional<AbstractBook> removeBook(String isbn, int amountToRemove) {
    if (isbn == null || isbn.isBlank()) {
      throw new IllegalArgumentException("ISBN must not be null or blank");
    }
    if (amountToRemove <= 0) {
      throw new IllegalArgumentException("Amount to remove must be greater than zero.");
    }

    Optional<AbstractBook> bookOptional = bookRepository.findById(isbn);
    if (bookOptional.isEmpty()) {
      throw new BookNotFoundException("Book with ISBN " + isbn + " not found.");
    }

    AbstractBook book = bookOptional.get();
    book.updateQuantity(-amountToRemove);

    if (book.getQuantity() == 0) {
      bookRepository.delete(book);
      return Optional.empty();
    }

    return Optional.of(bookRepository.save(book));
  }



  /**
   * Updates the details of an existing book.
   * The book identified by the provided ISBN is
   * updated with the details from the given updated book.
   *
   * @param updatedBook the book containing updated details; must not be null
   * @param isbn        the ISBN of the book to update; must not be null or blank
   * @return the updated {@link AbstractBook} instance
   * @throws IllegalArgumentException if the ISBN is null or blank, or if updatedBook is null
   * @throws BookNotFoundException if no book with the specified ISBN is found
   */
  @Transactional
  public AbstractBook updateBook(AbstractBook updatedBook, String isbn) {
    if (isbn == null || isbn.isBlank()) {
      throw new IllegalArgumentException("ISBN must not be null or blank");
    }
    if (updatedBook == null) {
      throw new IllegalArgumentException("Updated book must not be null");
    }

    Optional<AbstractBook> bookOptional = bookRepository.findById(isbn);
    if (bookOptional.isEmpty()) {
      throw new BookNotFoundException("Book not found: " + isbn);
    }

    AbstractBook existing = bookOptional.get();
    existing.setTitle(updatedBook.getTitle());
    existing.setAuthor(updatedBook.getAuthor());
    existing.setGenre(updatedBook.getGenre());
    existing.setPrice(updatedBook.getPrice());
    return bookRepository.save(existing);
  }



  /**
   * Retrieves a page of books filtered by a specified attribute.
   * Supported filter types are "genre", "title", and "author".
   * For the "genre" filter, the provided filter value
   * should correspond to a valid {@link Genre} enumeration.
   *
   * @param filterType  the type of attribute to filter by (e.g., "genre", "title", "author"); must not be null or blank
   * @param filterValue the value of the attribute to filter by; must not be null or blank
   * @param page        the page number to retrieve; must be >= 0
   * @param size        the number of items per page; must be > 0
   * @return a page of books matching the specified filter
   * @throws IllegalArgumentException if filterType or filterValue is null or blank,
   *                                  if an invalid filter type is provided,
   *                                  if page < 0 or size <= 0,
   *                                  or if an invalid genre is provided when filtering by genre
   */
  public Page<AbstractBook> getBooksByAttribute(String filterType, String filterValue, int page, int size) {
    if (filterType == null || filterType.isBlank()) {
      throw new IllegalArgumentException("Filter type must not be null or blank");
    }
    if (filterValue == null || filterValue.isBlank()) {
      throw new IllegalArgumentException("Filter value must not be null or blank");
    }
    if (page < 0 || size <= 0) {
      throw new IllegalArgumentException("Page must be >= 0 and size must be > 0");
    }
    Pageable pageable = PageRequest.of(page, size);
    switch (filterType.toLowerCase()) {
      case "genre":
        try {
          Genre genre = Genre.valueOf(filterValue.toUpperCase());
          return bookRepository.findByGenre(genre, pageable);
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Invalid genre: " + filterValue);
        }
      case "title":
        return bookRepository.findByTitle(filterValue, pageable);
      case "author":
        return bookRepository.findByAuthor(filterValue, pageable);
      default:
        throw new IllegalArgumentException("Invalid filter type: " + filterType);
    }
  }

  /**
   * Retrieves a page of books within a specified price range.
   *
   * @param minPrice the minimum price (inclusive); must not be negative
   * @param maxPrice the maximum price (inclusive); must not be negative and must be greater than or equal to minPrice
   * @param page     the page number to retrieve; must be >= 0
   * @param size     the number of items per page; must be > 0
   * @return a page of books whose prices fall within the specified range
   * @throws IllegalArgumentException if minPrice or maxPrice is negative, if minPrice is greater than maxPrice,
   *                                  or if page/size values are invalid (page < 0 or size <= 0)
   */
  public Page<AbstractBook> getBooksByPrice(double minPrice, double maxPrice, int page, int size) {
    if (minPrice < 0 || maxPrice < 0) {
      throw new IllegalArgumentException("Prices must not be negative");
    }
    if (minPrice > maxPrice) {
      throw new IllegalArgumentException("minPrice cannot be greater than maxPrice");
    }
    if (page < 0 || size <= 0) {
      throw new IllegalArgumentException("Page must be >= 0 and size must be > 0");
    }
    Pageable pageable = PageRequest.of(page, size);
    return bookRepository.findByPriceRange(
            BigDecimal.valueOf(minPrice),
            BigDecimal.valueOf(maxPrice),
            pageable);
  }


  /**
   * Retrieves a book by its ISBN.
   *
   * @param isbn the ISBN to look up; must not be null/blank
   * @return the matching AbstractBook
   * @throws IllegalArgumentException if the ISBN is null or blank
   * @throws BookNotFoundException if no book is found with the given ISBN
   */
  public AbstractBook getBookByIsbn(String isbn) {
    if (isbn == null || isbn.isBlank()) {
      throw new IllegalArgumentException("ISBN must not be null or blank");
    }
    Optional<AbstractBook> existingBook = bookRepository.findById(isbn);
    if (existingBook.isEmpty()) {
      throw new BookNotFoundException("Book with ISBN " + isbn + " not found.");
    }
    return existingBook.get();
  }
}
