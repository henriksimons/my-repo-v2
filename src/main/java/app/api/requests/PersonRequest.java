package app.api.requests;

public class PersonRequest {

    private final String name;
    private final String ssn;
    private final int age;

    public PersonRequest(int age, String name, String ssn) {
        this.age = age;
        this.name = name;
        this.ssn = ssn;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getSsn() {
        return ssn;
    }
}
