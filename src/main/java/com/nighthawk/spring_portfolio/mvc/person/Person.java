package com.nighthawk.spring_portfolio.mvc.person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import com.vladmihalcea.hibernate.type.json.JsonType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Convert(attributeName ="person", converter = JsonType.class)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Size(min=5)
    @Column(unique=true)
    @Email
    private String email;

    @NotEmpty
    private String password;

    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;

    @ManyToMany
    private Collection<PersonRole> roles = new ArrayList<>();

    @Convert(converter = JsonType.class)
    private Map<String, Map<String, Object>> stats = new HashMap<>();

    private Map<String, List<Integer>> playerIdMap = new HashMap<>();

    public Person(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static Person[] init() {
        Person p1 = new Person("toby@gmail.com", "123Toby!", "Thomas Edison");
        Person p2 = new Person("test@gmail.com", "password", "Tester Testing");
        Person p3 = new Person("niko@gmail.com", "123Niko!", "Nikola Tesla");
        Person p4 = new Person("madam@gmail.com", "123Madam!", "Madam Currie");
        Person p5 = new Person("jm1021@gmail.com", "123Qwerty!", "John Mortensen");

        Person persons[] = {p1, p2, p3, p4, p5};

        // Assign random playerIds
        Random random = new Random();
        for (Person person : persons) {
            int numPlayerIds = random.nextInt(5) + 1; // Generate 1 to 5 player IDs
            List<Integer> playerIds = new ArrayList<>();
            for (int i = 0; i < numPlayerIds; i++) {
                playerIds.add(random.nextInt(10000) + 1);
            }
            person.getPlayerIdMap().put("playerIds", playerIds);
        }

        return persons;
    }

    public static void main(String[] args) {
        Person persons[] = init();

        for (Person person : persons) {
            System.out.println(person);
        }
    }
}
