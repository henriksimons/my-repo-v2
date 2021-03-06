package my.app.api;

import my.app.*;
import my.app.mongo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@RestController
public class RestService {

    private Logger LOGGER = LoggerFactory.getLogger(RestService.class);

    @Autowired
    private final PersonService personService;

    public RestService(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public ResponseEntity<String> ping() {
        return ResponseEntity.status(HttpStatus.OK).body("Application is UP!");
    }

    @RequestMapping(value = "/person/{ssn}", method = RequestMethod.GET)
    public ResponseEntity<PersonModel> getPersonById(@PathVariable String ssn) {
        try {
            Optional<Person> response = personService.getPersonById(ssn);
            if (response.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(PersonModelMapper.map(response.get()));
            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public ResponseEntity getAllPersons() {
        try {
            List<Person> response = personService.getAllPersons();
            if (nonNull(response) && !response.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else
                return ResponseEntity.status(HttpStatus.OK).body("Database currently holds " + response.size() + " persons.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public ResponseEntity<String> createPerson(@RequestBody PersonRequest request) {
        try {
            if (nonNull(request)) {
                LOGGER.info("Request received, attempting to create Person...");
                if (nonNull(request.getSsn()) && nonNull(request.getFirstName()) && nonNull(request.getLastName())) {
                    PersonServiceResponse response = personService.createPerson(request);
                    if (nonNull(response.getObject())) {
                        return ResponseEntity.status(HttpStatus.OK).body("Successfully created person with ssn: " + request.getSsn());
                    } else
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not create person. " + response.getMessage());
                }
                LOGGER.warn("Required attributes ssn, first name and last name are missing. Can't create new person.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required body attributes {ssn}, {firstName} and {lastName} are missing. Can't create new person.");
            }
            LOGGER.warn("Missing request body. Can't create new person.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing request body. Can't create new person.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: " + e);
        }
    }

    @RequestMapping(value = "/person", method = RequestMethod.PUT)
    public ResponseEntity<String> updatePerson(@RequestBody PersonRequest request) {
        try {
            if (nonNull(request)) {
                LOGGER.info("Request received, attempting to update Person.");
                PersonServiceResponse response = personService.updatePerson(request);
                if (nonNull(response.getObject())) {
                    return ResponseEntity.status(HttpStatus.OK).body("Successfully updated person: " + request.getSsn());
                } else
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not update person. " + response.getMessage());
            }
            return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/person", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@RequestBody PersonRequest request) {
        if (nonNull(request)) {
            if (nonNull(request.getSsn())) {
                personService.delete(request);
                return ResponseEntity.status(HttpStatus.OK).body("Deleted person with ssn " + request.getSsn());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required body attribute {ssn} is missing. Can't delete person.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing request body. Can't create new person.");
    }


}
