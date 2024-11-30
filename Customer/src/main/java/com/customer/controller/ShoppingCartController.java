package com.customer.controller;

import com.library.dto.CartItemDto;
import com.library.dto.ProductDto;
import com.library.model.CartItem;
import com.library.model.Customer;
import com.library.model.Product;
import com.library.model.ShoppingCart;
import com.library.service.CartItemService;
import com.library.service.CustomerService;
import com.library.service.ProductService;
import com.library.service.ShoppingCartService;
import com.library.utils.ImageUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ShoppingCartController {

    private final CustomerService customerService;
    private final ShoppingCartService cartService;
    private final ProductService productService;
    private final CartItemService cartItemService;


    @GetMapping("/cart1")
    public String cart1(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        Customer customer = customerService.findByUsername(principal.getName());
        ShoppingCart cart = customer.getCart();
        if (cart == null) {

            return "redirect:/menu";

        }

        model.addAttribute("grandTotal", cart.getTotalPrice());

        List<Product> prod = cart.getCartItems().stream().map(CartItem::getProduct).toList();
        List<ProductDto> productDtos = transferData(prod);
        for (ProductDto productDto : productDtos) {

            productDto.setImageBase64(
                    ImageUtil.encodeToBase64(
                            ImageUtil.decompressImage(productDto.getImage())
                    )
            );

        }

        model.addAttribute("productDtoList", productDtos);
        model.addAttribute("shoppingCart", cart);
        model.addAttribute("title", "Cart");
        session.setAttribute("totalItems", cart.getTotalItems());
        return "cart";
    }

    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }

        Customer customer = customerService.findByUsername(principal.getName());
        ShoppingCart cart = customer.getCart();
        if (cart == null) {
            return "redirect:/menu";//todo create page with massage about empty cart button to redirect to menu
        }

        model.addAttribute("grandTotal", cart.getTotalPrice());



        List<CartItem> cartItems = cartService.getCartItems(cart);

        List<Product> products =                cartItemService.getProducts( cartItems);


        List<ProductDto> productDtos = transferData(products);

        for (ProductDto productDto : productDtos) {
            productDto.setImageBase64(
                    ImageUtil.encodeToBase64(
                            ImageUtil.decompressImage(productDto.getImage())
                    )
            );
        }

        // Проверяем, что количество элементов в cartItems и productDtos совпадает
        if (cartItems.size() != productDtos.size()) {
            throw new IllegalStateException("e");
        }

        // Создаем комбинированный список CartItem и ProductDto
        List<CartItemWithDto> cartItemsWithDto = new ArrayList<>();
        for (int i = 0; i < cartItems.size(); i++) {
            CartItemWithDto itemWithDto = new CartItemWithDto();
            itemWithDto.setCartItem(cartItems.get(i)); // CartItem
            itemWithDto.setProductDto(productDtos.get(i)); // Corresponding ProductDto
            cartItemsWithDto.add(itemWithDto);
        }

        // Передаем комбинированный список в модель
        model.addAttribute("cartItemsWithDto", cartItemsWithDto);
        model.addAttribute("shoppingCart", cart);
        model.addAttribute("title", "Корзина");

        // Устанавливаем общее количество товаров в сессию
        session.setAttribute("totalItems", cart.getTotalItems());

        return "cart";
    }


    // DTO для связи CartItem и ProductDto

    public static class CartItemWithDto {

        private CartItem cartItem;
        private ProductDto productDto;

        public CartItem getCartItem() {
            return cartItem;
        }

        public void setCartItem(CartItem cartItem) {
            this.cartItem = cartItem;
        }

        public ProductDto getProductDto() {
            return productDto;
        }

        public void setProductDto(ProductDto productDto) {
            this.productDto = productDto;
        }
    }



    @PostMapping("/add-to-cart")
    public String addItemToCart(@RequestParam("id") Long id,
                                @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                                HttpServletRequest request,
                                Model model,
                                Principal principal,
                                HttpSession session) {
        ProductDto productDto = productService.getById(id);

        byte[] decompressedImage = ImageUtil.decompressImage(productDto.getImage());
        productDto.setImageBase64(ImageUtil.encodeToBase64(decompressedImage));

        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        ShoppingCart shoppingCart = cartService.addItemToCart(productDto, quantity, username);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        model.addAttribute("shoppingCart", shoppingCart);
        return "redirect:" + request.getHeader("Referer");
    }


    //for postman

    @PostMapping("/do-add-to-cart")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addToCart(
            @RequestBody Map<String, Object> requestBody,
            Principal principal,
            HttpSession session) {

        // Extract required parameters from the request body
        Long id = ((Number) requestBody.get("id")).longValue();
        int quantity = requestBody.containsKey("quantity") ?
                ((Number) requestBody.get("quantity")).intValue() : 1;

        if (principal == null) {
            // User not authenticated
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "You need to log in."));
        }

        // Fetch product
        ProductDto productDto = productService.getById(id);

        // Decompress and encode the product image
        byte[] decompressedImage = ImageUtil.decompressImage(productDto.getImage());
        productDto.setImageBase64(ImageUtil.encodeToBase64(decompressedImage));

        // Get username from Principal
        String username = principal.getName();

        // Add item to the shopping cart
        ShoppingCart shoppingCart = cartService.addItemToCart(productDto, quantity, username);

        // Update session
        session.setAttribute("totalItems", shoppingCart.getTotalItems());

        // Build response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Item added to cart successfully.");
        response.put("totalItems", shoppingCart.getTotalItems());
        response.put("shoppingCart", shoppingCart);

        return ResponseEntity.ok(response);
    }


    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("id") Long id,
                             @RequestParam("quantity") int quantity,
                             Model model,
                             Principal principal,
                             HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        ProductDto productDto = productService.getById(id);

        byte[] decompressedImage = ImageUtil.decompressImage(productDto.getImage());
        productDto.setImageBase64(ImageUtil.encodeToBase64(decompressedImage));

        String username = principal.getName();
        ShoppingCart shoppingCart = cartService.updateCart(productDto, quantity, username);
        model.addAttribute("shoppingCart", shoppingCart);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        return "redirect:/cart";

    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItem(@RequestParam("id") Long id,
                             Model model,
                             Principal principal,
                             HttpSession session
    ) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            ProductDto productDto = productService.getById(id);

            byte[] decompressedImage = ImageUtil.decompressImage(productDto.getImage());
            productDto.setImageBase64(ImageUtil.encodeToBase64(decompressedImage));

            String username = principal.getName();
            ShoppingCart shoppingCart = cartService.removeItemFromCart(productDto, username);
            model.addAttribute("shoppingCart", shoppingCart);
            session.setAttribute("totalItems", shoppingCart.getTotalItems());
            return "redirect:/cart";
        }
    }

    private List<ProductDto> transferData(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setDescription(product.getDescription());
            productDto.setImage(product.getImage());

            productDto.setCategory(product.getCategory());
            productDtos.add(productDto);
        }
        return productDtos;
    }
}
