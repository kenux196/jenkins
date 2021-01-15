package org.kenux.springjpa.jpashop.rest.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.kenux.springjpa.jpashop.domain.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class OrderSummary {
    private int orderId;
    Member member;
    OrderStatus orderStatus;
    List<OrderItem> orderItems;
    Delivery deliveryStatus;
}
