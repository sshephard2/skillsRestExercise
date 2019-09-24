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
import skills.model.Skill;
import skills.repository.SkillRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SkillsApplication.class)
@AutoConfigureMockMvc
public class SkillEndpointTest
{	
	@Autowired
	MockMvc mockMvc;
	@Autowired
	SkillRepository repository;
	
	private static final String SKILL_ENDPOINT = "/skills/";
	
	private Skill repoTestData()
	{
		repository.deleteAll();
		Skill skill = new Skill();
		skill.setName("Java");
		skill.setDescription("Programming");
		return repository.save(skill);
	}
	
	@Test
	public void testGetCollectionEndpoint() throws Exception
	{
		repoTestData();
		
		mockMvc.perform(MockMvcRequestBuilders.get(SKILL_ENDPOINT))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$..skills.length()").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$..skills[0].name").value("Java"))
				.andExpect(MockMvcResultMatchers.jsonPath("$..skills[0].description").value("Programming"));
	}
	
	@Test
	public void testGetItemEndpoint() throws Exception
	{
		Skill savedSkill = repoTestData();
		
		mockMvc.perform(MockMvcRequestBuilders.get(SKILL_ENDPOINT + savedSkill.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Java"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Programming"));
	}
	
	@Test
	public void testPostItemEndpoint() throws Exception
	{
		repository.deleteAll();
		
		mockMvc.perform(MockMvcRequestBuilders.post(SKILL_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Python\",\"description\":\"Scripting\"}"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		List<Skill> savedskills = (List<Skill>)repository.findAll();
		Assert.assertEquals(1, savedskills.size());
		Assert.assertEquals("Python", savedskills.get(0).getName());
		Assert.assertEquals("Scripting", savedskills.get(0).getDescription());
	}
	
	@Test
	public void testPutItemEndpoint() throws Exception
	{
		Skill savedSkill = repoTestData();
		
		mockMvc.perform(MockMvcRequestBuilders.put(SKILL_ENDPOINT + savedSkill.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Python 3\",\"description\":\"Coding\"}"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		List<Skill> savedskills = (List<Skill>)repository.findAll();
		Assert.assertEquals(1, savedskills.size());
		Assert.assertEquals("Python 3", savedskills.get(0).getName());
		Assert.assertEquals("Coding", savedskills.get(0).getDescription());
	}
	
	@Test
	public void testPatchItemEndpoint() throws Exception
	{
		Skill savedSkill = repoTestData();
		
		mockMvc.perform(MockMvcRequestBuilders.patch(SKILL_ENDPOINT + savedSkill.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"description\":\"Coding\"}"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		List<Skill> savedskills = (List<Skill>)repository.findAll();
		Assert.assertEquals(1, savedskills.size());
		Assert.assertEquals("Java", savedskills.get(0).getName());
		Assert.assertEquals("Coding", savedskills.get(0).getDescription());
	}
	
	@Test
	public void testDeleteItemEndpoint() throws Exception
	{
		Skill savedSkill = repoTestData();
		
		mockMvc.perform(MockMvcRequestBuilders.delete(SKILL_ENDPOINT + savedSkill.getId()))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		List<Skill> savedskills = (List<Skill>)repository.findAll();
		Assert.assertEquals(0, savedskills.size());
	}
}
