package org.kenux.springjpa.jpashop.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.kenux.springjpa.jpashop.domain.Order;
import org.kenux.springjpa.jpashop.domain.OrderSearch;
import org.kenux.springjpa.jpashop.domain.OrderStatus;
import org.kenux.springjpa.jpashop.service.ItemService;
import org.kenux.springjpa.jpashop.service.MemberService;
import org.kenux.springjpa.jpashop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;

    @PostMapping("/")
    public ResponseEntity<?> order(@RequestParam("memberId") Long memberId,
                                       @RequestParam("itemId") Long itemId,
                                       @RequestParam("count") int count) {
        orderService.order(memberId, itemId, count);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
