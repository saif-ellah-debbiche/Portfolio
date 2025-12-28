package org.example.portfolio.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.portfolio.dtos.BlogDto;
import org.example.portfolio.dtos.ProjectDto;
import org.example.portfolio.entities.Blog;
import org.example.portfolio.entities.Image;
import org.example.portfolio.enums.ImageSize;
import org.example.portfolio.enums.SavingPlace;
import org.example.portfolio.exceptions.ResourceNotFoundException;
import org.example.portfolio.repos.BlogRepo;
import org.example.portfolio.utils.FileManagement;
import org.example.portfolio.utils.ImageManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BlogService {
    @Autowired
    private ImageManagement imageManagement;
    @Autowired
    private BlogRepo blogRepo;
    @Autowired
    private ObjectMapper mapper;

    public Blog createBlog(String blogMetadataString,MultipartFile contentFile,MultipartFile[] images){
        try {
            List<Image> imageList =imageManagement.saveMultipartFilesToCloudinary(images, ImageSize.BLOG_CONTENT);
            Blog blog =getBlogDto(blogMetadataString);
            if(blog.getTitle()==null){
                blog.setTitle(contentFile.getOriginalFilename().split("\\.")[0]);
            }
            if(blogRepo.existsByTitle(blog.getTitle())){
                throw new IllegalArgumentException("Blogs with the same title exist ");
            }
            blog.setImages(imageList);
            blog.setSavingPlace(SavingPlace.LOCAL_FS);
            String content = new String(contentFile.getBytes(), StandardCharsets.UTF_8);
            Pattern pattern = Pattern.compile("!\\[\\[([^\\]]+)\\]\\]");
            Matcher matcher = pattern.matcher(content);
            StringBuffer result = new StringBuffer();

            while (matcher.find()) {
                String imageName = matcher.group(1); // captured filename (e.g. "cat.png")
                Image image = imageList.stream().filter(curImage -> curImage.getName().equals(imageName)).findFirst().orElse(null);
                String replacement;
                if(image!=null){
                    replacement= "![" + imageName + "](" + image.getDefaultUrl() + ")";
                }else{
                    replacement = matcher.group(0);
                }
                matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));

            }
            matcher.appendTail(result);
            String blogLocalPath= FileManagement.uploadFile(contentFile,result.toString());
            blog.setContentUrl(blogLocalPath);
            return blogRepo.save(blog);

        } catch (IOException e) {
            throw new RuntimeException("Error while saving images to Cloudinary "+e.getMessage());
        }

    }
    private Blog getBlogDto(String blogString) throws JsonProcessingException {
         return mapper.readValue(blogString,Blog.class);
    }


    public boolean deleteBlog(String blogTitle) throws IOException {
        Blog blog = getBlogByTitle(blogTitle);
        imageManagement.deleteImagesFromCloudinary(blog.getImages());
        FileManagement.deleteBlogByTitle(blogTitle);
        blogRepo.delete(blog);
        return true;
    }
    public Blog getBlogByTitle(String title) throws IOException {
        Blog blog=blogRepo.findByTitle(title).orElse(null);
        if(blog==null) throw new ResourceNotFoundException("Blog","Title",title);
        String content = FileManagement.getBlogFileContent(blog.getContentUrl());
        blog.setContent(content);
        return blog;
    }

    public List<Blog> getBlogs(){
        return blogRepo.findAll();
    }

}
