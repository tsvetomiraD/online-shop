package uni.project.online.shop.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import uni.project.online.shop.model.Product;

import java.util.List;

@RequestMapping("/products")
public interface ProductsApi {
    @Operation(tags = {"Product"}, summary = "login")
    @GetMapping("/filter") ///makeup
    public List<Product> getFilteredProducts(@RequestParam String category, @RequestParam(required = false) String sort, @RequestParam(required = false) String type,
                                             @RequestParam(required = false) String brand, @RequestParam(required = false) Double price,
                                             @RequestParam(required = false) Double rate, @RequestParam(required = false) String targetGroup);

//    @Operation(tags = {"Product"}, summary = "login")
//    @GetMapping("/face-care")
//    public List<Product> getFaceCare(@RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "type", required = false) String type,
//                                     @RequestParam(value = "brand", required = false) String brand, @RequestParam(value = "price", required = false) Double price,
//                                     @RequestParam(value = "rate", required = false) Double rate, @RequestParam(value = "targetGroup", required = false) String targetGroup);
//
//    @Operation(tags = {"Product"}, summary = "login")
//    @GetMapping("/hair-care")
//    public List<Product> getHairCare(@RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "type", required = false) String type,
//                                     @RequestParam(value = "brand", required = false) String brand, @RequestParam(value = "price", required = false) Double price,
//                                     @RequestParam(value = "rate", required = false) Double rate, @RequestParam(value = "targetGroup", required = false) String targetGroup);
//
//    @Operation(tags = {"Product"}, summary = "login")
//    @GetMapping("/body-care")
//    public List<Product> getBodyCare(@RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "type", required = false) String type,
//                                     @RequestParam(value = "brand", required = false) String brand, @RequestParam(value = "price", required = false) Double price,
//                                     @RequestParam(value = "rate", required = false) Double rate, @RequestParam(value = "targetGroup", required = false) String targetGroup);
//
//    @Operation(tags = {"Product"}, summary = "login")
//    @GetMapping("/perfumes")
//    public List<Product> getPerfumes(@RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "type", required = false) String type,
//                                     @RequestParam(value = "brand", required = false) String brand, @RequestParam(value = "price", required = false) Double price,
//                                     @RequestParam(value = "rate", required = false) Double rate, @RequestParam(value = "targetGroup", required = false) String targetGroup);
//
//    @Operation(tags = {"Product"}, summary = "login")
//    @GetMapping("/accessories")
//    public List<Product> getAccessories(@RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "type", required = false) String type,
//                                        @RequestParam(value = "brand", required = false) String brand, @RequestParam(value = "price", required = false) Double price,
//                                        @RequestParam(value = "rate", required = false) Double rate, @RequestParam(value = "targetGroup", required = false) String targetGroup);

    @Operation(tags = {"Product"}, summary = "login")
    @GetMapping("/sale")
    public List<Product> getProductsOnSale();

    @Operation(tags = {"Product"}, summary = "login")
    @GetMapping("/home")
    public List<Product> getHomeScreenProducts(@RequestParam String category);

//    @Operation(tags = {"Product"}, summary = "login")
//    @GetMapping("/rate")
//    public List<Product> getHighRateProducts();
//
//    @Operation(tags = {"Product"}, summary = "login")
//    @GetMapping("/orders")
//    public List<Product> getMostOrderedProducts();

    @Operation(tags = {"Product"}, summary = "login")
    @GetMapping("/brand/{brand}")
    public List<Product> getProductsByBrand(@PathVariable("brand") String brand);

    @Operation(tags = {"Product"}, summary = "login")
    @GetMapping()
    public List<Product> searchProducts(@RequestParam("search") String search);

    @Operation(tags = {"Product"}, summary = "login")
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long id);

    @Operation(tags = {"Product"}, summary = "login")
    @GetMapping("/{id}/rate")
    public void rateProduct(@PathVariable("id") Long id, @RequestParam Double rate, HttpSession session);

    @Operation(tags = {"Product"}, summary = "login")
    @PostMapping("/by-ids")
    public List<Product> getProductsByIds(@RequestBody List<Long> ids);
}
