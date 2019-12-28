package org.angisource.bookshop.repository;

import org.angisource.bookshop.entity.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    Book findbyTitle(String title);
}
