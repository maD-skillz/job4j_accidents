package ru.job4j.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "authorities")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private int id;

    private String authority;

}
