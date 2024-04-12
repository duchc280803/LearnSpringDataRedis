package com.example.learnspringdataredis.controller;

import com.example.learnspringdataredis.entity.Product;
import com.example.learnspringdataredis.param.PagingParam;
import com.example.learnspringdataredis.request.ProductRequest;
import com.example.learnspringdataredis.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/product/")
public class ProductController {

    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> selectAllProduct(PagingParam pagingParam) {
        return new ResponseEntity<>(productService.getAllProducts(pagingParam), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<String> createProduc(@RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<String> updateProduct(@RequestBody ProductRequest productRequest,
                                                @RequestParam(name = "id") Integer id
    ) {
        return new ResponseEntity<>(productService.updateProduct(productRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<Void> deleteProduct(@RequestParam(name = "id") Integer id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
