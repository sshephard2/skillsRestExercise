package skills.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import skills.model.SkillLevel;
import skills.model.SkillLevelId;
import skills.model.SkillProjection;

@RepositoryRestResource(excerptProjection = SkillProjection.class)
public interface SkillLevelRepository extends CrudRepository<SkillLevel, SkillLevelId>
{
}
