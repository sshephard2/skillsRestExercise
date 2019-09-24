package skills.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import skills.model.Skill;

@RepositoryRestResource
public interface SkillRepository extends CrudRepository<Skill, Long>
{
}
