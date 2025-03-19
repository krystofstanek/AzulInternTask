package com.krystofstanek.Azul.Intern.Task.api.repository;

import com.krystofstanek.Azul.Intern.Task.api.model.AbstractBook;
import com.krystofstanek.Azul.Intern.Task.api.model.Genre;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



/**
 * Repository interface for performing CRUD operations on {@link AbstractBook} entities.
 */
@Repository
public interface BookRepository extends JpaRepository<AbstractBook, String> {

  /**
   * Finds books with the specified title.
   *
   * @param title    the title to search for
   * @param pageable pagination information
   * @return a page of books with the matching title
   */
  Page<AbstractBook> findByTitle(String title, Pageable pageable);

  /**
   * Finds books by the specified author.
   *
   * @param author   the author to search for
   * @param pageable pagination information
   * @return a page of books with the matching author
   */
  Page<AbstractBook> findByAuthor(String author, Pageable pageable);

  /**
   * Finds books in the specified genre.
   *
   * @param genre    the genre to search for
   * @param pageable pagination information
   * @return a page of books with the matching genre
   */
  Page<AbstractBook> findByGenre(Genre genre, Pageable pageable);


  /**
   * Finds books within the specified price range.
   *
   * @param minPrice the minimum price
   * @param maxPrice the maximum price
   * @param pageable pagination information
   * @return a page of books whose price is between the specified values
   */
  @Query("SELECT book FROM AbstractBook book WHERE book.price BETWEEN :minPrice AND :maxPrice")
  Page<AbstractBook> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}
