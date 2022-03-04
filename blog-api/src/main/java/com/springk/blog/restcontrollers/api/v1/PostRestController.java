package com.springk.blog.restcontrollers.api.v1;

import com.springk.blog.dtos.PostDto;
import com.springk.blog.dtos.request.PostRequest;
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
        log.info("Gets all post");
        return ResponseEntity.ok(new ResponseDto(
                HttpStatus.OK.value(),
                "Gets all post",
                _postService.findAll()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id){
        log.info("Get a post with id = "+id);
        return ResponseEntity.ok(new ResponseDto(
                HttpStatus.OK.value(),
                "Get a post",
                _postService.findById(id)
        ));
    }

    @GetMapping("/param")
    public ResponseEntity<?> getsByTitle(@RequestParam String title){
        log.info("Gets post with title = "+title);
        return ResponseEntity.ok(new ResponseDto(
                HttpStatus.OK.value(),
                "Gets post",
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

    @PostMapping("")
    public ResponseEntity<?> addNewPost(@Valid @RequestBody PostRequest postRequest){
        log.info("Creating a new post");
        return ResponseEntity.ok(new ResponseDto(
                HttpStatus.OK.value(),
                "Gets post",
                _postService.add(postRequest)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable long id,
            @Valid @RequestBody PostDto postDto){
        log.info("Updating a new post");
        return ResponseEntity.ok(new ResponseDto(
                HttpStatus.OK.value(),
                "Updated a post",
                _postService.update(postDto)
        ));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable long id){
        log.info("Deleting a post with id = "+id);
        _postService.delete(id);
        return ResponseEntity.ok(new ResponseSimple(
                HttpStatus.OK.value(),
                "Deleted a post successed"
        ));
    }
}
