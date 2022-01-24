package de.volkswagen.fooddiary.entry;

import de.volkswagen.fooddiary.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

    @Query
    List<Entry> findEntriesByPerson(Person person);

    @Query
    List<Entry> findEntriesByPersonAndEntryDate(Person person, LocalDate entryDate);

}
