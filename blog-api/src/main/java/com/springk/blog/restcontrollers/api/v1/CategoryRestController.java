package com.springk.blog.restcontrollers.api.v1;

import com.springk.blog.dtos.CategoryDto;
import com.springk.blog.dtos.request.CategoryRequest;
import com.springk.blog.dtos.response.ResponseDto;
import com.springk.blog.services.interfaces.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/v1/category")
public class CategoryRestController {

    @Autowired
    private ICategoryService _categoryService;

    @GetMapping("")
    public ResponseEntity<?> getsAll(){
        log.info("Gets all category");
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Gets all category successed", _categoryService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id){
        log.info("Get a category with id = "+id);
        CategoryDto category = _categoryService.findById(id);
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Get a category successed", category)) ;
    }

    @GetMapping("/{title}")
    public ResponseEntity<?> getByTitle(@PathVariable String title){
        log.info("Get a category with title = "+title);
        CategoryDto category = _categoryService.findByTitle(title);
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Get a category successed", category)) ;
    }

    @PostMapping("")
    public ResponseEntity<?> addNewCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        log.info("Creating a category with title: "+categoryRequest.getTitle());
        CategoryDto category = _categoryService.add(categoryRequest);
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Created a category successed", category)) ;
    }

    @PutMapping("/{title}")
    public ResponseEntity<?> updateCategory(
            @PathVariable String title,
            @Valid @RequestBody CategoryDto categoryDto){
        log.info("Updating a category with id = "+categoryDto.getId());
        CategoryDto category = _categoryService.update(categoryDto);
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Updated a category successed", category)) ;
    }
}
