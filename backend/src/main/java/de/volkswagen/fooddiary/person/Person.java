package de.volkswagen.fooddiary.person;

import de.volkswagen.fooddiary.entry.Entry;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "persons")
@NoArgsConstructor @Getter @Setter
public class Person implements Serializable {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private Double height;

    @Column
    private Double weight;

    @Column
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "person")
    private Set<Entry> personEntries;

}