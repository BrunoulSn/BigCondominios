package com.api.BlogAppApi.service;

import com.api.BlogAppApi.model.BlogAppPostModel;
import com.api.BlogAppApi.repository.BlogAppPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class BlogAppPostService {

    @Autowired
    BlogAppPostRepository blogAppPostRepository;

    public List<BlogAppPostModel> findAll(){
        return blogAppPostRepository.findAll();
    }

    public  Optional<BlogAppPostModel> findById(long id){
        return blogAppPostRepository.findById(id);
    }

    @Transactional
    public BlogAppPostModel save(BlogAppPostModel post){
        return blogAppPostRepository.save(post);
    }

    @Transactional
    public void delete(BlogAppPostModel post){
        blogAppPostRepository.delete(post);
    }

}
