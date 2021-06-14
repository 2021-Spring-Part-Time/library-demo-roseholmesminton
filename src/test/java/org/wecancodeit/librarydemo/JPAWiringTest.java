package org.wecancodeit.librarydemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.wecancodeit.librarydemo.models.Author;
import org.wecancodeit.librarydemo.models.Book;
import org.wecancodeit.librarydemo.models.Campus;
import org.wecancodeit.librarydemo.repositories.AuthorRepository;
import org.wecancodeit.librarydemo.repositories.BookRepository;
import org.wecancodeit.librarydemo.repositories.CampusRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JPAWiringTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    private CampusRepository campusRepo;

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private BookRepository bookRepo;

@Test
    public void campusShouldHaveAListOfBooks() {
        Campus testCampus = new Campus("Test Location");
        Author testAuthor1 = new Author("Test FirstName", "Test LastName");
        Book testBook = new Book("Title", "Description", testCampus, testAuthor1);

        campusRepo.save(testCampus);
        authorRepo.save(testAuthor1);
        bookRepo.save(testBook);

        entityManager.flush();
        entityManager.clear();

        Optional<Campus> retrievedCampusOpt = campusRepo.findById(testCampus.getId());
        Campus retrievedCampus = retrievedCampusOpt.get();
        Book retrievedBook = bookRepo.findById(testBook.getId()).get();
        assertThat(retrievedCampus.getBooks()).contains(testBook);

    }
    @Test
    public void booksShouldBeAbleToHaveMultipleAuthors(){
        Campus testCampus = new Campus("Test Location");
        Author testAuthor1 = new Author("Test FirstName1", "Test LastName1");
        Author testAuthor2 = new Author("Test FirstName2","Test LastName2");
        Book testBook1 = new Book("Title1", "Description1", testCampus, testAuthor1,testAuthor2);
        Book testBook2 = new Book("Title2", "Description2", testCampus, testAuthor1);
        Book testBook3= new Book("Title3", "Description3", testCampus, testAuthor2);

        campusRepo.save(testCampus);

        authorRepo.save(testAuthor1);
        authorRepo.save(testAuthor2);

        bookRepo.save(testBook1);
        bookRepo.save(testBook2);
        bookRepo.save(testBook3);

        entityManager.flush();
        entityManager.clear();

        Book retrievedBook = bookRepo.findById(testBook1.getId()).get();
        Author retrieveAuthor1= authorRepo.findById(testAuthor1.getId()).get();
        Author retrieveAuthor2= authorRepo.findById(testAuthor2.getId()).get();
        assertThat(retrievedBook.getAuthors()).containsExactlyInAnyOrder(testAuthor1,testAuthor2);

    }
}
