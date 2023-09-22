package com.coderXAmod.ElectronicStore.Services;

import com.coderXAmod.ElectronicStore.dto.PagableResponse;
import com.coderXAmod.ElectronicStore.dto.ProductDto;
import com.coderXAmod.ElectronicStore.entities.Product;

import java.util.List;

public interface ProductService {
    //create
    ProductDto create (ProductDto productDto);
    //update
    ProductDto update (ProductDto productDto,String productId);
    //delete
    void delete (String productId);
    //get single
    ProductDto  get(String productid);
    //getAll
    PagableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);
    //get All :live
    PagableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir);
    //search product
    PagableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir);
    //other methods

}

