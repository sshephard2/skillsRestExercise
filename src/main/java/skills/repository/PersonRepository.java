package skills.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import skills.model.Person;

@RepositoryRestResource(path="people", collectionResourceRel="people")
public interface PersonRepository extends CrudRepository<Person, Long>
{
}
