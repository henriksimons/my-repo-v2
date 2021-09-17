package my.app;

public class PersonModelMapper {

    public static PersonModel map(Person person) {
        return PersonModel.builder()
                .withFirstName(person.getFirstName())
                .withLastName(person.getLastName())
                .withYearsOfAge(person.getYearsOfAge())
                .withSsn(person.getSsn())
                .build();
    }
}
