package my.app.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("persons")
public class Person {

    private String address;
    private String city;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer postalCode;
    @Id
    private String ssn;

    public Person(String ssn, String firstName, String lastName, String phoneNumber, String address, Integer postalCode, String city) {
        super();
        this.address = address;
        this.city = city;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
        this.ssn = ssn;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String address;
        private String city;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private Integer postalCode;
        private String ssn;

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

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder withPostalCode(Integer postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Person build() {
            return new Person(ssn, firstName, lastName, phoneNumber, address, postalCode, city);
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}