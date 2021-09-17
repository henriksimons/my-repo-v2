package my.app;

public class PersonRequest {

    //TODO fit to personModel

    private final String ssn;
    private final String firstName;
    private final String lastName;
    private final int yearsOfAge;

    public PersonRequest(String ssn, String firstName, String lastName, int yearsOfAge) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearsOfAge = yearsOfAge;
    }

    public String getSsn() {
        return ssn;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getYearsOfAge() {
        return yearsOfAge;
    }
}
