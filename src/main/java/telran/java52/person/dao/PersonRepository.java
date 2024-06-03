package telran.java52.person.dao;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.java52.person.model.Person;


public interface PersonRepository extends JpaRepository<Person, Integer> {

	//List<Person> findByCity(String city);

	Stream <Person>  findByAddress_City(String city);

	Stream<Person> findAllPersonsByBirthDateBetween(LocalDate from, LocalDate to);
	
	Stream <Person> findByName(String name);
	

}
