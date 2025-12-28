package org.example.portfolio.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.portfolio.dtos.EducationDto;
import org.example.portfolio.dtos.ProjectDto;
import org.example.portfolio.entities.Project;
import org.example.portfolio.services.BlogService;
import org.example.portfolio.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    @Autowired
    private BlogService blogService;


    @PostMapping("/create-project")
    public ResponseEntity<?> createProject(@RequestParam(required = false) MultipartFile[] images, @RequestParam String projectDetailsData) throws IOException {
        return ResponseEntity.ok(ownerService.createProject(images, projectDetailsData));
    }

    @DeleteMapping("/delete-project/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        ownerService.deleteProject(projectId);
        return ResponseEntity.ok("project deleted");
    }

    @PostMapping("/update-project/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable Long projectId, @RequestBody ProjectDto projectDto) {
        return ResponseEntity.ok(ownerService.updateProject(projectId, projectDto));
    }


    @PostMapping("/create-education")
    public ResponseEntity<?> createEducation(@RequestBody EducationDto educationDto) {
        return ResponseEntity.ok(ownerService.createEducation(educationDto));
    }

    @PostMapping("/create-blog")
    public ResponseEntity<?> createBlog(@RequestParam String blogMetaData,@RequestParam MultipartFile blogContent,@RequestParam MultipartFile[] images) {
        return ResponseEntity.ok(blogService.createBlog(blogMetaData,blogContent,images));
    }
    @DeleteMapping("/delete-blog/{blogTitle}")
    public ResponseEntity<?> deleteBlog(@PathVariable String blogTitle) throws IOException {
        return ResponseEntity.ok(blogService.deleteBlog(blogTitle));
    }
}
