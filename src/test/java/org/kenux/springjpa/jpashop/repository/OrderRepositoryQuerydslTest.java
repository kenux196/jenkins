/**
 * Project: B.IOT Cloud
 * <p>
 * Copyright (c) 2017 DMC R&D Center, SAMSUNG ELECTRONICS Co.,LTD.
 * (Maetan dong)129, Samsung-ro Yeongtong-gu, Suwon-si. Gyeonggi-do 443-742, Korea
 * All right reserved.
 * <p>
 * This software is the confidential and proprietary information of Samsung Electronics, Inc.
 * ("Confidential Information"). You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you entered
 * into with Samsung Electronics.
 */
package org.kenux.springjpa.jpashop.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.kenux.springjpa.jpashop.domain.*;
import org.kenux.springjpa.jpashop.rest.model.OrderSummary;
import org.kenux.springjpa.jpashop.service.ItemService;
import org.kenux.springjpa.jpashop.service.MemberService;
import org.kenux.springjpa.jpashop.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

/**
 * <pre>
 * 서비스 명   : jpashop
 * 패키지 명   : org.kenux.springjpa.jpashop.repository
 * 클래스 명   : OrderRepositoryQuerydslTest
 * 설명       :
 *
 * ====================================================================================
 *
 * </pre>
 *
 * @author skyun
 * @version 1.0.0
 * @date 2021-01-12
 **/

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@DisplayName("QueryDSL Test")
public class OrderRepositoryQuerydslTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;

    private Member createMemberWithAddress(String name, Address address) {
        Member member = Member.builder().name(name).address(address).build();
        em.persist(member);
        return member;
    }

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
    public void insertBaseData() {
        IntStream.range(1, 101).forEach(i -> {
            Address address;
            switch (i % 4) {
                case 0:
                    address = Address.builder().city("대구").street("중앙로").zipcode("12345").build();
                    break;
                case 1:
                    address = Address.builder().city("대구").street("달구벌대로").zipcode("33322").build();
                    break;
                case 2:
                    address = Address.builder().city("부산").street("어느바닷길").zipcode("11122").build();
                    break;
                case 3:
                    address = Address.builder().city("서울").street("어느달동네").zipcode("98765").build();
                    break;
                default:
                    address = Address.builder().city("제주도").street("어느 해안도로").zipcode("76553").build();
                    break;
            }
            Member member = createMemberWithAddress("user" + i, address);
        });

        createBook("스프링 부트 갈아먹기", 13500, 10);
        createBook("JPA 부셔버리기", 27600, 10);
        createBook("테스트 주도 개발", 38500, 10);
        createBook("도메인 주도 개발", 32500, 10);
        createBook("깃허브 잘 쓰기", 32500, 10);
    }

    @Test
    public void 더미_주문_등록() {
        List<Member> members = memberRepository.findByName("user1");
        Item item1 = itemRepository.getOne(1L);
        Item item2 = itemRepository.getOne(3L);
//        Member member = memberService.findOne(members.get(0));
        members.forEach(member -> {
            orderService.order(member.getId(), item1.getId(), 3);
            orderService.order(member.getId(), item2.getId(), 4);
        });
    }

    @Test
    public void searchOrderByMemberNameTest() {
        // Given
        Member member = memberService.findOne(1L);
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName(member.getName());

        List<Order> orders = orderService.searchOrders(orderSearch);

        log.info(orderSearch.getMemberName() + "의 주문 내역 : " + orders);

        log.info(orders.get(0).getOrderItems().get(0).getItem().getName() + "");

        ModelMapper modelMapper = new ModelMapper();
        List<OrderSummary> summaries = new ArrayList<>();
        orders.forEach(order -> {
            OrderSummary summary = modelMapper.map(order, OrderSummary.class);
            summaries.add(summary);
        });
        log.info(summaries + "");
    }

    @Test
    public void querydslOrderTest1() {
        // Given
        Member member = createMember("kenux");
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;

        log.info("주문 넣기");
        orderService.order(member.getId(), item.getId(), orderCount);

        // when
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName(member.getName());
        orderSearch.setOrderStatus(OrderStatus.ORDER);
        orderSearch.setDeliveryAddress(member.getAddress().getCity());

        log.info("주문 검색");
        List<Order> orders = orderService.searchOrders(orderSearch);

        log.info("검색 결과");
        orders.forEach(order -> log.info("" + order));

        // Then
        Order order = orders.get(0);
        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, order.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, order.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * 2, order.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, item.getStockQuantity());
        assertEquals("상품 주문자가 일치하는가 ?", member.getName(), order.getMember().getName());
    }
//
//    @Test
//    public void
}
