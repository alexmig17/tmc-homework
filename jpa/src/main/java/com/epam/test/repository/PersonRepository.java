package com.epam.test.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epam.test.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>, PersonCustomRepository {

    /**
     * find all persons with basket
     *
     * @return List of Person
     */
    @Query(value = "SELECT * FROM PERSON p join BASKET b on p.id = b.PERSON_ID join USER u on p.id = u.id", nativeQuery = true)
    List<Person> findPersonsWithBasket();

    Optional<Person> getByEmail(String email);

    @Query("SELECT COUNT(p.id) FROM Person p WHERE p.email=:email")
    Integer countUsersWithEmail(String email);

    /**
     * find persons registered on <code>date</>
     *
     * @param date date
     * @return List of Person
     */
    @Query("SELECT p FROM Person p where p.creationDate = :date")
    List<Person> findPersonsRegisteredOnDay(@Param("date") LocalDate date);

    @Override
    @CacheEvict("findById")
    <S extends Person> S save(S entity);

    @Override
    @Cacheable("findById")
    Optional<Person> findById(Integer integer);

    @Override
    @CacheEvict("findById")
    void deleteById(Integer integer);

    @Override
    @CacheEvict("findById")
    void delete(Person entity);
}
