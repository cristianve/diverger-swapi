# Technical test Swapi
Diverger Swapi Proxy Java API

### Java Spring dependencies

- Spring web
- Lombok
- Swagger

### Versions

* Spring boot 3.2.5
* Java 17

# Exercise definition

1. Connect to https://swapi.trileuco.com/ external API.
2. Create endpoint:
   --> http://{host}:{port}/swapi-proxy/person-info?name=Luke%20Skywalker
3. Return JSON with this format:
```
{
  "name": "Luke Skywalker",
  "birth_year": "19BBY",
  "gender": "male",
  "planet_name": "Tatooine",
  "fastest_vehicle_driven": "X-wing",
  "films": [
    {
      "name": "A New Hope",
      "release_date": "1977-05-25"
    },
    {
      "name": "The Empire Strikes Back",
      "release_date": "1980-05-17"
    },
    {
      "name": "Return of the Jedi",
      "release_date": "1983-05-25"
    },
    {
      "name": "Revenge of the Sith",
      "release_date": "2005-05-19"
    },
    {
      "name": "The Force Awakens",
      "release_date": "2015-12-11"
    }
  ]
}
```

#### Notes: 
- **Incorrect name** -- return --> **JSON 404 ERROR**
- Attribute **fastest_vehicle_driven** has to be filled with the faster (high max_atmosphering_speed) vehicle or starship.



# How to set up


# Results