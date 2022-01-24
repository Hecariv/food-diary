package de.volkswagen.fooddiary.person;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<PersonDTO> personDTOList = this.personService.findAll();
        return personDTOList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(personDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable final Long id) {
        return ResponseEntity.ok(this.personService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createPerson(@RequestBody @Valid final PersonDTO personDTO) {
        return new ResponseEntity<>(this.personService.create(personDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePerson(@PathVariable final Long id, @RequestBody @Valid final PersonDTO personDTO) {
        this.personService.update(id, personDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable final Long id) {
        this.personService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
