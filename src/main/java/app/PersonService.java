package app;

import app.api.requests.PersonRequest;
import app.mongo.PersonRepository;
import app.mongo.classes.Person;
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
            Person response = personRepository.save(new Person(request.getAge(), request.getName(), request.getSsn()));
            LOGGER.info("Created new Person with ssn: {}, name: {}, age: {}.}", response.getSsn(), response.getName(), response.getAge());
            return response;
        }
        return null;
    }

    public Optional<Person> getPersonById(String ssn) {
        LOGGER.info("Searching for person with ssn (id): {}", ssn);
        Optional<Person> person = personRepository.findById(ssn);
        person.ifPresent(result -> LOGGER.info("Fetched person {}", result.getName()));
        return person;
    }

    public Person setAge(PersonRequest request) {
        if (nonNull(request)) {
            int age = request.getAge();
            String ssn = request.getSsn();
            Optional<Person> personResult = getPersonById(ssn);
            if (personResult.isPresent()) {
                Person person = personResult.get();
                int previousAge = person.getAge();
                person.setAge(age);
                LOGGER.info("Updating age for person {} with ssn {} from {} to {}", person.getName(), person.getSsn(), previousAge, person.getAge());
                return personRepository.save(person);
            }
        }
        return null;
    }
}
