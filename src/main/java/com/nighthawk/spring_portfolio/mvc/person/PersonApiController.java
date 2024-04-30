package com.nighthawk.spring_portfolio.mvc.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/person")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
public class PersonApiController {

    @Autowired
    private PersonJpaRepository repository;

    @Autowired
    private PersonDetailsService personDetailsService;

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Person>> getPeople() {
        return new ResponseEntity<>(repository.findAllByOrderByNameAsc(), HttpStatus.OK);
    }

    @GetMapping("/jwt")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Person> getAuthenticatedPersonData() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Person person = repository.findByEmail(username);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        return optional.map(person -> new ResponseEntity<>(person, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {
            Person person = optional.get();
            repository.deleteById(id);
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> postPerson(@RequestParam("email") String email,
                                             @RequestParam("password") String password,
                                             @RequestParam("name") String name,
                                             @RequestParam("dob") String dobString,
                                             @RequestParam("eco") Integer eco,
                                             @RequestParam("primaryCrop") String primaryCrop,
                                             @RequestParam("cash") Integer cash) {
        try {
            Date dob = new SimpleDateFormat("MM-dd-yyyy").parse(dobString);
            Person person = new Person(email, password, name, eco, primaryCrop, cash, dob);
            personDetailsService.save(person);
            return new ResponseEntity<>(email + " is created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(dobString + " error; try MM-dd-yyyy", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> personSearch(@RequestBody final Map<String, String> map) {
        String term = map.get("term");
        List<Person> list = repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/setStats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> personStats(@RequestBody final Map<String, Object> stat_map) {
        long id = Long.parseLong((String) stat_map.get("id"));
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {
            Person person = optional.get();
            Map<String, Object> attributeMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : stat_map.entrySet()) {
                if (!entry.getKey().equals("date") && !entry.getKey().equals("id")) {
                    attributeMap.put(entry.getKey(), entry.getValue());
                }
            }
            Map<String, Map<String, Object>> date_map = new HashMap<>();
            date_map.put((String) stat_map.get("date"), attributeMap);
            person.setStats(date_map);
            repository.save(person);
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/integerMap/update/{id}")
    public ResponseEntity<String> updateIntegerMap(@PathVariable long id, @RequestBody Map<String, Integer> integerMap) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {
            Person person = optional.get();
            Map<String, Integer> existingIntegerMap = person.getIntegerMap();
            existingIntegerMap.putAll(integerMap);
            person.setIntegerMap(existingIntegerMap);
            repository.save(person);
            return new ResponseEntity<>("Additional key-value pairs added to the integerMap for person with ID: " + id, HttpStatus.OK);
        }
        return new ResponseEntity<>("Person not found with ID: " + id, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/integerMap/{id}")
    public ResponseEntity<Map<String, Integer>> getIntegerMap(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {
            Person person = optional.get();
            Map<String, Integer> integerMap = person.getIntegerMap();
            return new ResponseEntity<>(integerMap, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/integerMap/delete/{id}")
    public ResponseEntity<String> deleteIntegerMap(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {
            Person person = optional.get();
            person.setIntegerMap(new HashMap<>());
            repository.save(person);
            return new ResponseEntity<>("IntegerMap deleted successfully for person with ID: " + id, HttpStatus.OK);
        }
        return new ResponseEntity<>("Person not found with ID: " + id, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/eco")
    public ResponseEntity<List<Person>> getPeopleSortedByEco() {
        List<Person> people = repository.findAll();
        people.sort(Comparator.comparingInt(Person::getEco).reversed());
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @PostMapping("/ecoUpdate")
    public ResponseEntity<Object> updateEco(@RequestBody Map<String, Object> requestBody) {
        String email = (String) requestBody.get("email");
        Integer eco = (Integer) requestBody.get("eco");
        personDetailsService.changeEco(email, eco);
        return new ResponseEntity<>(email + " eco score updated successfully", HttpStatus.CREATED);
    }

    // @GetMapping("/cash/{id}")
    // public ResponseEntity<Integer> getCash(@PathVariable long id) {
    //     int cash = personDetailsService.getCash(id);
    //     return new ResponseEntity<>(cash, HttpStatus.OK);
    // }

    @PostMapping("/cashUpdate")
    public ResponseEntity<Object> updateCash(@RequestBody Map<String, Object> requestBody) {
        String email = (String) requestBody.get("email");
        Integer cash = (Integer) requestBody.get("cash");
        personDetailsService.changeCash(email, cash);
        return new ResponseEntity<>(email + " cash amount updated successfully", HttpStatus.CREATED);
    }
}
