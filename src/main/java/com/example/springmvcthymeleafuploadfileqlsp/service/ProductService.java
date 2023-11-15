package com.example.springmvcthymeleafuploadfileqlsp.service;

import com.example.springmvcthymeleafuploadfileqlsp.model.Product;

import java.util.ArrayList;
import java.util.List;

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
}
