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
import skills.model.Person;
import skills.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SkillsApplication.class)
@AutoConfigureMockMvc
public class PersonEndpointTest
{	
	@Autowired
	MockMvc mockMvc;
	@Autowired
	PersonRepository repository;
	
	private static final String PEOPLE_ENDPOINT = "/people/";
	
	private Person repoTestData()
	{
		repository.deleteAll();
		Person person = new Person();
		person.setFirstName("Stephen");
		person.setLastName("Shephard");
		return repository.save(person);
	}
	
	@Test
	public void testGetCollectionEndpoint() throws Exception
	{
		repoTestData();
		
		mockMvc.perform(MockMvcRequestBuilders.get(PEOPLE_ENDPOINT))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$..people.length()").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$..people[0].firstName").value("Stephen"))
				.andExpect(MockMvcResultMatchers.jsonPath("$..people[0].lastName").value("Shephard"));
	}
	
	@Test
	public void testGetItemEndpoint() throws Exception
	{
		Person savedPerson = repoTestData();
		
		mockMvc.perform(MockMvcRequestBuilders.get(PEOPLE_ENDPOINT + savedPerson.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Stephen"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Shephard"));
	}
	
	@Test
	public void testPostItemEndpoint() throws Exception
	{
		repository.deleteAll();
		
		mockMvc.perform(MockMvcRequestBuilders.post(PEOPLE_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"firstName\":\"John\",\"lastName\":\"Smith\"}"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		List<Person> savedPeople = (List<Person>)repository.findAll();
		Assert.assertEquals(1, savedPeople.size());
		Assert.assertEquals("John", savedPeople.get(0).getFirstName());
		Assert.assertEquals("Smith", savedPeople.get(0).getLastName());
	}
	
	@Test
	public void testPutItemEndpoint() throws Exception
	{
		Person savedPerson = repoTestData();
		
		mockMvc.perform(MockMvcRequestBuilders.put(PEOPLE_ENDPOINT + savedPerson.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"firstName\":\"Peter\",\"lastName\":\"Smith\"}"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		List<Person> savedPeople = (List<Person>)repository.findAll();
		Assert.assertEquals(1, savedPeople.size());
		Assert.assertEquals("Peter", savedPeople.get(0).getFirstName());
		Assert.assertEquals("Smith", savedPeople.get(0).getLastName());
	}
	
	@Test
	public void testPatchItemEndpoint() throws Exception
	{
		Person savedPerson = repoTestData();
		
		mockMvc.perform(MockMvcRequestBuilders.patch(PEOPLE_ENDPOINT + savedPerson.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"lastName\":\"Smith\"}"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		List<Person> savedPeople = (List<Person>)repository.findAll();
		Assert.assertEquals(1, savedPeople.size());
		Assert.assertEquals("Stephen", savedPeople.get(0).getFirstName());
		Assert.assertEquals("Smith", savedPeople.get(0).getLastName());
	}
	
	@Test
	public void testDeleteItemEndpoint() throws Exception
	{
		Person savedPerson = repoTestData();
		
		mockMvc.perform(MockMvcRequestBuilders.delete(PEOPLE_ENDPOINT + savedPerson.getId()))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		List<Person> savedPeople = (List<Person>)repository.findAll();
		Assert.assertEquals(0, savedPeople.size());
	}
}
