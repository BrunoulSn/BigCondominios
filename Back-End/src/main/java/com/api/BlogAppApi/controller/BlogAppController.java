package com.api.BlogAppApi.controller;

import com.api.BlogAppApi.DTOs.BlogAppRecordDto;
import com.api.BlogAppApi.model.BlogAppPostModel;
import com.api.BlogAppApi.service.BlogAppPostService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
public class BlogAppController {

    @Autowired
    BlogAppPostService blogAppPostService;

    //Lista Todos os Posts
    @GetMapping(value = "/posts")
    public ResponseEntity<List<BlogAppPostModel>> getAllPosts(){
        List<BlogAppPostModel> posts = blogAppPostService.findAll();
        if(posts.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(posts);
        }
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping(value= "/posts/{id}")
    public ResponseEntity<Object> getPostDetails(@PathVariable long id){
        Optional<BlogAppPostModel> blogAppPostModelOptional = blogAppPostService.findById(id);
        if (!blogAppPostModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("blog not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(blogAppPostModelOptional.get());
    }

    @PostMapping(value = "/newpost")
    public ResponseEntity<Object> saveNewPost(@RequestBody @Valid BlogAppRecordDto blogAppDto){
        var postModel = new BlogAppPostModel();
        BeanUtils.copyProperties(blogAppDto, postModel);
        postModel.setData(LocalDate.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(blogAppPostService.save(postModel));
    }

    @DeleteMapping("/posts/{id}")
            public ResponseEntity<Object> deletePost (@PathVariable(value = "id") long id){
        Optional<BlogAppPostModel> blogAppPostModelOptional = blogAppPostService.findById(id);
        if(!blogAppPostModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("blog not found");
        }
        blogAppPostService.delete(blogAppPostModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("blog deleted successfully");
    }

}
