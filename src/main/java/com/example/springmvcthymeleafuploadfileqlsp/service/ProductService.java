package com.example.springmvcthymeleafuploadfileqlsp.service;

import com.example.springmvcthymeleafuploadfileqlsp.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProductService implements IProductService{
    List<Product> products = new ArrayList<>();

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public Product findById(int id) {
        return products.get(id);
    }

    @Override
    public void update(int id, Product product) {
        //  tìm vị trí của sản phẩm có id tương ứng trong danh sách products
        int index = products.indexOf(findById(id));
        // Nếu sản phẩm có tồn tại sẽ cập nhật thông tin của sản phẩm tại vị trí index đó bằng thông tin mới là product
        products.set(index, product);
    }

    @Override
    public void remove(int id) {
        products.remove(findById(id));
    }

    @Override
    public List<Product> search(String keyword) {
        return products.stream()
                .filter(product -> product.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
