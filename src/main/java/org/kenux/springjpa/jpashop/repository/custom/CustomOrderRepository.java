package org.kenux.springjpa.jpashop.repository.custom;

import org.kenux.springjpa.jpashop.domain.Member;
import org.kenux.springjpa.jpashop.domain.Order;
import org.kenux.springjpa.jpashop.domain.OrderSearch;

import java.util.List;

public interface CustomOrderRepository {

    public List<Order> search(OrderSearch orderSearch);

    public List<Member> search2(OrderSearch orderSearch);
}
