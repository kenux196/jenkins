package org.kenux.springjpa.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;

@Setter @Getter
@DiscriminatorValue("M")
public class Movie extends Item{
    private String director;
    private String actor;
}
