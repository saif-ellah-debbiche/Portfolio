package org.example.portfolio.controllers;


import org.example.portfolio.entities.Blog;
import org.example.portfolio.entities.Project;
import org.example.portfolio.services.BlogService;
import org.example.portfolio.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientsController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private OwnerService ownerService;

    @GetMapping("/test")
    public String sayHello(){
        return "Hello";
    }


    @GetMapping("/blogs")
    public List<Blog> getBlogs(){
        return blogService.getBlogs();
    }
    @GetMapping("/blogs/{blogTitle}")
    public Blog getBlogs(@PathVariable String blogTitle) throws IOException {
        return blogService.getBlogByTitle(blogTitle);
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> updateProject() {
        return ResponseEntity.ok(ownerService.getProjects());
    }


}
