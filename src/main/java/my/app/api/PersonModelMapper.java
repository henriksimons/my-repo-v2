package my.app.api;

import my.app.api.PersonModel;
import my.app.mongo.Person;

public class PersonModelMapper {

    public static PersonModel map(Person person) {
        return PersonModel.builder()
                .withAddress(person.getAddress())
                .withCity(person.getCity())
                .withFirstName(person.getFirstName())
                .withLastName(person.getLastName())
                .withPhoneNumber(person.getPhoneNumber())
                .withPostalCode(person.getPostalCode())
                .withSsn(person.getSsn())
                .build();
    }
}
