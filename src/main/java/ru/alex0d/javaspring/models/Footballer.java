package ru.alex0d.javaspring.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "footballers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Footballer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "footballers_seq")
    @SequenceGenerator(name = "footballers_seq", sequenceName = "footballers_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
