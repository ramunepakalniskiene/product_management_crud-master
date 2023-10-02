package com.example.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/")
    public String displayUserList(@RequestParam(required = false) String message,
                                  @RequestParam(required = false) String error,
                                  Model model) {
        model.addAttribute("message", message);
        model.addAttribute("error", error);
        model.addAttribute("productList", productService.getAllProducts());
        return "productList";
    }


    @GetMapping("/sort-product")
    public String displayUserListSortedByName(@RequestParam(required = false) String message,
                                  @RequestParam(required = false) String error,
                                  Model model) {
        model.addAttribute("message", message);
        model.addAttribute("error", error);
        model.addAttribute("productList", productService.getProductsSortedByName());
        return "productList";
    }
    @GetMapping("/sort-product-by-description")
    public String displayUserListSortedByDescription(@RequestParam(required = false) String message,
                                              @RequestParam(required = false) String error,
                                              Model model) {
        model.addAttribute("message", message);
        model.addAttribute("error", error);
        model.addAttribute("productList", productService.getProductsSortedByDescription());
        return "productList";
    }
    @GetMapping("/sort-product-by-category")
    public String displayUserListSortedByCategory(@RequestParam(required = false) String message,
                                              @RequestParam(required = false) String error,
                                              Model model) {
        model.addAttribute("message", message);
        model.addAttribute("error", error);
        model.addAttribute("productList", productService.getProductsSortedByCategory());
        return "productList";
    }
    @GetMapping("/sort-product-by-price")
    public String displayUserListSortedByPrice(@RequestParam(required = false) String message,
                                              @RequestParam(required = false) String error,
                                              Model model) {
        model.addAttribute("message", message);
        model.addAttribute("error", error);
        model.addAttribute("productList", productService.getProductsSortedByPrice());
        return "productList";
    }


    @GetMapping("/add-product")
    public String displayAddProductPage() {
        return "addProduct";
    }

    @PostMapping("/add-product")
    public String createProduct(Product product) {
        try {
            this.productService.createProduct(product);
            return "redirect:/?message=PRODUCT_CREATED_SUCCESS";
        } catch (Exception exception) {
            return "redirect:/?message=PRODUCT_CREATION_FAILED&error=" + exception.getMessage();
        }

    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable() Long id) {
        try {
            productService.deleteProduct(id);
            return "redirect:/?message=PRODUCT_DELETED_SUCCESSFULLY";

        } catch (Exception exception) {
            return "redirect:/?message=PRODUCT_DELETE_FAILED&error=" + exception.getMessage();

        }
    }

    @GetMapping("/sell-product/{id}")
    public String sellProduct(@PathVariable Long id, Model model) {
        try {
            Product sellProduct = productService.findProductById(id);
             if (sellProduct.getQuantity() > 0) {
             //   sellProduct.setQuantity(sellProduct.getQuantity() - quantityToSell);
                productService.updateProductQuantity(sellProduct);
               //  model.addAttribute("quantityToSell",0);
                return "redirect:/?message=PRODUCT_SOLD_SUCCESS";
            } else {
                return "redirect:/?message=PRODUCT_SOLD_FAILED&error=Not enough quantity to sell";
            }
        } catch (Exception exception) {
            return "redirect:/?message=PRODUCT_SOLD_FAILED&error=" + exception.getMessage();
        }
    }


   @GetMapping("/edit/{id}")
    public String showEditProductPage(@PathVariable Long id, Model model) {
        try {
            Product foundProduct = productService.findProductById(id);
            model.addAttribute("productItem", foundProduct);
            return "editProduct";
        } catch (Exception exception) {
            return "redirect:/?message=PRODUCT_EDIT_FAILED&error=" + exception.getMessage();
        }
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Product product) {
        try {
            product.setId(id);
            productService.updateProduct(product);
            return "redirect:/?message=PRODUCT_EDITED_SUCCESSFULLY";
        } catch (Exception exception) {
            return "redirect:/?message=PRODUCT_EDIT_FAILED&error=" + exception.getMessage();
        }
    }
    @GetMapping(value = "filters")
    public String findProductByName(@RequestParam (value = "name", required = true) String name, Model model) {
        model.addAttribute("search", productService.findProductByName(name));
        return "filters";
    }
//    //*************************************************************************
//    @RequestMapping(value = "/sell-product/{id}", method = RequestMethod.POST)
//    public String handle(@ModelAttribute ("sellProduct") Product product, BindingResult result, RedirectAttributes redirectAttrs) {
//        if (result.hasErrors()) {
//            redirectAttrs.addFlashAttribute("error", "The error XYZ occurred.");
//            return "redirect:/products/";
//
//        } else
//        // Save account ...
//        redirectAttrs.addAttribute("id", product.getId()).addFlashAttribute("success", "Product sold!");
//        return "redirect:/addProduct";
//    }

}