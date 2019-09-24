package skills.endpoints;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.junit.Assert;
import skills.SkillsApplication;
import skills.model.Level;
import skills.model.Person;
import skills.model.Skill;
import skills.model.SkillLevel;
import skills.repository.PersonRepository;
import skills.repository.SkillLevelRepository;
import skills.repository.SkillRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SkillsApplication.class)
@AutoConfigureMockMvc
public class SkillLevelEndpointTest
{	
	@Autowired
	MockMvc mockMvc;
	@Autowired
	PersonRepository personRepository;
	@Autowired
	SkillRepository skillRepository;
	@Autowired
	SkillLevelRepository skillLevelRepository;
	
	private static final String PEOPLE_ENDPOINT = "/people/";
	private static final String SKILL_ENDPOINT = "/skills/";
	private static final String SKILL_LEVELS_ENDPOINT = "/skillLevels/";
	
	private Person personTestData()
	{
		personRepository.deleteAll();
		Person person = new Person();
		person.setFirstName("Stephen");
		person.setLastName("Shephard");
		return personRepository.save(person);
	}
	
	private Skill skillTestData()
	{
		skillRepository.deleteAll();
		Skill skill = new Skill();
		skill.setName("Java");
		skill.setDescription("Programming");
		return skillRepository.save(skill);
	}
	
	@Test
	public void testPostSkillLevelEndpoint() throws Exception
	{
		Person savedPerson = personTestData();
		Skill savedSkill = skillTestData();
		
		mockMvc.perform(MockMvcRequestBuilders.post(SKILL_LEVELS_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"person\":\"" + PEOPLE_ENDPOINT + savedPerson.getId()
						+ "\",\"skill\":\"" + SKILL_ENDPOINT + savedSkill.getId()
						+ "\",\"level\":\"WORKING\"}"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		List<SkillLevel> savedSkillLevels = (List<SkillLevel>)skillLevelRepository.findAll();
		Assert.assertEquals(1, savedSkillLevels.size());
		Assert.assertEquals("Stephen", savedSkillLevels.get(0).getPerson().getFirstName());
		Assert.assertEquals("Shephard", savedSkillLevels.get(0).getPerson().getLastName());
		Assert.assertEquals("Java", savedSkillLevels.get(0).getSkill().getName());
		Assert.assertEquals("Programming", savedSkillLevels.get(0).getSkill().getDescription());
		Assert.assertEquals(Level.WORKING, savedSkillLevels.get(0).getLevel());
	}
}
