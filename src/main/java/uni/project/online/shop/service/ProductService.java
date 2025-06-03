package uni.project.online.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.project.online.shop.model.AvgRating;
import uni.project.online.shop.model.Type;
import uni.project.online.shop.model.Product;
import uni.project.online.shop.model.TypesForCategory;
import uni.project.online.shop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    AuthService authService;

    public List<Product> getFilteredProducts(String category, String sort, String type, String brand, Double price, Double rate, String targetGroup) {
        Map<String, Object> filters = getFilters(sort, type, brand, price, rate, targetGroup, category);

        return productRepository.getProducts(filters);
    }

//    public List<Product> getFaceCare(String sort, String type, String brand, Double price, Double rate, String targetGroup) {
//        Map<String, Object> filters = getFilters(sort, type, brand, price, rate, targetGroup, "face");
//
//        return productRepository.getProducts(filters);
//    }
//
//    public List<Product> getPerfumes(String sort, String type, String brand, Double price, Double rate, String targetGroup) {
//        Map<String, Object> filters = getFilters(sort, type, brand, price, rate, targetGroup, "perfume");
//
//        return productRepository.getProducts(filters);
//    }
//
//    public List<Product> getHairCare(String sort, String type, String brand, Double price, Double rate, String targetGroup) {
//        Map<String, Object> filters = getFilters(sort, type, brand, price, rate, targetGroup, "hair");
//
//        return productRepository.getProducts(filters);
//    }
//
//    public List<Product> getAccessories(String sort, String type, String brand, Double price, Double rate, String targetGroup) {
//        Map<String, Object> filters = getFilters(sort, type, brand, price, rate, targetGroup, "accessories");
//
//        return productRepository.getProducts(filters);
//    }
//
//    public List<Product> getBodyCare(String sort, String type, String brand, Double price, Double rate, String targetGroup) {
//        Map<String, Object> filters = getFilters(sort, type, brand, price, rate, targetGroup, "body");
//
//        return productRepository.getProducts(filters);
//    }

    private Map<String, Object> getFilters(String sort, String type, String brand, Double price, Double rate, String targetGroup, String category) {
        Map<String, Object> filters = new HashMap<>();
        String by;
        String ascOrDesc;
        if (sort == null) {
            by = "orders";
            ascOrDesc = "DESC";
        } else {
            String[] sortBy = sort.split("-");
            by = sortBy[0];
            ascOrDesc = sortBy[1].toUpperCase();
        }

        filters.put("category", category);
        filters.put("by", by);
        filters.put("ascOrDesc", ascOrDesc);

        if (brand != null) {
            filters.put("brand", brand);
        }
        if (type != null) {
            filters.put("type", type);
        }
        if (price != null) {
            filters.put("price", price);
        }
        if (rate != null) {
            filters.put("rate", rate);
        }
        if (targetGroup != null) {
            filters.put("targetGroup", targetGroup);
        }
        return filters;
    }

    public List<Product> getHomeScreenProducts(String category) { // id, rate, orders
        return productRepository.getHomePageCategories(category); // can be date_created?
    }
    
    public List<Product> getProductsOnSale() {
        return productRepository.getProductsOnSale();
    }

//    public List<Product> getHighestRateProducts() {
//        return productRepository.getHomePageCategories("rate");
//    }
//
//    public List<Product> getMostOrderedProducts() {
//        return productRepository.getHomePageCategories("orders");
//    }

    public List<Product> getProductsByBrand(String brand) {
        return productRepository.getProductsByBrand(brand);
    }

    public List<Product> search(String search) {
        return productRepository.searchInProducts(search);
    }

    public Product getProduct(Long id) {
        return productRepository.getProduct(id);
    }

    public void rateProduct(Long id, Long userId, Double rating) {
        if (productRepository.getRating(id, userId) != null)
            productRepository.updateUserRating(id, userId, rating);
        else
            productRepository.rate(id, userId, rating);

        rate(id);
    }

    private void rate(Long id) {
        AvgRating rate = productRepository.getRate(id);
        double avgRating = rate.getRate() / rate.getCount();
        productRepository.setRate(id, avgRating);
    }

    public List<Product> getProductsByIds(List<Long> ids) {
        List<Product> res = new ArrayList<>();
        for (long id: ids) {
            res.add(productRepository.getProduct(id));
        }
        return res;
    }

    public List<Product> getAll() {
        return productRepository.getAll();
    }

    public List<TypesForCategory> getAllCategories() {
        List<Type> categories =  productRepository.getAllCategories();
        List<TypesForCategory> r = new ArrayList<>();
        for (Type type : categories) {
            List<Type> types = productRepository.getTypesForCategory(type.getId());
            TypesForCategory typesForCategory = new TypesForCategory(type, types);
            r.add(typesForCategory);
        }
        return r;
    }

    public TypesForCategory getCategoryByName(String name) {
        Type category = productRepository.getCategoryByName(name);
        return new TypesForCategory(category, productRepository.getTypesForCategory(category.getId()));
    }
}
