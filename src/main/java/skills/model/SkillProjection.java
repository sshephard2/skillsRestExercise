package skills.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="skillSummary", types=SkillLevel.class)
public interface SkillProjection
{
	@Value("#{target.person?.firstName}")
	String getFirstName();
	
	@Value("#{target.person?.lastName}")
	String getLastName();
	
	@Value("#{target.skill?.name}")
	String getSkillName();
	
	Level getLevel();
}
