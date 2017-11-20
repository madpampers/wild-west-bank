package ru.edhunter.wildwestbank.model.idgenerator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import ru.edhunter.wildwestbank.model.Account;

import java.io.Serializable;
import java.util.Random;

public class RandomAccountIdGenerator implements IdentifierGenerator {

    private Random r = new Random();

    @Override
    public Serializable generate(SessionImplementor sessionImplementor, Object o) throws HibernateException {
        if (o instanceof Account) {
            return ((Account) o).getType() + (1_000_000_000 + Math.abs(r.nextInt(1_000_000_000)));
        } else throw new RuntimeException();
    }
}
