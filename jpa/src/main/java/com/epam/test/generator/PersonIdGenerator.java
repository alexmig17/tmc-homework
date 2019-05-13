package com.epam.test.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import com.epam.test.entity.Person;

public class PersonIdGenerator extends SequenceStyleGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        if (obj instanceof Person) {
            Person person = (Person) obj;
            Integer id = person.getId();
            if (id != null) {
                return id;
            }
        }
        return super.generate(session, obj);
    }
}
