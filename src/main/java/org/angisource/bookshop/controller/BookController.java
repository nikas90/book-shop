package org.angisource.bookshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.angisource.bookshop.entity.Book;
import org.angisource.bookshop.exception.NotFoundException;
import org.angisource.bookshop.response.ApiResponse;
import org.angisource.bookshop.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
import java.util.Map;

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
    public ResponseEntity<Object> create(@Validated @RequestBody Book book, WebRequest request) {
        LOGGER.debug("**************create**************");
        Book createdBook = bookService.create(book);
        if (createdBook == null) {
            ApiResponse response = new ApiResponse(book, ((ServletWebRequest) request).getRequest().getRequestURI(), HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(response);
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdBook.getId())
                    .toUri();
            System.out.println(uri);
            Map<String, String[]> requestMapper = request.getParameterMap();
            System.out.println(requestMapper);
            ApiResponse response = new ApiResponse(createdBook, ((ServletWebRequest) request).getRequest().getRequestURI(), HttpStatus.CREATED);
            return ResponseEntity.created(uri).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        LOGGER.debug("**************delete**************");
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/all-pageable", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<Object> getAllPageable(@RequestParam(defaultValue = "0") Integer page_no,
                                                 @RequestParam(defaultValue = "10") Integer page_size,
                                                 @RequestParam(defaultValue = "id") String sort_by,
                                                 @RequestParam(defaultValue = "ASC") String sort_type,
                                                 WebRequest request) {
        LOGGER.debug("************** getAllBooks **************");
        Page<Book> pageBooks = bookService.findAll(page_no, page_size, sort_by, sort_type);
        ApiResponse response = new ApiResponse(pageBooks, ((ServletWebRequest) request).getRequest().getRequestURI(), HttpStatus.OK);
        return ResponseEntity.ok().body(response);
    }


    @RequestMapping(value = "/all-pageable2", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<Object> getAllPageableV2(@PageableDefault(page = 0, size = 20)
                                                   @SortDefault.SortDefaults({
                                                           @SortDefault(sort = "id", direction = Sort.Direction.ASC),
                                                           @SortDefault(sort = "title", direction = Sort.Direction.DESC)
                                                   }) Pageable pageable,
                                                   WebRequest request) {

        LOGGER.debug("************** getAllBooks **************");
        Page<Book> pageBooks = bookService.findAllV2(pageable);
        ApiResponse response = new ApiResponse(pageBooks, ((ServletWebRequest) request).getRequest().getRequestURI(), HttpStatus.OK);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/all-pageable3", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<Object> getAllPageableV3(@PageableDefault(page = 0, size = 20)
                                                   @SortDefault.SortDefaults({
                                                           @SortDefault(sort = "id", direction = Sort.Direction.ASC),
                                                           @SortDefault(sort = "title", direction = Sort.Direction.DESC)
                                                   }) Pageable pageable,
                                                   WebRequest request) {

        LOGGER.debug("************** getAllBooks **************");
        Slice<Book> pageBooks = bookService.findAllV3(pageable);
        ApiResponse response = new ApiResponse(pageBooks, ((ServletWebRequest) request).getRequest().getRequestURI(), HttpStatus.OK);
        return ResponseEntity.ok().body(response);
    }

}
