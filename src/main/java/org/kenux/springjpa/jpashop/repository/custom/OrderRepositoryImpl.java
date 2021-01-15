package org.kenux.springjpa.jpashop.repository.custom;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.kenux.springjpa.jpashop.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

public class OrderRepositoryImpl extends QuerydslRepositorySupport implements CustomOrderRepository {

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> search(OrderSearch orderSearch) {
        QOrder order = QOrder.order;
        QMember member = QMember.member;
        QDelivery delivery = QDelivery.delivery;

//        JPQLQuery query = from(order);
        JPQLQuery<Order> query = from(order);

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query.leftJoin(order.member, member)
                    .where(member.name.contains(orderSearch.getMemberName()));
        }

        if (StringUtils.hasText(orderSearch.getDeliveryAddress())) {
            query.leftJoin(order.delivery, delivery)
                    .where(delivery.address.city.contains(orderSearch.getDeliveryAddress()));
        }

        if (orderSearch.getOrderStatus() != null) {
            query.where(order.status.eq(orderSearch.getOrderStatus()));
        }

        return query.fetch();
    }

    @Override
    public List<Member> search2(OrderSearch orderSearch) {
        QOrder order = QOrder.order;
        QMember member = QMember.member;

        JPQLQuery<Member> query = from(member);

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query.rightJoin(member.orders, order)
                    .where(member.name.contains(orderSearch.getMemberName()));
        }
        return query.fetch();
    }
}
