# Skills tracker RESTful API

Spring Data Rest application using the HAL browser.  Uses projection on SkillLevel to show a useful summary.

## Endpoints

### people

POST to /people e.g.

```
{
	"firstName": "Stephen",
	"lastName": "Shephard"
}
```

### skills

POST to /skills e.g.

```
{
	"name": "Java",
	"description": "Coding"
}
```

### skillLevels

POST to /skillLevels using URI links to person and skill items

```
{
	"person": "/people/1",
	"skill": "/skills/2",
	"level": "EXPERT"
}
```

Not done

* composite key ID for skillLevels suitable for use with URIs (and hence GET, PUT and DELETE support) - ran out of time, but would use a `BackendIdConverter`

* search - add suitable `find` methods to the repositories for required searches

* logging - can configure Spring Data Rest not to log the request content (therefore not storing personal info in logs) but may need to use `@RepositoryRestController` for more control

* liquibase
