package skills.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class SkillLevelId implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Long person;

	private Long skill;
}
