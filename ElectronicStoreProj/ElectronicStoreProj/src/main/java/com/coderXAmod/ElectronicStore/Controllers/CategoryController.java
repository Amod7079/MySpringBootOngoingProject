package com.coderXAmod.ElectronicStore.Controllers;

import com.coderXAmod.ElectronicStore.Services.CategoryService;
import com.coderXAmod.ElectronicStore.dto.ApiResponceMessage;
import com.coderXAmod.ElectronicStore.dto.CategoryDtos;
import com.coderXAmod.ElectronicStore.dto.PagableResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
//create
    @PostMapping
public ResponseEntity<CategoryDtos> createCategory(@RequestBody CategoryDtos categoryDtos)
{
    //call service to save object becz service me hi object save hai
    CategoryDtos categoryDtos1=categoryService.create(categoryDtos);
    return new ResponseEntity<>(categoryDtos1, HttpStatus.CREATED);
}
    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDtos> updateCategory(@PathVariable String categoryId,@RequestBody CategoryDtos categoryDtos)
    {
        CategoryDtos updatedCategory = categoryService.update(categoryDtos, categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponceMessage> deleteCategory(@PathVariable String categoryId )
    {
      categoryService.delete(categoryId);
        ApiResponceMessage responcemessage = ApiResponceMessage.builder().message("Category is deleted sucessfully").status(HttpStatus.OK).build();
    return new ResponseEntity<>(responcemessage,HttpStatus.OK);
    }
    //getall
    @GetMapping
    public ResponseEntity<PagableResponse<CategoryDtos>> getAll(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir

            )
    {
        PagableResponse<CategoryDtos> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
   return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }
    //getsingle
    @GetMapping("/{categoryId}")
public ResponseEntity<CategoryDtos> getSingle(@PathVariable String categorytId)
{
    CategoryDtos categoryDtos = categoryService.get(categorytId);
    return ResponseEntity.ok(categoryDtos);

}
}
