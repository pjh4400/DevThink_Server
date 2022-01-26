package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Book;
import com.devthink.devthink_server.dto.BookResponseDto;
import com.devthink.devthink_server.infra.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    /**
     * 입력된 isbn 정보로 Book을 조회하며, 없으면 새로운 Book을 생성한다.
     * @param isbn 도서 API 에서 가져온 ISBN
     * @return 조회 혹은 생성된 Book 객체
     */
    public Book getBookByIsbn(int isbn){
        Optional<Book> book = bookRepository.getBookByIsbn(isbn);
        if (book.isEmpty()) {
            return createBook(isbn);
        } else {
            return book.get();
        }
    }

    /**
     * 입력된 isbn 정보로 새로운 Book 을 등록한다.
     * @param isbn 도서 API 에서 가져온 ISBN
     * @return 생성된 Book 객체
     */
    @Transactional
    public Book createBook(int isbn){
        Book book = Book.builder().isbn(isbn).build();
        return bookRepository.save(book);
    }

    /**
     * Pagination 을 적용한 책 List를 가져온다.
     * @return BookResponseDto
     */
    public Page<BookResponseDto> getBooks(Pageable pageable){
        Page<Book> bookPage = bookRepository.findAllByReviewCntNot(0,pageable);
        List<BookResponseDto> bookResponseDtos = bookPage
                .stream()
                .map(Book::toBookResponseDto)
                .collect(Collectors.toList());
        return new PageImpl<>(bookResponseDtos,pageable,bookPage.getTotalElements());
    }


    /**
     * 리뷰 수가 가장 많은 책을 가져온다.
     * @return BookResponseDTO, 데이터가 없는 경우 null
     */
    public BookResponseDto getMostReviewCntBook(){
        if(bookRepository.findTopByOrderByReviewCntDesc().isEmpty()) {
            return null;
        } else {
            return bookRepository.findTopByOrderByReviewCntDesc().get().toBookResponseDto();
        }
    }



}
