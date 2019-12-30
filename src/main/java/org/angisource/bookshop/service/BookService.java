package org.angisource.bookshop.service;

import org.angisource.bookshop.entity.Book;
import org.angisource.bookshop.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public Optional<Book> findFirstByTitle(String title) {
        return bookRepository.findFirstByTitle(title);
    }

    @Override
    public Page<Book> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortType) {
        Sort sortOpts = null;
        if (sortBy != null && !sortBy.isEmpty()) {
            if (sortType.equals("desc")) {
                sortOpts = Sort.by(Sort.Order.desc(sortBy));
            } else
                sortOpts = Sort.by(Sort.Order.asc(sortBy));
        }
        Pageable paging = PageRequest.of(pageNo, pageSize, sortOpts);
        Page<Book> pagedResult = bookRepository.findAll(paging);
        return pagedResult;
    }
}
