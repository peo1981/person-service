package telran.java52.person.service;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java52.person.dao.PersonRepository;
import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.dto.PersonDto;
import telran.java52.person.dto.exceptions.PersonNotFoundException;
import telran.java52.person.model.Address;
import telran.java52.person.model.Person;


@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
	
	final PersonRepository personRepository;
	final ModelMapper modelMapper;

	@Transactional
	@Override
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person =personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return modelMapper.map(person, PersonDto.class);
	}
	@Transactional (readOnly =true)
	@Override
	public Iterable <PersonDto> findPersonByCity(String city) {
		Stream <Person> persons =personRepository.findByAddress_City(city);
		return persons.map(p->modelMapper.map(p,PersonDto.class)).toList();
	}
	@Transactional (readOnly =true)
	@Override
	public Iterable<PersonDto> findPersonByAges(Integer minAge, Integer maxAge) {
	    LocalDate from = LocalDate.now().minusYears(maxAge);
	    LocalDate to = LocalDate.now().minusYears(minAge);
		Stream <Person> persons =personRepository.findAllPersonsByBirthDateBetween(from, to);
		return persons.map(p->modelMapper.map(p,PersonDto.class)).toList();
		
	}
    @Transactional
	@Override
	public PersonDto updateName(Integer id, String newName) {
		Person person =personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setName(newName);
		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}
	@Transactional (readOnly =true)
	@Override
	public Iterable<PersonDto> findPersonByName(String name) {
		Stream <Person> persons =personRepository.findByName(name);
		return persons.map(p->modelMapper.map(p,PersonDto.class)).toList();
	}

	@Override
	public Iterable<CityPopulationDto> citiesPopulation() {
		
		return personRepository.getCitiesPopulation();
	}
	@Transactional
	@Override
	public PersonDto updateAddress(Integer id, AddressDto newAddress) {
		Person person =personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setAddress(modelMapper.map(newAddress, Address.class));
		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}
    @Transactional 
	@Override
	public PersonDto deletePerson(Integer id) {
		Person person =personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		 personRepository.deleteById(id);
		 return modelMapper.map(person, PersonDto.class);
	}

}
