package com.coderXAmod.ElectronicStore.Services.Impl;

import com.coderXAmod.ElectronicStore.Exception.ResourseNotFoundException;
import com.coderXAmod.ElectronicStore.Services.CategoryService;
import com.coderXAmod.ElectronicStore.dto.CategoryDtos;
import com.coderXAmod.ElectronicStore.dto.PagableResponse;
import com.coderXAmod.ElectronicStore.entities.Category;
import com.coderXAmod.ElectronicStore.helper.Helper;
import com.coderXAmod.ElectronicStore.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
@Autowired
private CategoryRepository categoryRepository;
@Autowired
private ModelMapper mapper;
    @Override
    public CategoryDtos create(CategoryDtos categoryDtos) {
        String categoryId = UUID.randomUUID().toString();
        categoryDtos.setCategoryId(categoryId);
        Category category = mapper.map(categoryDtos, Category.class);


        Category savedCategory = categoryRepository.save(category);
        return mapper.map(savedCategory,CategoryDtos.class);
    }

    @Override
    public CategoryDtos update(CategoryDtos categoryDtos, String categoryId) {
        //pehle get kr lo then uske baad update krna
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category not found exception!!"));
    //update category details
        category.setTitle(categoryDtos.getTitle());
        category.setDescription(categoryDtos.getDescription());
        category.setCoverImage(categoryDtos.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);
        return mapper.map(updatedCategory,CategoryDtos.class);
    }

    @Override
    public void delete(String categoryId) {
        //creating category id randomly


        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category not found exception!!"));
categoryRepository.delete(category);
    }

    @Override
    public PagableResponse<CategoryDtos> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending()) ;
        PageRequest pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PagableResponse<CategoryDtos> pageableResponse = Helper.getPageableResponse(page, CategoryDtos.class);
return pageableResponse;
    }

    @Override
    public CategoryDtos get(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category not found exception!!"));
return mapper.map(category,CategoryDtos.class);
    }
}
