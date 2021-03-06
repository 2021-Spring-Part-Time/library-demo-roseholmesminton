package org.wecancodeit.librarydemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wecancodeit.librarydemo.repositories.AuthorRepository;
import org.wecancodeit.librarydemo.repositories.BookRepository;

import javax.annotation.Resource;
@Controller
public class AuthorController {
    @Resource
    private AuthorRepository authorRepo;

    @RequestMapping("/authors")
    public String displayAuthors(Model model){
        model.addAttribute("authors",authorRepo.findAll());
        return "authorsView";
    }
}

