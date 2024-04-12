package com.example.learnspringdataredis.service;

import com.example.learnspringdataredis.entity.Product;
import com.example.learnspringdataredis.param.PagingParam;
import com.example.learnspringdataredis.request.ProductRequest;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts(PagingParam pagingParam);

    String createProduct(ProductRequest productRequest);

    String updateProduct(ProductRequest productRequest, Integer id);

    void deleteProduct(Integer id);
}
