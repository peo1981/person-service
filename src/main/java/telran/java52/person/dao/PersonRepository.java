package telran.java52.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.model.Person;


public interface PersonRepository extends JpaRepository<Person, Integer> {

	//List<Person> findByCity(String city);

	Stream <Person>  findByAddress_City(String city);
	
	Stream<Person> findAllPersonsByBirthDateBetween(LocalDate from, LocalDate to);
	
	Stream <Person> findByName(String name);
	@Query("select new telran.java52.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by count(p) desc")
	List<CityPopulationDto> getCitiesPopulation();
	@Query("SELECT e FROM Employee e WHERE e.salary BETWEEN ?1 AND ?2")
	Stream<Person> findBySalaryBetween(Integer from, Integer to);
	@Query("SELECT c FROM Child c ")
	Stream<Person> findAllChildren();
	

}
