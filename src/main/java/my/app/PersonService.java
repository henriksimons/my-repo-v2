package my.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
public class PersonService {

    private final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person createPerson(PersonRequest request) {
        if (nonNull(request)) {
            if (alreadyExists(request.getSsn())) {
                Person response = personRepository.save(createNewPersonRequest(request));
                LOGGER.info("Created new Person with ssn: {}, name: {} {}, age: {}.}", response.getSsn(), response.getFirstName(), response.getLastName(), response.getYearsOfAge());
                return response;
            }
            LOGGER.warn("Person with ssn: {} already exists.", request.getSsn());
        }
        return null;
    }

    private Person createNewPersonRequest(PersonRequest request){
        return Person.builder()
                .withSsn(request.getSsn())
                .withFirstName(request.getFirstName())
                .withLastName(request.getLastName())
                .withYearsOfAge(request.getYearsOfAge())
                .build();
    }


    private boolean alreadyExists(String ssn) {
        return !getPersonById(ssn).isPresent();
    }

    public Optional<Person> getPersonById(String ssn) {
        LOGGER.info("Searching for person with ssn (id): {}", ssn);
        Optional<Person> person = personRepository.findById(ssn);
        person.ifPresent(result -> LOGGER.info("Fetched person {}", result.getFirstName()));
        return person;
    }

    public Person setAge(PersonRequest request) {
        if (nonNull(request)) {
            int age = request.getYearsOfAge();
            String ssn = request.getSsn();
            Optional<Person> personResult = getPersonById(ssn);
            if (personResult.isPresent()) {
                Person person = personResult.get();
                int previousAge = person.getYearsOfAge();
                person.setYearsOfAge(age);
                LOGGER.info("Updating age for person {} with ssn {} from {} to {}", person.getFirstName(), person.getSsn(), previousAge, person.getYearsOfAge());
                return personRepository.save(person);
            }
        }
        return null;
    }
}
