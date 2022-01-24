package de.volkswagen.fooddiary.entry;

import de.volkswagen.fooddiary.person.Person;
import de.volkswagen.fooddiary.person.PersonRepository;
import de.volkswagen.fooddiary.product.Product;
import de.volkswagen.fooddiary.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;
    private final PersonRepository personRepository;
    private final ProductRepository productRepository;

    public List<EntryDTO> findAll() {
        return this.entryRepository.findAll()
                .stream()
                .map(entry -> this.mapToDto(entry, new EntryDTO()))
                .collect(Collectors.toList());
    }

    public List<EntryDTO> findByPerson(Long personId) {
        final Person person = this.personRepository.findById(personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));
        return this.entryRepository.findEntriesByPerson(person)
                .stream()
                .map(entry -> this.mapToDto(entry, new EntryDTO()))
                .collect(Collectors.toList());
    }

    public List<EntryDTO> findByPersonAndEntryDate(Long personId, LocalDate entryDate) {
        final Person person = this.personRepository.findById(personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));
        return this.entryRepository.findEntriesByPersonAndEntryDate(person, entryDate)
                .stream()
                .map(entry -> this.mapToDto(entry, new EntryDTO()))
                .collect(Collectors.toList());
    }

    public EntryDTO get(final Long id) {
        return this.entryRepository.findById(id)
                .map(entry -> this.mapToDto(entry, new EntryDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final EntryDTO entryDTO) {
        final Entry entry = this.mapToEntity(entryDTO, new Entry());
        return this.entryRepository.save(entry).getId();
    }

    public void update(final Long id, final EntryDTO entryDTO) {
        Entry entry = this.entryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.mapToEntity(entryDTO, entry);
        this.entryRepository.save(entry);
    }

    public void delete(final Long id) {
        this.entryRepository.deleteById(id);
    }

    private EntryDTO mapToDto(final Entry entry, final EntryDTO entryDTO) {
        entryDTO.setId(entry.getId());
        entryDTO.setEntryDate(entry.getEntryDate());
        entryDTO.setAmount(entry.getAmount());
        entryDTO.setPerson(entry.getPerson() == null ? null : entry.getPerson().getId());
        entryDTO.setProduct(entry.getProduct() == null ? null : entry.getProduct().getCode());
        return entryDTO;
    }

    private Entry mapToEntity(final EntryDTO entryDTO, final Entry entry) {
        entry.setEntryDate(entryDTO.getEntryDate());
        entry.setAmount(entryDTO.getAmount());
        if (entryDTO.getPerson() != null && (entry.getPerson() == null || !entry.getPerson().getId().equals(entryDTO.getPerson()))) {
            final Person person = this.personRepository.findById(entryDTO.getPerson())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));
            entry.setPerson(person);
        }
        if (entryDTO.getProduct() != null && (entry.getProduct() == null || !entry.getProduct().getCode().equals(entryDTO.getProduct()))) {
            final Product product = productRepository.findById(entryDTO.getProduct())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
            entry.setProduct(product);
        }
        return entry;
    }

}
