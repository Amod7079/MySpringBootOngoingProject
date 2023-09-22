package com.coderXAmod.ElectronicStore.Controllers;

import com.coderXAmod.ElectronicStore.Services.FileService;
import com.coderXAmod.ElectronicStore.Services.ProductService;
import com.coderXAmod.ElectronicStore.dto.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.io.InputStream;

//import static org.springframework.boot.SpringApplication.logger;

@RestController
@RequestMapping("/products")
public class ProductController
{
    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;
    @Value("${product.image.path}")
    private String imagePath;
//create
    @PostMapping
public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto)
{
    ProductDto createdProduct = productService.create(productDto);
    return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
}

    //update
    @PutMapping("{productId}")
    public ResponseEntity<ProductDto> updatedProduct(@PathVariable String productId, @RequestBody ProductDto productDto)
    {
        ProductDto updatedProduct = productService.update(productDto,productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponceMessage> delete(@PathVariable String productId)
    {
        productService.delete(productId);
        ApiResponceMessage responcemessage = ApiResponceMessage.builder().message("Product is Deleted Sucessfully:").status(HttpStatus.OK).success(true).build();
    return new ResponseEntity<>(responcemessage,HttpStatus.OK);
    }
    //get Single
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId)
    {
        ProductDto productDto = productService.get(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }
    //getAll
//    @GetMapping
//    public ResponseEntity<PagableResponse<ProductDto>> getAll(
//
//            @RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
//            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
//            @RequestParam(value="sortBy",defaultValue = "title",required = false) String sortBy,
//            @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir
//    )
//    {
//        PagableResponse<ProductDto> pagableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
//        return new ResponseEntity<>(pagableResponse,HttpStatus.OK);
//    }
    //getAll
    @GetMapping
    public ResponseEntity<PagableResponse<ProductDto>> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ) {
        PagableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }


    @GetMapping("/live")
    public ResponseEntity<PagableResponse<ProductDto>> getAllLive(

            @RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir
    )
    {
        PagableResponse<ProductDto> pagableResponse = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pagableResponse,HttpStatus.OK);
    }


    //search All
    @GetMapping("/search/{query}")
    public ResponseEntity<PagableResponse<ProductDto>> searhProduct(
@PathVariable String query,
            @RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir
    )
    {
        PagableResponse<ProductDto> pagableResponse = productService.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pagableResponse,HttpStatus.OK);
    }
    //uploadImage
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponce> uploadProductImage(
            @PathVariable String productId,
            @RequestParam("productImage")MultipartFile image
            ) throws IOException
    {
        String fileName = fileService.uploadFile(image, imagePath);
        ProductDto productDto = productService.get(productId);
    productDto.setProductImageName(fileName);
        ProductDto updatedProduct = productService.update(productDto, productId);
        ImageResponce responce = ImageResponce.builder().imageName(updatedProduct.getProductImageName()).message("Product Image is sucessfully uploaded !!").status(HttpStatus.CREATED).success(true).build();
return new ResponseEntity<>(responce,HttpStatus.CREATED);
    }
//serve Image
@GetMapping("/image/{productId}")
public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
  ProductDto productDto=productService.get(productId);
    //logger.info("User Image Name:{}",user.getName());
    InputStream resourse = fileService.getResourse(imagePath,productDto.getProductImageName());
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    StreamUtils.copy(resourse,response.getOutputStream());
}
}
