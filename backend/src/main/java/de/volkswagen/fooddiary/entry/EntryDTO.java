package de.volkswagen.fooddiary.entry;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor @Getter @Setter
public class EntryDTO {

    private Long id;
    private LocalDate entryDate;
    private Integer amount;

    @NotNull
    private Long person;

    @NotNull
    private String product;

}
