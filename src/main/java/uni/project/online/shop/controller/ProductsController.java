package uni.project.online.shop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import uni.project.online.shop.api.ProductsApi;
import uni.project.online.shop.model.Product;
import uni.project.online.shop.service.AuthService;
import uni.project.online.shop.service.ProductService;

import java.util.List;

@RestController
public class ProductsController implements ProductsApi {
    @Autowired
    private ProductService productService;
    @Autowired
    AuthService authService;

    @Override
    public List<Product> getFilteredProducts(String category, String sort, String type, String brand, Double price, Double rate, String targetGroup) {
        return productService.getFilteredProducts(category, sort, type, brand, price, rate, targetGroup);
    }

//    @Override
//    public List<Product> getFaceCare(String sort, String type, String brand, Double price, Double rate, String targetGroup) {
//        return productService.getFaceCare(sort, type, brand, price, rate, targetGroup);
//    }
//
//    @Override
//    public List<Product> getHairCare(String sort, String type, String brand, Double price, Double rate, String targetGroup) {
//        return productService.getHairCare(sort, type, brand, price, rate, targetGroup);
//    }
//
//    @Override
//    public List<Product> getBodyCare(String sort, String type, String brand, Double price, Double rate, String targetGroup) {
//        return productService.getBodyCare(sort, type, brand, price, rate, targetGroup);
//    }
//
//    @Override
//    public List<Product> getPerfumes(String sort, String type, String brand, Double price, Double rate, String targetGroup) {
//        return productService.getPerfumes(sort, type, brand, price, rate, targetGroup);
//    }
//
//    @Override
//    public List<Product> getAccessories(String sort, String type, String brand, Double price, Double rate, String targetGroup) {
//        return productService.getAccessories(sort, type, brand, price, rate, targetGroup); //todo
//    }

    @Override
    public List<Product> getHomeScreenProducts(String category) {
        return productService.getHomeScreenProducts(category);
    }

    @Override
    public List<Product> getProductsOnSale() {
        return productService.getProductsOnSale();
    }

//    @Override
//    public List<Product> getHighRateProducts() {
//        return productService.getHighestRateProducts();
//    }
//
//    @Override
//    public List<Product> getMostOrderedProducts() {
//        return productService.getMostOrderedProducts();
//    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productService.getProductsByBrand(brand); //todo
    }

    @Override
    public List<Product> searchProducts(String search) {
        return productService.search(search);
    }

    @Override
    public Product getProduct(Long id) {
        return productService.getProduct(id);
    }

    @Override
    public void rateProduct(Long id, Double rate, HttpSession session) {
        Long userId = authService.getUserIdFromSession(session);
        productService.rateProduct(id, userId, rate);
    }

    @Override
    public List<Product> getProductsByIds(List<Long> ids) {
        return productService.getProductsByIds(ids);
    }
}
