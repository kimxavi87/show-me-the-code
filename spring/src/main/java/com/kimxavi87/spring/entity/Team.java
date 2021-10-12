package com.kimxavi87.spring.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {
    @Id @GeneratedValue
    private Long id;
    private String name;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "team")
    private final List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }
}
