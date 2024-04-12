package com.example.learnspringdataredis.service.impl;

import com.example.learnspringdataredis.entity.Product;
import com.example.learnspringdataredis.param.PagingParam;
import com.example.learnspringdataredis.repository.ProductRepository;
import com.example.learnspringdataredis.request.ProductRequest;
import com.example.learnspringdataredis.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl extends BaseRedisServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(
            RedisTemplate<String, Object> redisTemplate,
            ProductRepository productRepository) {
        super(redisTemplate);
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts(PagingParam pagingParam) {
        // First, get data from redis
        List<Product> productList = new ArrayList<>();
        Set<String> keys = this.getFieldPrefixes("*"); // Get all key from redis
        for (String key : keys) {
            Object obj = this.get(key);
            if (obj instanceof Product) {
                productList.add((Product) obj);
            }
        }

        // if Not available data from redis, get data from database
        if (productList.isEmpty()) {
            productList = productRepository.findAll();

            // Stores in redis
            for (Product product : productList) {
                this.set(product.getId().toString(), product.getProductName());
                this.hashSetTolive("hash_key", product.getId().toString(), product);
            }
        }

        return productList;
    }

    @Override
    public String createProduct(ProductRequest productRequest) {
        Product productNew = Product
                .builder()
                .productName(productRequest.getProductName())
                .quantity(productRequest.getQuantity())
                .status(productRequest.getStatus())
                .build();
        productRepository.save(productNew);

        // Lưu dữ liệu vào Redis
        this.set(productNew.getId().toString(), productRequest.getProductName());
        this.hashSetTolive("hash_key", productNew.getId().toString(), productNew);

        return "Create success";
    }

    @Override
    public String updateProduct(ProductRequest productRequest, Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        productOptional.ifPresent(p -> {
            p.setProductName(productRequest.getProductName());
            p.setQuantity(productRequest.getQuantity());
            p.setStatus(productRequest.getStatus());
            productRepository.save(p);
        });

        // Xóa dữ liệu cũ từ Redis và lưu dữ liệu mới
        productOptional.ifPresent(p -> {
            this.delete(p.getId().toString());
            this.hashSetTolive("hash_key", p.getId().toString(), productRequest);
        });

        return "Update success";
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
        // Xóa dữ liệu từ Redis
        this.delete(id.toString());
        this.delete("hash_key", id.toString());
    }

}
