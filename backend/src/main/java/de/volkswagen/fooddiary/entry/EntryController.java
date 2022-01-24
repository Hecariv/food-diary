package de.volkswagen.fooddiary.entry;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/entries")
@RequiredArgsConstructor
public class EntryController {

    private final EntryService entryService;

    @GetMapping
    public ResponseEntity<List<EntryDTO>> getEntries(@RequestParam(required = false, name = "person_id") Long personId,
                                                     @RequestParam(required = false, name = "date")
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entryDate) {
        List<EntryDTO> entryDTOList;
        if (personId == null && entryDate == null) {
            entryDTOList = this.entryService.findAll();
        } else if (personId != null && entryDate == null) {
            entryDTOList = this.entryService.findByPerson(personId);
        } else {
            entryDTOList = this.entryService.findByPersonAndEntryDate(personId, entryDate);
        }
        return entryDTOList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(entryDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntryDTO> getEntry(@PathVariable final Long id) {
        return ResponseEntity.ok(this.entryService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createEntry(@RequestBody @Valid final EntryDTO entryDTO) {
        return new ResponseEntity<>(this.entryService.create(entryDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEntry(@PathVariable final Long id, @RequestBody @Valid final EntryDTO entryDTO) {
        this.entryService.update(id, entryDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable final Long id) {
        this.entryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
