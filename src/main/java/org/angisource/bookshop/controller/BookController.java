package org.angisource.bookshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.angisource.bookshop.entity.Book;
import org.angisource.bookshop.exception.NotFoundException;
import org.angisource.bookshop.response.ApiResponse;
import org.angisource.bookshop.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/book")
@RequiredArgsConstructor
@Setter
public class BookController {

    private static Logger LOGGER = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    @RequestMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<Object> getAll(WebRequest request) {
        LOGGER.debug("************** getAllBooks **************");
        List<Book> books = bookService.findAll();
        ApiResponse response = new ApiResponse(books, ((ServletWebRequest) request).getRequest().getRequestURI(), HttpStatus.OK);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET})
    public ResponseEntity<Object> getById(@PathVariable("id") long id, WebRequest request) {
        LOGGER.debug("************** Get Book with id: " + id + " **************");
        Book book = bookService.findById(id).orElseThrow(() -> new NotFoundException("The book with id=" + id + " has not been found"));

        ApiResponse response = new ApiResponse(book, ((ServletWebRequest) request).getRequest().getRequestURI(), HttpStatus.OK);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.POST})
    public ResponseEntity<Book> create(@Validated @RequestBody Book book) {
        LOGGER.debug("**************create**************");
        Book createdBook = bookService.create(book);
        if (createdBook == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdBook.getId())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(createdBook);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        LOGGER.debug("**************delete**************");
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
