package com.coderXAmod.ElectronicStore.Services.Impl;

import com.coderXAmod.ElectronicStore.Services.ProductService;
import com.coderXAmod.ElectronicStore.dto.PagableResponse;
import com.coderXAmod.ElectronicStore.dto.ProductDto;
import com.coderXAmod.ElectronicStore.entities.Product;
import com.coderXAmod.ElectronicStore.helper.Helper;
import com.coderXAmod.ElectronicStore.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
   private ModelMapper mapper;
    @Override
    public ProductDto create(ProductDto productDto) {
        //product id
        String ProductId = UUID.randomUUID().toString();
        Product product = mapper.map(productDto, Product.class);
        product.setProductId(ProductId);
        //productDate
        product.setAddedDate(new Date());
        Product saveProduct = productRepository.save(product);
        return mapper.map(saveProduct, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with given id:"));
     product.setTitle(productDto.getTitle());
     product.setDescription(productDto.getDescription());
     product.setPrice(productDto.getPrice());
     product.setDiscountedPrice(productDto.getDiscountedPrice());
 product.setQuantity(product.getQuantity());
 product.setLive(productDto.isLive());
 product.setStock(productDto.isStock());
 product.setProductImageName(productDto.getProductImageName());
        Product updatedProduct = productRepository.save(product);
   return mapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with given id:"));
productRepository.delete(product);
    }

    @Override
       public ProductDto get(String productid) {
        Product product = productRepository.findById(productid).orElseThrow(() -> new RuntimeException("Product not found with given id:"));
        return mapper.map(product, ProductDto.class);
    }

    @Override
         public PagableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);


    }

    @Override
    public  PagableResponse<ProductDto>  getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public  PagableResponse<ProductDto>  searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }
}
