package org.angisource.bookshop.service;

import org.angisource.bookshop.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface IBookService {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Book create(Book book);

    void delete(Long id);

    void delete(Book book);

    Optional<Book> findFirstByTitle(String title);

    Page<Book> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortType);

    Page<Book> findAllV2(Pageable pageable);

    Slice<Book> findAllV3(Pageable pageable);
}
