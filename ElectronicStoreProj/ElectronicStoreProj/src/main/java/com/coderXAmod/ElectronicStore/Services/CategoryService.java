package com.coderXAmod.ElectronicStore.Services;

import com.coderXAmod.ElectronicStore.dto.CategoryDtos;
import com.coderXAmod.ElectronicStore.dto.PagableResponse;
import com.coderXAmod.ElectronicStore.entities.Category;

public interface CategoryService {
    //create
    CategoryDtos create (CategoryDtos categoryDtos);
    //update
    CategoryDtos update(CategoryDtos categoryDtos,String categoryId);
    //delete
    void delete(String categoryId);
    //getAll
    PagableResponse<CategoryDtos> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);


    //get Single category details
    CategoryDtos get(String categoryId);
    //search
}
