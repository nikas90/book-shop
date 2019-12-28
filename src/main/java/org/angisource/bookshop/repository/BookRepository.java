package org.angisource.bookshop.repository;

import org.angisource.bookshop.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long>, JpaRepository<Book, Long> {
    Optional<Book> findFirstByTitle(String title);
}
