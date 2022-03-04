package com.springk.blog.services.impls;

import com.springk.blog.dal.entities.Category;
import com.springk.blog.dal.entities.Post;
import com.springk.blog.dal.repositories.CategoryRepository;
import com.springk.blog.dal.repositories.PostRepository;
import com.springk.blog.dal.repositories.UserRepository;
import com.springk.blog.dtos.PostDto;
import com.springk.blog.dtos.request.PostRequest;
import com.springk.blog.exceptions.BadRequestException;
import com.springk.blog.exceptions.ForbiddenException;
import com.springk.blog.exceptions.ObjectNotFoundException;
import com.springk.blog.services.interfaces.IPostService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class PostService implements IPostService {

    private Type _postDtoTypes = new TypeToken<List<PostDto>>(){}.getType();
    @Autowired
    private ModelMapper _mapper;
    @Autowired
    private PostRepository _postRepository;
    @Autowired
    private UserRepository _userRepository;
    @Autowired
    private CategoryRepository _categoryRespository;

    @Override
    @Transactional
    public List<PostDto> findAll() {
        return _mapper.map(_postRepository.findAll(), _postDtoTypes);
    }

    @Override
    @Transactional
    public PostDto findById(long id) {
        Post post = _postRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Not found post with id = "+id));
        log.info("Found a post with id = " + id);
        return _mapper.map(post, PostDto.class);
    }

    @Override
    @Transactional
    public List<PostDto> findByTitle(String title) {
        return _mapper.map(_postRepository.findByTitleContaining(title), _postDtoTypes);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<PostDto> findByCategory(String titleCategory) {
        Category category = _categoryRespository.findByTitle(titleCategory)
                .orElseThrow(() -> new ObjectNotFoundException("Not found category with title "+titleCategory));
        return _mapper.map(_postRepository.findByCategoryId(category.getId()), _postDtoTypes);
    }

    @Override
    @Transactional
    public List<PostDto> findByUserId(long id) {
        return _mapper.map(_postRepository.findByUserId(id), _postDtoTypes);
    }

    @Override
    @Transactional
    public PostDto add(PostRequest postRequest) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(post.getContent());
        post.setUser(_userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new BadRequestException("Username isn't exist !")));

        Set<Category> categories = new HashSet<>();
        postRequest.getCategories().forEach(category -> {
            categories.add(_categoryRespository.findByTitle(category)
                    .orElseThrow(() -> new BadRequestException("Category isn't exist !")));
        });

        if(categories.size() == 0)
            throw new BadRequestException("Category isn't exist !");

        post.setCategories(categories);
        _postRepository.saveAndFlush(post);

        String title = post.getTitle().replaceAll(" ", "");
        StringBuilder slug = new StringBuilder();
        slug.append("/");
        slug.append(post.getUser().getUsername());
        slug.append("/");
        slug.append(post.getId());
        slug.append("/");
        slug.append(title);
        post.setSlug(slug.toString());

        return _mapper.map(_postRepository.saveAndFlush(post), PostDto.class);
    }

    @Override
    @Transactional
    public PostDto update(PostDto postDto) {
        String nameAuth = SecurityContextHolder.getContext().getAuthentication().getName();
        Post post = _postRepository.findById(postDto.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Not found post with id = " + postDto.getId()));

        //Can't update the post without created by own
        if(nameAuth != postDto.getUser().getUsername() || nameAuth != post.getUser().getUsername())
            throw new ForbiddenException("You are not permission to access this post !");

        _mapper.map(postDto, post);
        return _mapper.map(_postRepository.save(post), PostDto.class);
    }

    @Override
    @Transactional
    public void delete(long id) {
        String nameAuth = SecurityContextHolder.getContext().getAuthentication().getName();
        Post post = _postRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Not found post with id = " + id));

        //Can't delete the post without created by own
        if(nameAuth != post.getUser().getUsername())
            throw new ForbiddenException("You are not permission to access this post !");

        log.info("Deleting a post with id = "+id);
        _postRepository.deleteById(id);
        log.info("Deleted success a post with id = "+id);
    }

}
