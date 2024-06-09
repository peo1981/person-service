package telran.java52.person.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.dto.PersonDto;
import telran.java52.person.service.PersonService;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

	final PersonService personService; 

	@PostMapping
	public Boolean addPerson(@RequestBody  PersonDto personDto) {
		
		return personService.addPerson(personDto);
	}

	@GetMapping("/{id}")
	public PersonDto findPersonById(@PathVariable Integer id) {
		
		return personService.findPersonById(id);
				
	}

	@GetMapping("/city/{city}")
	public Iterable<PersonDto> findPersonByCity(@PathVariable String city) {
		
		return personService.findPersonByCity(city);
	}

	@GetMapping("/ages/{minAge}/{maxAge}")
	public Iterable<PersonDto> findPersonByAges(@PathVariable Integer minAge,@PathVariable  Integer maxAge) {
		
		return personService.findPersonByAges(minAge, maxAge);
	}

	@PutMapping("/{id}/name/{newName}")
	public PersonDto updateName(@PathVariable Integer id,@PathVariable("newName") String newName) {
		
		return personService.updateName(id, newName);
	}

	@GetMapping("/name/{name}")
	public Iterable<PersonDto> findPersonByName(@PathVariable String name) {
		
		return personService.findPersonByName(name);
	}

	@GetMapping("population/city")
	public Iterable<CityPopulationDto> citiesPopulation() {
		
		return personService.citiesPopulation();
	}

	@PutMapping("/{id}/address")
	public PersonDto updateAddress(@PathVariable Integer id,@RequestBody AddressDto newAddress) {
		
		return personService.updateAddress(id, newAddress);
	}

	@DeleteMapping("/{id}")
	public PersonDto deletePerson(@PathVariable Integer id) {
		
		return personService.deletePerson(id);
	}
	@GetMapping("/children")
	public PersonDto[] findAllChildren() {
		
		return personService.findAllChildren();
	};
	@GetMapping("/salary/{from}/{to}")
	public PersonDto[]findEmployeeBySalary(@PathVariable Integer from,@PathVariable Integer to){
		
		return personService.findEmployeeBySalary(from, to);
	};
	
}
