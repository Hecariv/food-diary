package de.volkswagen.fooddiary.person;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor @Getter @Setter
public class PersonDTO {

    private Long id;
    private String name;
    private Gender gender;
    private Double height;
    private Double weight;
    private LocalDate dateOfBirth;

}
