package org.kenux.springjpa.jpashop.service;

import org.kenux.springjpa.jpashop.domain.*;
import org.kenux.springjpa.jpashop.repository.MemberRepository;
import org.kenux.springjpa.jpashop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemService itemService;

    // 주문
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findById(memberId).orElseGet(Member::new);
        Item item = itemService.findOne(itemId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    // 주문 취소
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseGet(Order::new);
        order.cancel();
    }

    // 주문 검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch.toSpecification()); // Specification 사용
    }

    public List<Order> searchOrders(OrderSearch orderSearch) {
        return orderRepository.search(orderSearch);  // Use QueryDSL
    }
}
