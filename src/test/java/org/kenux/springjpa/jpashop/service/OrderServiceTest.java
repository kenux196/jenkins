package org.kenux.springjpa.jpashop.service;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kenux.springjpa.jpashop.domain.*;
import org.kenux.springjpa.jpashop.exceptions.NotEnoughStockException;
import org.kenux.springjpa.jpashop.repository.OrderRepository;
import org.kenux.springjpa.jpashop.rest.model.OrderSummary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    private Member createMember(String name) {
        Member member = Member.builder()
                .name(name)
                .address(new Address("대구", "대실역남로35", "42123"))
                .build();
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

    @Test
    public void 상품주문() throws Exception {
        // Given
        Member member = createMember("kenux");
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;

        // When
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // Then
        Optional<Order> getOrder = orderRepository.findById(orderId);

        if (getOrder.isPresent()) {
            Order order = getOrder.get();
            assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, order.getStatus());
            assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, order.getOrderItems().size());
            assertEquals("주문 가격은 가격 * 수량이다.", 10000 * 2, order.getTotalPrice());
            assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, item.getStockQuantity());
        } else {
            fail("데이터 못찾음");
        }
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        // Given
        Member member = createMember("kenux");
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 11; // 재고 수량보다 많이 주문.

        // When
        orderService.order(member.getId(), item.getId(), orderCount);

        // Then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void 주문취소() {
        // Given
        Member member = createMember("kenux");
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // When
        orderService.cancelOrder(orderId);

        // Then
        Order order = orderRepository.getOne(orderId);

        assertEquals("주문 취소시 상태듣 CANCEL이다", OrderStatus.CANCEL, order.getStatus());
        assertEquals("주문이 취소된 상품은 그만틈 재고가 증가해야 한다.", 10, item.getStockQuantity());
    }

    @Test
    public void 주문검색_Use_Specification() {

        // Given
        Member member = createMember("kenux");
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;

        log.info("주문 넣기");
        orderService.order(member.getId(), item.getId(), orderCount);

        // when
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName("");
        orderSearch.setOrderStatus(OrderStatus.ORDER);

        log.info("주문 검색");
        List<Order> orders = orderService.findOrders(orderSearch);

        log.info("검색 결과");
        orders.forEach(order -> log.info("" + order));
    }

    @Test
    public void 주문검색_Use_QueryDsl() {

        // Given
        Member member = createMember("kenux");
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;

        log.info("주문 넣기");
        orderService.order(member.getId(), item.getId(), orderCount);

        // when
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName("");
        orderSearch.setOrderStatus(OrderStatus.ORDER);

        log.info("주문 검색");
        List<Order> orders = orderService.searchOrders(orderSearch);

        log.info("검색 결과");
        orders.forEach(order -> log.info("" + order));

        log.info("DTO 변환");
        ModelMapper modelMapper = new ModelMapper();
        OrderSummary summary = modelMapper.map(orders.get(0), OrderSummary.class);
        log.info("" + summary);

        log.info("Member 엔티티의 영속 상태 = " + em.contains(member)); // 엔티티가 영속 상태인지 체크...
    }
}
