package ru.alex0d.javaspring.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "teams")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teams_seq")
    @SequenceGenerator(name = "teams_seq", sequenceName = "teams_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @JsonIgnore
    @OneToMany(mappedBy = "team")
    private List<Footballer> footballers;

    public Team(long id, String name, LocalDate creationDate) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        List<Long> footballerIds = null;
        if (footballers != null) {
            footballerIds = footballers.stream().map(Footballer::getId).toList();
        }
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", footballerIds=" + footballerIds +
                '}';
    }
}
