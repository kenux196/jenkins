package org.kenux.springjpa.jpashop.domain;

import lombok.*;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter @ToString
@Builder
@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
