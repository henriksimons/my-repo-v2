package my.app;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("persons")
public class Person {

    //TODO fit to personModel

    @Id
    private String ssn;

    private String firstName;
    private String lastName;
    private int yearsOfAge;

    public Person(String ssn, String firstName, String lastName, int yearsOfAge) {
        super();
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearsOfAge = yearsOfAge;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getYearsOfAge() {
        return yearsOfAge;
    }

    public void setYearsOfAge(int yearsOfAge) {
        this.yearsOfAge = yearsOfAge;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private String ssn;
        private String firstName;
        private String lastName;
        private int yearsOfAge;

        public Builder withSsn(String ssn) {
            this.ssn = ssn;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withYearsOfAge(int yearsOfAge) {
            this.yearsOfAge = yearsOfAge;
            return this;
        }

        public Person build() {
            return new Person(ssn, firstName, lastName, yearsOfAge);
        }
    }

}