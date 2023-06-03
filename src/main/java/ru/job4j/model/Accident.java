package ru.job4j.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Table(name = "accidents")
public class Accident {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String text;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @ToString.Exclude
    @JoinColumn(name = "types_id")
    private AccidentType type;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @ToString.Exclude
    @JoinTable(
            name = "accidents_rules",
            joinColumns = { @JoinColumn(name = "accidents_id") },
            inverseJoinColumns = { @JoinColumn(name = "rules_id") }
    )
    private Set<Rule> rules = new HashSet<>();

    public void addRule(Rule rule) {
        rules.add(rule);
    }
}