package my.app;

public class PersonModel {

    private String address; //TODO Address object containing multiple addresses
    private String city;
    private String firstName;
    private String lastName;
    private String phoneNumber; //TODO PhoneNumber object containing multiple addresses
    private int postalCode;
    private String ssn;
    private int yearsOfAge; //TODO Automatic calculation

    public PersonModel(Builder builder) {
        this.address = builder.address;
        this.city = builder.city;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.phoneNumber = builder.phoneNumber;
        this.postalCode = builder.postalCode;
        this.ssn = builder.ssn;
        this.yearsOfAge = builder.yearsOfAge;

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
        private int postalCode;
        private String ssn;
        private int yearsOfAge;

        public PersonModel build() {
            return new PersonModel(this);
        }

        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
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

        public Builder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder withPostalCode(int postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder withSsn(String ssn) {
            this.ssn = ssn;
            return this;
        }

        public Builder withYearsOfAge(int yearsOfAge) {
            this.yearsOfAge = yearsOfAge;
            return this;
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

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public int getYearsOfAge() {
        return yearsOfAge;
    }

    public void setYearsOfAge(int yearsOfAge) {
        this.yearsOfAge = yearsOfAge;
    }
}
