package my.app;

public class PersonModel {

    private int age;
    private String name;

    public PersonModel(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private int age;
        private String id;
        private String name;

        public PersonModel build() {
            return new PersonModel(this);
        }

        public Builder withAge(int age) {
            this.age = age;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
