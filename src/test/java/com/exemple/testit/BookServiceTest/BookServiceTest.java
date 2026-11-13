package com.exemple.testit.BookServiceTest;

import com.example.testit.adapter.mail.MailService;
import com.example.testit.model.Book;
import com.example.testit.model.Member;
import com.example.testit.model.Status;
import com.example.testit.repository.BookRepository;
import com.example.testit.repository.MemberRepository;
import com.example.testit.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class BookServiceTest {
    BookService bookService;
    BookRepository bookRepository;
    MemberRepository memberRepository;
    MailService mailService;

    @BeforeEach
    void setUp() {
        bookRepository = Mockito.mock(BookRepository.class);
        memberRepository = Mockito.mock(MemberRepository.class);
        mailService = Mockito.mock(MailService.class);
        bookService = new BookService(bookRepository, memberRepository, mailService);
    }
    @Test
    public void test1() {
        //given
        var book1 = new Book("Titre1", "Auteur1");
        var member1 = new Member("Username1");
        //when
        book1.setStatus(Status.DISPONIBLE);
        book1.setId(1L);
        member1.setId(2L);
        //then
        Mockito.when(bookRepository.findById(book1.getId())).thenReturn(Optional.of(book1));
        Mockito.when(memberRepository.findById(member1.getId())).thenReturn(Optional.of(member1));
        Mockito.when(bookRepository.save(book1)).thenReturn(book1);

        Book result = bookService.borrowBook(book1.getId(),member1.getId());

        assertFalse(result.getStatus()==Status.DISPONIBLE,"Livre bien emprunte");
    }
}
