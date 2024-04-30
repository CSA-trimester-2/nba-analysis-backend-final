package com.nighthawk.spring_portfolio.mvc.person;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Convert;
import static jakarta.persistence.FetchType.EAGER;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

import com.vladmihalcea.hibernate.type.json.JsonType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/*
Person is a POJO, Plain Old Java Object.
Annotations add functionality to POJO and connect it to the database.
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Convert(attributeName = "person", converter = JsonType.class)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Size(min = 5)
    @Column(unique = true)
    @Email
    private String email;

    @NotEmpty
    private String password;

    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @ManyToMany(fetch = EAGER)
    private Collection<PersonRole> roles = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> stats = new HashMap<>();

    private Integer eco;
    private String primaryCrop;
    private Integer cash;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Integer> integerMap = new HashMap<>();

    public Person(String email, String password, String name, Date dob) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
    }

    public Person(String email, String password, String name, Integer eco, String primaryCrop, Integer cash, Date dob) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.eco = eco;
        this.primaryCrop = primaryCrop;
        this.cash = cash;
        this.dob = dob;
    }

    public int getAge() {
        if (this.dob != null) {
            LocalDate birthDay = this.dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return Period.between(birthDay, LocalDate.now()).getYears();
        }
        return -1;
    }

    public static Person[] init() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Person[] persons = new Person[2];

        try {
            persons[0] = new Person("mirzahbeg123@gmail.com", "notTellingYouLOL", "h4seebcmd", -5, "corn", 52, sdf.parse("12-06-2007"));
            persons[1] = new Person("ermitsactuallypronouncedwithaTHUH@gmail.com", "iLOVEagricutlre", "tirthFarmer999", 8, "corn", 14, sdf.parse("01-01-2024"));
        } catch (Exception e) {
        }

        return persons;
    }

    public static void main(String[] args) {
        Person[] persons = init();
        for (Person person : persons) {
            System.out.println("Name: " + person.getName());
            System.out.println("Email: " + person.getEmail());
            System.out.println("Password: " + person.getPassword());
            System.out.println("Date of Birth: " + person.getDob());
            System.out.println("Age: " + person.getAge());
            System.out.println("Integer Map: " + person.getIntegerMap());
            System.out.println("Eco: " + person.getEco());
            System.out.println("Primary Crop: " + person.getPrimaryCrop());
            System.out.println("Cash: " + person.getCash());
            System.out.println("---------------------------------");
        }
    }
}