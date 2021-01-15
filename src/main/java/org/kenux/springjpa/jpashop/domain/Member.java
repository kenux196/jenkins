package org.kenux.springjpa.jpashop.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @ToString(exclude = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    @Embedded // ?
    private Address address;

    @OneToMany(mappedBy = "member")
    private final List<Order> orders = new ArrayList<>();
}
