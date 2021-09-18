package my.app;

import my.app.api.PersonRequest;
import my.app.mongo.Person;
import my.app.mongo.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.*;

@Service
public class PersonService {

    private final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private final PersonRepository personRepository;

    @Autowired
    private final PersonServiceCache cache;

    public PersonService(PersonRepository personRepository, PersonServiceCache cache) {
        this.personRepository = personRepository;
        this.cache = cache;
    }

    public PersonServiceResponse createPerson(PersonRequest request) {
        PersonServiceResponse response = new PersonServiceResponse();
        if (nonNull(request)) {
            if (!alreadyExists(request.getSsn())) {
                Person mongoResponse = personRepository.save(createNewPersonRequest(request));
                LOGGER.info("New person with ssn {} created", request.getSsn());
                response.setObject(mongoResponse);
                return response;
            }
            LOGGER.info("Person with ssn {} already exists!", request.getSsn());
            response.setMessage("Person with ssn " + request.getSsn() + " already exists!");
            return response;
        }
        response.setMessage("Could not create new person, missing request data.");
        return response;
    }

    private Person createNewPersonRequest(PersonRequest request) {
        return Person.builder()
                .withAddress(request.getAddress())
                .withCity(request.getCity())
                .withFirstName(request.getFirstName())
                .withLastName(request.getLastName())
                .withPhoneNumber(request.getPhoneNumber())
                .withPostalCode(request.getPostalCode())
                .withSsn(request.getSsn())
                .build();
    }


    private boolean alreadyExists(String ssn) {
        return getPersonById(ssn).isPresent();
    }

    public Optional<Person> getPersonById(String ssn) {
        LOGGER.info("Searching for person with ssn (id): {}", ssn);
        Person person = isCached(ssn);
        if (isNull(person)) {
            Optional<Person> optionalPerson = personRepository.findById(ssn);
            if (optionalPerson.isPresent()) {
                LOGGER.info("Fetched person from database {}", optionalPerson.get().getSsn());
                cache.cache(ssn, optionalPerson.get());
                LOGGER.info("Caching person with ssn {}", ssn);
                LOGGER.info("Returning person from database with ssn {}", ssn);
                return optionalPerson;
            }
            LOGGER.info("Could not find person with ssn {}", ssn);
            return Optional.empty();
        }
        LOGGER.info("Returning person from cache with ssn {}", ssn);
        return Optional.of(person);
    }

    private Person isCached(String ssn) {
        return cache.exists(ssn);
    }

    public PersonServiceResponse updatePerson(PersonRequest request) {
        PersonServiceResponse response = new PersonServiceResponse();
        if (nonNull(request)) {
            Person suggestedPerson = Person.builder()
                    .withAddress(request.getAddress())
                    .withCity(request.getCity())
                    .withFirstName(request.getFirstName())
                    .withLastName(request.getLastName())
                    .withPhoneNumber(request.getPhoneNumber())
                    .withPostalCode(request.getPostalCode())
                    .withSsn(request.getSsn())
                    .build();
            Optional<Person> existing = getPersonById(suggestedPerson.getSsn());

            if (existing.isPresent()) {
                Person updatedPerson = compareAndSetFinalRequest(suggestedPerson, existing.get());
                LOGGER.info("Updating person {}...", existing.get().getSsn());
                response.setObject(personRepository.save(updatedPerson));
                if (nonNull(response.getObject())) {
                    LOGGER.info("Successfully updated person {}", existing.get().getSsn());
                }
                response.setMessage("Updated person with ssn " + request.getSsn() + ".");
                return response;
            }

            response.setMessage("Can not update person with ssn " + request.getSsn() + " because it does not exist.");
            return response;
        }
        response.setMessage("Request is missing. Can not update person with ssn");
        return response;
    }

    private Person compareAndSetFinalRequest(Person suggested, Person existing) {

        if (nonNull(suggested.getAddress())) {
            LOGGER.info("Changing address from {} to {}", existing.getAddress(), suggested.getAddress());
            existing.setAddress(suggested.getAddress());
        }
        if (nonNull(suggested.getCity())) {
            LOGGER.info("Changing city from {} to {}", existing.getCity(), suggested.getCity());
            existing.setCity(suggested.getCity());
        }
        if (nonNull(suggested.getFirstName())) {
            LOGGER.info("Changing first name from {} to {}", existing.getFirstName(), suggested.getFirstName());
            existing.setFirstName(suggested.getFirstName());
        }
        if (nonNull(suggested.getLastName())) {
            LOGGER.info("Changing last name from {} to {}", existing.getLastName(), suggested.getLastName());
            existing.setLastName(suggested.getLastName());
        }
        if (nonNull(suggested.getPostalCode())) {
            LOGGER.info("Changing postal code from {} to {}", existing.getPostalCode(), suggested.getPostalCode());
            existing.setPostalCode(suggested.getPostalCode());
        }
        if (nonNull(suggested.getPhoneNumber())) {
            LOGGER.info("Changing phone number from {} to {}", existing.getPhoneNumber(), suggested.getPhoneNumber());
            existing.setPhoneNumber(suggested.getPhoneNumber());
        }
        return existing;
    }

    public PersonServiceResponse delete(PersonRequest request) {
        PersonServiceResponse response = new PersonServiceResponse();
        if (nonNull(request)) {
            personRepository.deleteById(request.getSsn());
            LOGGER.info("Person with ssn {} deleted from database.", request.getSsn());

            cache.delete(request.getSsn());
            LOGGER.info("Person with ssn {} deleted from cache.", request.getSsn());

            response.setMessage("Person with ssn " + request.getSsn() + "deleted.");
            return response;
        }
        response.setMessage("Missing request, could not delete person.");
        return response;
    }

    public List<Person> getAllPersons() {
        List<Person> allPersons = personRepository.findAll();
        LOGGER.info("Fetching all {} person from database.", allPersons.size());
        return allPersons;
    }

}
