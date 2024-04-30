package com.nighthawk.spring_portfolio.mvc.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PersonDetailsService implements UserDetailsService {

    @Autowired
    private PersonJpaRepository personJpaRepository;
    @Autowired
    private PersonRoleJpaRepository personRoleJpaRepository;

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personJpaRepository.findByEmail(email);
        if (person == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        person.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(person.getEmail(), person.getPassword(), authorities);
    }

    public List<Person> listAll() {
        return personJpaRepository.findAllByOrderByNameAsc();
    }

    public List<Person> listLike(String term) {
        return personJpaRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);
    }

    public void save(Person person) {
        person.setPassword(passwordEncoder().encode(person.getPassword()));
        personJpaRepository.save(person);
    }

    public Person get(long id) {
        return personJpaRepository.findById(id).orElse(null);
    }

    public Person getByEmail(String email) {
        return personJpaRepository.findByEmail(email);
    }

    public void delete(long id) {
        personJpaRepository.deleteById(id);
    }

    public void changeEco(String email, int eco) {
        Person player = getByEmail(email);
        if (player != null) {
            int newEco = player.getEco() + eco;
            player.setEco(newEco);
            personJpaRepository.save(player);
        }
    }

    public void changeCash(String email, int cash) {
        Person player = getByEmail(email);
        if (player != null) {
            int newCash = player.getCash() + cash;
            player.setCash(newCash);
            personJpaRepository.save(player);
        }
    }

    public void addRoleToPerson(String email, String roleName) {
        Person person = getByEmail(email);
        if (person != null) {
            PersonRole role = personRoleJpaRepository.findByName(roleName);
            if (role != null) {
                boolean addRole = person.getRoles().stream().noneMatch(r -> r.getName().equals(roleName));
                if (addRole) {
                    person.getRoles().add(role);
                    personJpaRepository.save(person);
                }
            }
        }
    }

    public List<PersonRole> listAllRoles() {
        return personRoleJpaRepository.findAll();
    }

    public void setIntegerMap(long id, Map<String, Integer> integerMap) {
        Person person = get(id);
        if (person != null) {
            person.setIntegerMap(integerMap);
            personJpaRepository.save(person);
        }
    }

    public Map<String, Integer> getIntegerMap(long id) {
        Person person = get(id);
        if (person != null) {
            return person.getIntegerMap();
        }
        return null;
    }

    public void deleteIntegerMap(long id) {
        Person person = get(id);
        if (person != null) {
            person.setIntegerMap(null);
            personJpaRepository.save(person);
        }
    }
}
