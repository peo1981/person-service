package telran.java52.person.service;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.hibernate.type.TrueFalseConverter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java52.person.dao.PersonRepository;
import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.ChildDto;
import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.dto.EmployeeDto;
import telran.java52.person.dto.PersonDto;
import telran.java52.person.dto.exceptions.PersonNotFoundException;
import telran.java52.person.model.Address;
import telran.java52.person.model.Child;
import telran.java52.person.model.Employee;
import telran.java52.person.model.Person;


@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {
	
	final PersonRepository personRepository;
	final ModelMapper modelMapper;
	final PersonModelDtoMapper mapper;

	@Transactional
	@Override
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(mapper.mapToModel(personDto));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person =personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		
		return mapper.mapToDto(person);
	}
	@Transactional (readOnly =true)
	@Override
	public Iterable <PersonDto> findPersonByCity(String city) {
		Stream <Person> persons =personRepository.findByAddress_City(city);
		return persons.map(p->mapper.mapToDto(p)).toList();
	}
	@Transactional (readOnly =true)
	@Override
	public Iterable<PersonDto> findPersonByAges(Integer minAge, Integer maxAge) {
	    LocalDate from = LocalDate.now().minusYears(maxAge);
	    LocalDate to = LocalDate.now().minusYears(minAge);
		Stream <Person> persons =personRepository.findAllPersonsByBirthDateBetween(from, to);
		return persons.map(p->mapper.mapToDto(p)).toList();
		
	}
    @Transactional
	@Override
	public PersonDto updateName(Integer id, String newName) {
		Person person =personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setName(newName);
		personRepository.save(person);
		return mapper.mapToDto(person);
	}
	@Transactional (readOnly =true)
	@Override
	public Iterable<PersonDto> findPersonByName(String name) {
		Stream <Person> persons =personRepository.findByName(name);
		return persons.map(p->mapper.mapToDto(p)).toList();
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
		return mapper.mapToDto(person);
	}
    @Transactional 
	@Override
	public PersonDto deletePerson(Integer id) {
		Person person =personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		 personRepository.deleteById(id);
		 return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public void run(String... args) throws Exception {
		if(personRepository.count()==0) {
			Person person =new Person(1000, "john", LocalDate.of(1955, 12, 10), new Address("Raanana", "YakovOrland", 3));
			Child child =new Child(2000, "Moshe", LocalDate.of(2015, 12, 10), new Address("Raanana", "YakovOrland", 3), "ShalomYeled");
		    Employee employee = new Employee(3000, "Sarah",LocalDate.of(2015, 12, 10) ,  new Address("Raanana", "YakovOrland", 3), "IBM", 25000);
		    personRepository.save(person);
		    personRepository.save(child);
		    personRepository.save(employee);
		    
		}
		
	}
	@Transactional (readOnly =true)
	@Override
	public PersonDto[] findAllChildren() {
		Stream <Person> persons =personRepository.findAllChildren();
		return persons.map(p->mapper.mapToDto(p)).toArray(PersonDto[]::new);
	}
	@Transactional (readOnly =true)
	@Override
	public PersonDto[] findEmployeeBySalary(Integer from, Integer to) {
		Stream <Person> persons =personRepository.findBySalaryBetween(from, to);
		return persons.map(p->mapper.mapToDto(p)).toArray(PersonDto[]::new);
	}

}
