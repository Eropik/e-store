package com.customer.controller;

import com.library.dto.CustomerDto;
import com.library.dto.ProductDto;
import com.library.model.*;
import com.library.service.*;
import com.library.utils.ImageUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequiredArgsConstructor
public class OrderController {

    private final CustomerService customerService;
    private final OrderService orderService;

    private final ShoppingCartService cartService;

    private final CartItemService cartItemService;

    private final CountryService countryService;

    private final CityService cityService;
    private final OrderDetailService orderDetailService;

    @GetMapping("/check-out")
    public String checkOut(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        } else {


            CustomerDto customer = customerService.getCustomer(principal.getName());
            if (customer.getAddress() == null || customer.getCity() == null || customer.getPhoneNumber() == null) {
                model.addAttribute("information", "You need update your information before check out");
                List<Country> countryList = countryService.findAll();
                List<City> cities = cityService.findAll();
                model.addAttribute("customer", customer);
                model.addAttribute("cities", cities);
                model.addAttribute("countries", countryList);
                model.addAttribute("title", "Profile");
                model.addAttribute("page", "Profile");
                return "customer-information";
            } else {
                ShoppingCart cart = customerService.findByUsername(principal.getName()).getCart();


                List<CartItem> cartItems = cartService.getCartItems(cart);

                List<Product> products = cartItemService.getProducts( cartItems);


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
                List<ShoppingCartController.CartItemWithDto> cartItemsWithDto = new ArrayList<>();
                for (int i = 0; i < cartItems.size(); i++) {
                    ShoppingCartController.CartItemWithDto itemWithDto = new ShoppingCartController.CartItemWithDto();
                    itemWithDto.setCartItem(cartItems.get(i)); // CartItem
                    itemWithDto.setProductDto(productDtos.get(i)); // Corresponding ProductDto
                    cartItemsWithDto.add(itemWithDto);
                }

                // Передаем комбинированный список в модель
                model.addAttribute("cartItemsWithDto", cartItemsWithDto);



                model.addAttribute("customer", customer);
                model.addAttribute("title", "Check-Out");
                model.addAttribute("page", "Check-Out");
                model.addAttribute("shoppingCart", cart);
                model.addAttribute("grandTotal", cart.getTotalItems());
                return "checkout"; // todo check transactional bcs lob
            }
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

    @GetMapping("/orders")
    public String getOrders(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            Customer customer = customerService.findByUsername(principal.getName());
            List<Order> orderList = customer.getOrders();
            model.addAttribute("orders", orderList);
            model.addAttribute("title", "Order");
            model.addAttribute("page", "Order");
            return "order";
        }
    }

    @RequestMapping(value = "/cancel-order", method = {RequestMethod.PUT, RequestMethod.GET})
    public String cancelOrder(Long id, RedirectAttributes attributes) {
        orderService.cancelOrder(id);
        attributes.addFlashAttribute("success", "Cancel order successfully!");
        return "redirect:/order";
    }


    @RequestMapping(value = "/add-order", method = {RequestMethod.POST})
    public String createOrder(Principal principal,
                              Model model,
                              HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            Customer customer = customerService.findByUsername(principal.getName());


            ShoppingCart cart = customer.getCart();
            Order order = orderService.save(cart);

            List<ProductDto> productDtos=transferData(orderDetailService.getProducts(order));

            for (ProductDto productDto : productDtos) {
                productDto.setImageBase64(
                        ImageUtil.encodeToBase64(
                                ImageUtil.decompressImage(productDto.getImage())
                        )
                );
            }


           // List<List<OrderDetail>> orderDetails = order.getOrderDetailList();



            List<OrderController.OrderWithDto> orderWithDtos = new ArrayList<>();
            for(int i = 0; i< order.getOrderDetailList().size();i++ ){
                OrderController.OrderWithDto orderWithDto = new OrderController.OrderWithDto();
                orderWithDto.setProductDto(productDtos.get(i));
                orderWithDto.setOrderDetailList(order.getOrderDetailList());

                orderWithDtos.add(orderWithDto);
            }


                session.removeAttribute("totalItems");
            model.addAttribute("order", orderWithDtos);
            model.addAttribute("ord", order);
            model.addAttribute("title", "Order Detail");
            model.addAttribute("page", "Order Detail");
            model.addAttribute("success", "Add order successfully");
            return "order-detail";
        }
    }

   public static class OrderWithDto{
        private List<OrderDetail> orderDetailList;
        private ProductDto productDto;

       public OrderWithDto() {
       }

       public List<OrderDetail> getOrderDetailList() {
           return orderDetailList;
       }

       public void setOrderDetailList(List<OrderDetail> orderDetailList) {
           this.orderDetailList = orderDetailList;
       }

       public ProductDto getProductDto() {
           return productDto;
       }

       public void setProductDto(ProductDto productDto) {
           this.productDto = productDto;
       }
   }


}
