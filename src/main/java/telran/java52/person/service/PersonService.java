package telran.java52.person.service;

import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.dto.PersonDto;


public interface PersonService {
	
	Boolean addPerson(PersonDto personDto);
	PersonDto findPersonById(Integer id);
	Iterable <PersonDto> findPersonByCity(String city);
	Iterable <PersonDto> findPersonByAges(Integer minAge, Integer maxAge);
	PersonDto updateName(Integer id, String newName);
	Iterable <PersonDto> findPersonByName(String name);
	Iterable <CityPopulationDto> citiesPopulation();
	PersonDto updateAddress(Integer id, AddressDto newAddress);
	PersonDto deletePerson(Integer id);
	PersonDto[]findAllChildren();
	PersonDto[]findEmployeeBySalary(Integer from,Integer to);
	
}
