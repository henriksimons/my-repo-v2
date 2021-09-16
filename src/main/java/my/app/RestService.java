package my.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public ResponseEntity<String> createPerson(@RequestBody PersonRequest request) {
        try {
            if (nonNull(request)) {
                LOGGER.info("Request received, attempting to create Person...");
                if (nonNull(request.getSsn()) && nonNull(request.getFirstName()) && nonNull(request.getLastName())) {
                    Person response = personService.createPerson(request);
                    if (nonNull(response)) {
                        return ResponseEntity.status(HttpStatus.OK).body("Successfully created person with ssn: " + response.getSsn());
                    } else
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not create person.");
                }
                LOGGER.warn("Required attributes ssn, first name and last name are missing. Can't create new person.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required body attributes {ssn}, {firstName} and {lastName} are missing. Can't create new person.");
            }
            LOGGER.warn("Missing request body. Can't create new person.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing request body. Can't create new person.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/person", method = RequestMethod.PUT)
    public ResponseEntity<String> updateAge(@RequestBody PersonRequest request) {
        try {
            if (nonNull(request)) {
                LOGGER.info("Request received, attempting to create Person.");
                Person response = personService.setAge(request);
                if (nonNull(response)) {
                    return ResponseEntity.status(HttpStatus.OK).body("Successfully created person: " + response);
                } else
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not create person.");
            }
            return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
