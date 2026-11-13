package com.exemple.testit.BookRepositoryIT;

import com.example.testit.adapter.mail.MailService;
import com.example.testit.model.Book;
import com.example.testit.model.Member;
import com.example.testit.model.Status;
import com.example.testit.repository.BookRepository;
import com.example.testit.repository.MemberRepository;
import com.example.testit.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryIT {
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
    public void Test2(){
        //given
        Book book1 = new Book("Titre1", "Auteur1");
        Book book2 = new Book("Titre2", "Auteur2");
        Book book3 = new Book("Titre3", "Auteur3");
        Member member1 = new Member("Username1");
        Member member2 = new Member("Username2");
        //when
        book1.setStatus(Status.DISPONIBLE);
        book2.setStatus(Status.DISPONIBLE);
        book3.setStatus(Status.DISPONIBLE);
        book1.setId(1L);
        book2.setId(2L);
        book3.setId(3L);
        member1.setId(4L);
        member2.setId(5L);
        //then
        Mockito.when(bookRepository.findById(book1.getId())).thenReturn(Optional.of(book2));
        Mockito.when(bookRepository.findById(book2.getId())).thenReturn(Optional.of(book2));
        Mockito.when(bookRepository.findById(book3.getId())).thenReturn(Optional.of(book3));
        Mockito.when(memberRepository.findById(member1.getId())).thenReturn(Optional.of(member1));
        Mockito.when(memberRepository.findById(member2.getId())).thenReturn(Optional.of(member2));
        Mockito.when(bookRepository.save(book1)).thenReturn(book1);
        Mockito.when(bookRepository.save(book2)).thenReturn(book2);
        Mockito.when(bookRepository.save(book3)).thenReturn(book3);
        bookService.borrowBook(book1.getId(),member1.getId());
        //bookService.borrowBook(book2.getId(),member2.getId());
        //bookService.borrowBook(book3.getId(),member2.getId());

        List<Book> result1 = bookRepository.findByMemberAndStatus(member1.getId(),Status.DISPONIBLE);
        List<Book> result2 = bookRepository.findByMemberAndStatus(member2.getId(),Status.DISPONIBLE);

        assertEquals(1, result1.size());
        //assertEquals(result2.size(), 2);
    }

}
