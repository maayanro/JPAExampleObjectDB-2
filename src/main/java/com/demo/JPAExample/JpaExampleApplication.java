package com.demo.JPAExample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JpaExampleApplication {

	public static void main(String[] args) {

		SpringApplication.run(JpaExampleApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo() {
		return args -> {
			List<Person> persons = new ArrayList<>();
			Person p1 = new Person("Maayan", Date.valueOf(LocalDate.of(1988,8,10)), Person.Gender.FEMALE);
			Person p2 = new Person("Paz", Date.valueOf(LocalDate.of(1988,8,10)), Person.Gender.FEMALE);
			Person p3 = new Person("Orel", Date.valueOf(LocalDate.of(1989,4,28)), Person.Gender.MALE);
			Person p4 = new Person("Adam", Date.valueOf(LocalDate.of(2020,2,8)), Person.Gender.MALE);
			Person p5 = new Person("Aviv", Date.valueOf(LocalDate.of(2020,10,9)), Person.Gender.MALE);

			List<Person> kids = new ArrayList<>();
			kids.add(p4);
			kids.add(p5);
			p1.setSpouse(p3);
			p1.setKids(kids);

			p3.setKids(kids);

			persons.add(p1);
			persons.add(p2);
			persons.add(p3);
			persons.add(p4);
			persons.add(p5);

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/mydb1.odb");
			EntityManager em = emf.createEntityManager();

			showMenu(em, persons);

			em.close();
			emf.close();
		};
	}

	private void showMenu(EntityManager em, List<Person> persons) {

		System.out.println("--JPA ObjectDB Menu Exercise--");
		System.out.println("1. insert persons");
		System.out.println("2. findAll persons");
		System.out.println("3. findAll persons ById");
		System.out.println("4. findAll persons ByName");
		System.out.println("5. findAll persons ByBirthDate");
		System.out.println("6. findAll persons ByMonth");
		System.out.println("7. setName of person ById");
		System.out.println("8. findAll single female");
		System.out.println("9. findAll parents");
		Scanner choice = new Scanner(System.in);
		System.out.println("enter your choice: (exit press -1)");
		int input = choice.nextInt();
		while(input != -1) {
			switch(input) {
				case 1 :
					insertPersons(em, persons);
					break;
				case 2 :
					findAllPersons(em);
					break;
				case 3 :
					findById(em, 100L);
					break;
				case 4 :
					findByName(em, "Maayan");
					break;
				case 5 :
					Date date = Date.valueOf(LocalDate.of(1988,8,10));
					findByBirthDate(em, date);
					break;
				case 6 :
					findByMonth(em, 8);
					break;
				case 7 :
					setNameById(em, 1L, "MyNewName");
					break;
				case 8 :
					findAllSingleByGender(em, Person.Gender.FEMALE);
					break;
				case 9 :
					findAllParents(em);
					break;
				default:
					System.out.println("!!!please insert a valid choice!!!");
					break;
			}
			input = choice.nextInt();
		}
	}

	private void insertPersons(EntityManager em, List<Person> persons) {

		em.getTransaction().begin();
		for(Person p : persons) {
			em.persist(p);
		}
		em.getTransaction().commit();
	}

	private void findAllPersons(EntityManager em) {

		TypedQuery<Person> q = em.createQuery("Select p from Person p", Person.class);
		List<Person> res = q.getResultList();
		for(Person p : res) {
			System.out.println(p);
		}
	}

	private Person findById(EntityManager em, long id) {
		Person person;
		person = em.find(Person.class, id);
		System.out.println(person);

		return person;
	}

	public List<Person> findByName(EntityManager em, String name) {
		List<Person> persons;
		Query q = em.createQuery("Select p from Person p where p.name=:name");
		q.setParameter("name", name);
		persons = q.getResultList();
		for(Person p : persons) {
			System.out.println(p);
		}
		return persons;
	}

	public List<Person> findByBirthDate(EntityManager em, Date date) {
		List<Person> persons;
		Query q4 = em.createQuery("Select p from Person p where p.birthDate=:date");
		q4.setParameter("date", date);
		persons = q4.getResultList();
		for(Person p : persons) {
			System.out.println(p);
		}

		return persons;
	}

	public List<Person> findByMonth(EntityManager em, int month) {
		List<Person> persons;
		Query q = em.createQuery("Select p from Person p where MONTH(p.birthDate)=:month");
		q.setParameter("month", month);
		persons = q.getResultList();
		for(Person p : persons) {
			System.out.println(p);
		}

		return persons;
	}

	public void setNameById(EntityManager em, Long id, String newName) {
		Person foundP = em.find(Person.class, id);
		if(foundP != null) {
			foundP.setName(newName);
			System.out.println(foundP);
		}
	}

	public List<Person> findAllSingleByGender(EntityManager em, Person.Gender gender) {
		List<Person> persons;
		Query q = em.createQuery("Select p from Person p where p.spouse is null and gender=:gender");
		q.setParameter("gender", gender);
		persons = q.getResultList();
		for(Person p : persons) {
			System.out.println(p);
		}

		return persons;
	}

	public List<Person> findAllParents(EntityManager em) {
		List<Person> persons;
		Query q = em.createQuery("Select p from Person p where p.kids is not empty");
		persons = q.getResultList();
		for(Person p : persons) {
			System.out.println(p);
		}

		return persons;
	}
}
