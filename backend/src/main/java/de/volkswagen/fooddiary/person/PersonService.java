package de.volkswagen.fooddiary.person;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    public List<PersonDTO> findAll() {
        return this.personRepository.findAll()
                .stream()
                .map(person -> this.mapToDto(person, new PersonDTO()))
                .collect(Collectors.toList());
    }

    public PersonDTO get(final Long id) {
        return this.personRepository.findById(id)
                .map(person -> this.mapToDto(person, new PersonDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final PersonDTO personDTO) {
        final Person person = this.mapToEntity(personDTO, new Person());
        return this.personRepository.save(person).getId();
    }

    public void update(final Long id, final PersonDTO personDTO) {
        final Person person = this.personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.mapToEntity(personDTO, person);
        this.personRepository.save(person);
    }

    public void delete(final Long id) {
        this.personRepository.deleteById(id);
    }

    private PersonDTO mapToDto(final Person person, final PersonDTO personDTO) {
        this.modelMapper.map(person, personDTO);
        return personDTO;
    }

    private Person mapToEntity(final PersonDTO personDTO, final Person person) {
        this.modelMapper.map(personDTO, person);
        return person;
    }

}
