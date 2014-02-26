package com.javathinking.samples.jpa;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Date: 12/04/13
 */
@ContextConfiguration
public class JpaEnumTest extends AbstractTransactionalJUnit4SpringContextTests {
    @PersistenceContext
    private EntityManager em;

    @Test
    public void saveUserShouldRecordRoles() {
        // set up
        em.persist(new User.Builder().withUserName("paul").withRole(Role.Admin).withRole(Role.Manager).build());
        // perform
        Query query = em.createQuery("select o from User o");
        User user = (User) query.getSingleResult();
        // assert
        assertThat(user.getUserName(), is("paul"));
        assertThat(user.getRoles().size(), is(2));
    }

    @Test
    public void orderOfRolesShouldBePreserved() {
        // set up
        User user1 = new User.Builder().withUserName("paul1").withRole(Role.Admin).withRole(Role.Manager).build();
        User user2 = new User.Builder().withUserName("paul2").withRole(Role.Manager).withRole(Role.Admin).build();
        em.persist(user1);
        em.persist(user2);
        // perform
        User user1a = em.find(User.class, user1.getId());
        User user2a = em.find(User.class, user2.getId());
        // assert
        assertThat(user1a.getUserName(), is("paul1"));
        assertThat(user1a.getRoles().get(0), is(Role.Admin));
        assertThat(user1a.getRoles().get(1), is(Role.Manager));

        assertThat(user2a.getUserName(), is("paul2"));
        assertThat(user2a.getRoles().get(0), is(Role.Manager));
        assertThat(user2a.getRoles().get(1), is(Role.Admin));

    }
}
