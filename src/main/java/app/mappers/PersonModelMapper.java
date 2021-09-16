package app.mappers;

import app.classes.PersonModel;
import app.mongo.classes.Person;

import java.util.Optional;

public class PersonModelMapper {

    public static PersonModel map(Person person) {
        return PersonModel.builder()
                .withAge(person.getAge())
                .withName(person.getName())
                .build();
    }
}
