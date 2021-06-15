package org.wecancodeit.librarydemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.wecancodeit.librarydemo.models.Campus;
import org.wecancodeit.librarydemo.repositories.AuthorRepository;
import org.wecancodeit.librarydemo.repositories.BookRepository;
import org.wecancodeit.librarydemo.repositories.CampusRepository;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class WebLayerTests {

    @MockBean
    private CampusRepository campusRepo;

    @MockBean
    private BookRepository bookRepo;

    @MockBean
    private AuthorRepository authorRepo;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void campusesShouldBeOkAndReturnCampusesViewWithCampusesModelAttribute() throws Exception{
        mockMvc.perform(get("/campuses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("campusesView"))
                .andExpect(model().attributeExists("campuses"));
    }
    @Test
    public void booksShouldBeOkAndReturnBooksViewWithsModelBooksAttribute() throws Exception{
        mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("booksView"))
                .andExpect(model().attributeExists("books"));
    }

    @Test
    public void authorShouldBeOkAndReturnAuthorsViewWithsModelAuthorAttribute() throws Exception{
        mockMvc.perform(get("/authors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("authorsView"))
                .andExpect(model().attributeExists("authors"));
    }
    @Test
    public void shouldBeOkForASingleCampusEndpointWithCampusViewAndCampusModelAttribute() throws Exception{
        Campus testCampus = new Campus("Columbus");
        when(campusRepo.findCampusByLocation("Columbus")).thenReturn(testCampus);
        mockMvc.perform(get("/campuses/Columbus"))
                .andExpect(status().isOk())
                .andExpect(view().name("campusView"))
                .andExpect(model().attributeExists("campus"));
    }
}