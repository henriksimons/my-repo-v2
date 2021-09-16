package app.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import app.mongo.classes.Person;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String> {

    /*@Query("{name:'?0'}")
    Person findPersonByName(String name);*/

    @Query(value="{age:'?0'}", fields="{'name' : 1}")
    List<Person> findAll(String category);

    public long count();

}