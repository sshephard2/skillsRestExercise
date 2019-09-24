package skills.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@IdClass(SkillLevelId.class)
@Table(name = "skill_level")
public class SkillLevel implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name = "person_id", referencedColumnName = "id", nullable=false, insertable=false, updatable=false)
	private Person person;

	@Id
	@ManyToOne
	@JoinColumn(name = "skill_id", referencedColumnName = "id", nullable=false, insertable=false, updatable=false)
	private Skill skill;

	@Enumerated
	private Level level;
}
