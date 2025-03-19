package com.krystofstanek.Azul.Intern.Task.api.controller;

import com.krystofstanek.Azul.Intern.Task.api.model.AbstractBook;
import com.krystofstanek.Azul.Intern.Task.api.model.Genre;
import com.krystofstanek.Azul.Intern.Task.api.model.SimpleBook;
import com.krystofstanek.Azul.Intern.Task.service.BookService;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that handles RESTful endpoints for managing books.
 * Provides endpoints to add, remove, update, and retrieve books.
 */
@RestController
@RequestMapping("/books")
public class BookstoreController {

  private final BookService bookService;

  /**
   * Creates a new {@code BookstoreController} with the specified {@link BookService}.
   *
   * @param bookService the service used to manage books
   */
  @Autowired
  public BookstoreController(BookService bookService) {
    this.bookService = bookService;
  }

  /**
   * Creates a new book (accessible only to users with the ADMIN role).
   *
   * @param newBook the details of the book to be created
   * @return a {@link ResponseEntity} containing the created book
   */
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<AbstractBook> addBook(@Valid @RequestBody SimpleBook newBook) {
    AbstractBook savedBook = bookService.addBook(newBook);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
  }


  /**
   * Removes a specified quantity of a book by ISBN (accessible only to users with the ADMIN role).
   *
   * @param isbn     the ISBN of the book to remove
   * @param quantity the quantity to remove
   * @return a {@link ResponseEntity} with no content if successful
   */
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{isbn}")
  public ResponseEntity<Map<String, Integer>> removeBook(
          @PathVariable String isbn,
          @RequestParam int quantity) {

    Optional<AbstractBook> remaining = bookService.removeBook(isbn, quantity);

    if (remaining.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(Map.of("remainingQuantity", remaining.get().getQuantity()));
  }


  /**
   * Updates an existing book's details (accessible only to users with the ADMIN role).
   *
   * @param updatedBook the updated book details
   * @param isbn        the ISBN of the book to update
   * @return a {@link ResponseEntity} containing the updated book
   */
  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{isbn}")
  public ResponseEntity<AbstractBook> updateBook(
          @PathVariable String isbn,
          @RequestBody @Valid SimpleBook updatedBook) {
    return ResponseEntity.ok(bookService.updateBook(updatedBook, isbn));
  }

  /**
   * Retrieves a book by its ISBN.
   *
   * @param isbn the ISBN of the book to retrieve
   * @return a {@link ResponseEntity} containing the book if found, or an appropriate HTTP response
   */
  @GetMapping("/{isbn}")
  public ResponseEntity<AbstractBook> getBookByIsbn(@PathVariable String isbn) {
    return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
  }

  /**
   * Retrieves a page of books filtered by genre.
   *
   * @param genre the genre to filter by
   * @param page  the page number to retrieve
   * @param size  the number of items per page
   * @return a {@link ResponseEntity} containing a page of books matching the specified genre
   */
  @GetMapping("/genre")
  public ResponseEntity<Page<AbstractBook>> getBooksByGenre(
          @RequestParam Genre genre,
          @RequestParam int page,
          @RequestParam int size) {
    Page<AbstractBook> books = bookService.getBooksByAttribute("genre", genre.name(), page, size);
    return ResponseEntity.ok(books);
  }

  /**
   * Retrieves a page of books filtered by author.
   *
   * @param author the author name to filter by
   * @param page   the page number to retrieve
   * @param size   the number of items per page
   * @return a {@link ResponseEntity} containing a page of books matching the specified author
   */
  @GetMapping("/author")
  public ResponseEntity<Page<AbstractBook>>  getBooksByAuthor(
          @RequestParam String author,
          @RequestParam int page,
          @RequestParam int size) {
    Page<AbstractBook> books = bookService.getBooksByAttribute("author", author, page, size);
    return ResponseEntity.ok(books);
  }

  /**
   * Retrieves a page of books filtered by title.
   *
   * @param title the title to filter by
   * @param page  the page number to retrieve
   * @param size  the number of items per page
   * @return a {@link ResponseEntity} containing a page of books matching the specified title
   */
  @GetMapping("/title")
  public ResponseEntity<Page<AbstractBook>>  getBooksByTitle(
          @RequestParam String title,
          @RequestParam int page,
          @RequestParam int size) {
    Page<AbstractBook> books = bookService.getBooksByAttribute("title", title, page, size);
    return ResponseEntity.ok(books);
  }

  /**
   * Retrieves a page of books within the specified price range.
   *
   * @param minPrice the minimum price (inclusive)
   * @param maxPrice the maximum price (inclusive)
   * @param page     the page number to retrieve
   * @param size     the number of items per page
   * @return a {@link ResponseEntity} containing a page of books matching the specified range
   */
  @GetMapping("/price")
  public ResponseEntity<Page<AbstractBook>>  getBooksByPrice(
          @RequestParam double minPrice,
          @RequestParam double maxPrice,
          @RequestParam int page,
          @RequestParam int size) {
    Page<AbstractBook> books = bookService.getBooksByPrice(minPrice, maxPrice, page, size);
    return ResponseEntity.ok(books);
  }
}
