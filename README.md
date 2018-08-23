##### Usage:
```Java
    public class Person {
        private String id;
        private String name;
        private Address address;
    }

    public class Address {
        private String id;
        private String country;
        private String city;
        private String addressLine;
    }

    Person person = new Person();
    person.address = new Address();
    person.name = "rick.zhao";
    person.address.country = "china";

    TemplateResolver resolver = new TemplateResolver(this.person);
    String result = resolver.resolve("{{name}}'s country is {{address.country}}");
    // The result is "rick.zhao's country is china"
```