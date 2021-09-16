package my.app;

public class ResponseModel {

    private String message;
    private PersonModel person;

    private ResponseModel(Builder builder) {
        this.message = builder.message;
        this.person = builder.person;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String message;
        private PersonModel person;

        public Builder() {
        }

        public ResponseModel build() {
            return new ResponseModel(this);
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withPerson(PersonModel person) {
            this.person = person;
            return this;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PersonModel getPerson() {
        return person;
    }

    public void setPerson(PersonModel person) {
        this.person = person;
    }
}
