package com.example.springmvcthymeleafuploadfileqlsp.controller;

import com.example.springmvcthymeleafuploadfileqlsp.model.Product;
import com.example.springmvcthymeleafuploadfileqlsp.model.ProductForm;
import com.example.springmvcthymeleafuploadfileqlsp.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    ProductService productService = new ProductService();

    @Value("${file-upload}")
    private String fileUpload;

    @GetMapping("/home")
    public String list(Model model){
        List<Product> productList = productService.findAll();
        model.addAttribute("productL", productList);
        return "/home";
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("productForm", new ProductForm());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute ProductForm productForm) {
        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            System.out.println(ex);
        }
        Product product = new Product(productForm.getId(), productForm.getName(), productForm.getPrice(), productForm.getDescription(), productForm.getBrand(), fileName);
        productService.save(product);
        return "redirect:/products/home";
    }

    @GetMapping("/update")
    public ModelAndView showEditForm(@ModelAttribute ProductForm productForm){
        Product product = productService.findById(productForm.getId());
        ModelAndView modelAndView = new ModelAndView("/update");
        modelAndView.addObject("productEdit", product);
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String edit(@PathVariable int id, @ModelAttribute ProductForm productForm){
        Product product = productService.findById(productForm.getId());
        Product product1 = null;
        MultipartFile multipartFile = productForm.getImage();

        if (!multipartFile.isEmpty()){
            String fileName = multipartFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload + fileName));
            } catch (IOException ex) {
                System.out.println(ex);
            }
            product1 = new Product(productForm.getId(), productForm.getName(), productForm.getPrice(), productForm.getDescription(), productForm.getBrand(), fileName);
        } else {
            product1 = new Product(productForm.getId(), productForm.getName(), productForm.getPrice(), productForm.getDescription(), productForm.getBrand(), product.getImage());
        }

        productService.update(productForm.getId(), product1);
        return "redirect:/products/home";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, @ModelAttribute ProductForm productForm){
        productService.remove(productForm.getId());
        return "redirect:/products/home";
    }

    @GetMapping("/view/{id}")
    public String View(@PathVariable int id, Model model){
        ProductForm productForm = new ProductForm();
        model.addAttribute("productView", productService.findById(productForm.getId()));
        return "/view";
    }

    @PostMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model){
        List<Product> searchProduct = productService.search(keyword);
        model.addAttribute("productSearch", searchProduct);
        return "/home";
    }
}
