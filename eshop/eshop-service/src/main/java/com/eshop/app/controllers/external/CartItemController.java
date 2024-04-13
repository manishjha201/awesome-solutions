package com.eshop.app.controllers.external;

import com.eshop.app.common.entities.rdbms.Cart;
import com.eshop.app.common.entities.rdbms.CartProduct;
import com.eshop.app.services.CartItemService;
import com.eshop.app.services.external.ShoppingCartService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user/api/v1/cart")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartProduct>> getCartItems(@PathVariable Long userId) {
        return ResponseEntity.ok(cartItemService.getCartItemsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<CartProduct> addCartItem(@RequestBody CartProduct cartItem) {
        return ResponseEntity.ok(cartItemService.addCartItem(cartItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        cartItemService.removeCartItem(id);
        return ResponseEntity.ok().build();
    }



    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestParam(required = false) Long cartId, @RequestParam Long productId, @RequestParam int quantity) {
        try {
            //Cart cart = shoppingCartService.addToCart(cartId, productId, quantity);
           // return ResponseEntity.ok("Item added to cart. Cart ID: " + cart.getId());
            return null;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/pay")
    public ResponseEntity<?> processPayment(@RequestParam Long cartId) {
        try {
            shoppingCartService.processPayment(cartId);
            return ResponseEntity.ok("Payment processed and inventory updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
