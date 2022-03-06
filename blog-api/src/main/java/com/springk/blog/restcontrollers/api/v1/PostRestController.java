package com.springk.blog.restcontrollers.api.v1;

import com.springk.blog.dtos.PostDto;
import com.springk.blog.dtos.request.PostRequest;
import com.springk.blog.dtos.request.UpdatePostRequest;
import com.springk.blog.dtos.response.ResponseDto;
import com.springk.blog.dtos.response.ResponseSimple;
import com.springk.blog.services.interfaces.IPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/v1/post")
public class PostRestController {
    @Autowired
    private IPostService _postService;

    @GetMapping("")
    public ResponseEntity<?> getsAll(){
        log.info("Get all post");
        return ResponseEntity.ok(new ResponseDto(
                HttpStatus.OK.value(),
                "Get all post",
                _postService.findAll()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id){
        log.info("Get a post with id = "+id);
        return ResponseEntity.ok(new ResponseDto(
                HttpStatus.OK.value(),
                "Gets a post",
                _postService.findById(id)
        ));
    }

    @GetMapping("/param")
    public ResponseEntity<?> getsByTitle(@RequestParam String title){
        log.info("Get list post with title = "+title);
        return ResponseEntity.ok(new ResponseDto(
                HttpStatus.OK.value(),
                "Get list post by title",
                _postService.findByTitle(title)
        ));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> findByCategory(
            @PathVariable String category){
        log.info("Finding list posts with category");
        return ResponseEntity.ok(new ResponseDto(
                HttpStatus.OK.value(),
                "Get list posts",
                _postService.findByCategory(category)
        ));
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<?> findByAuthorId(
            @PathVariable(name = "id") long id){
        log.info("Finding list posts with author id");
        return ResponseEntity.ok(new ResponseDto(
                HttpStatus.OK.value(),
                "Get list posts with author id = "+id,
                _postService.findByUserId(id)
        ));
    }

    @PostMapping("")
    public ResponseEntity<?> addNewPost(@Valid @RequestBody PostRequest postRequest){
        log.info("Creating a new post");
        return ResponseEntity.ok(new ResponseDto(
                HttpStatus.OK.value(),
                "Created a new post",
                _postService.add(postRequest)
        ));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePost(
            @Valid @RequestBody UpdatePostRequest updatePostRequest){
        log.info("Updating a new post");
        return ResponseEntity.ok(new ResponseDto(
                HttpStatus.OK.value(),
                "Updated a post",
                _postService.update(updatePostRequest)
        ));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(
            @PathVariable(name = "id") long id){
        log.info("Deleting a post with id = "+id);
        _postService.delete(id);
        return ResponseEntity.ok(new ResponseSimple(
                HttpStatus.OK.value(),
                "Deleted a post successed"
        ));
    }

}
