package my.app;

public class PersonModelMapper {

    public static PersonModel map(Person person) {
        return PersonModel.builder()
                .withAge(person.getYearsOfAge())
                .withName(person.getFirstName())
                .build();
    }
}
