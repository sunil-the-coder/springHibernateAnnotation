package com.spatil.person.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spatil.person.dao.PersonDao;
import com.spatil.person.model.Person;

@Repository("personDao")
public class PersonDaoImpl implements PersonDao {

	@Autowired
	private SessionFactory sessionFactory;

	/*public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}*/

	@Override
	public void savePerson(Person person) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(person);
		session.getTransaction().commit();
	}

	@Override
	public void updatePerson(Person person) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(person);
		session.getTransaction().commit();
	}

	@Override
	public void deletePerson(Integer personId) {		
		Person person = findPersonById(personId);
		
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(person);
		session.getTransaction().commit();
	}

	@Override
	public Person findPersonById(Integer personId) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Person person = (Person) session.load(Person.class, personId);
		session.getTransaction().commit();
		return person;
	}

	@Override
	public Person findPersonByName(String personName) {

		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();

		Criteria criteria = session.createCriteria(Person.class);
		criteria.add(Expression.eq("name", personName));

		// Only return first matching person to upper layer.
		Person person = (Person) criteria.list().get(0);

		session.getTransaction().commit();
		
		return person;
	}

	@Override
	public List<Person> getAllPersons() {

		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Person.class);
		List<Person> persons = (List<Person>) criteria.list();
		session.getTransaction().commit();

		// return (List<Person>) session.createQuery("from person").list();

		return persons;

	}

}
