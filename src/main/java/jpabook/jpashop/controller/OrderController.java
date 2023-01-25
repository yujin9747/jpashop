package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.request.OrderCreationRequest;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderCreationRequest request){
        return ResponseEntity.ok(orderService.order(request.getMemberId(), request.getItemId(), request.getCount()));
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        orderService.cancelOrder(id);
        return ResponseEntity.ok().build();
    }

}
