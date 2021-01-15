package org.kenux.springjpa.jpashop.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
public class Academy {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="academy_id")
//    private List<Subject> subjects = new ArrayList<>(); // 중복
    private Set<Subject> subjects = new LinkedHashSet<>(); // Set은 순서 보장 안되므로, 순서 보장이 되게 LinkedHashSet 적용

    @Builder
    public Academy(String name, Set<Subject> subjects) {
        this.name = name;
        if(subjects != null){
            this.subjects = subjects;
        }
    }

    public void addSubject(Subject subject){
        this.subjects.add(subject);
        subject.updateAcademy(this);
    }
}
