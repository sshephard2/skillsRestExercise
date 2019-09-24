package skills.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "skill")
public class Skill implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;
	
	@OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<SkillLevel> skillLevels;
}
