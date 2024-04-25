package org.blogg.Controller;

import org.blogg.Payloads.CategoryDto;
import org.blogg.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
    return new ResponseEntity<CategoryDto>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
        return new ResponseEntity<>(categoryService.updateCategory(categoryDto, categoryId), HttpStatus.OK);
    }

    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("The existing category with category ID "+categoryId+" is deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/findCategory/{categoryId}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Integer categoryId){
        return new ResponseEntity<>(categoryService.findById(categoryId), HttpStatus.OK);
    }

    @GetMapping("/viewAllCategory")
    public List<CategoryDto> viewAllCategory(){
        return categoryService.viewAllCategory();
    }
}
