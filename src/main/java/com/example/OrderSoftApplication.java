package com.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import com.example.models.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.example.models.Address;
import com.example.models.Customer;
import com.example.models.Order;
import com.example.models.ServiceTypes;
import com.example.repositories.AddressRepository;
import com.example.repositories.CustomerRepository;
import com.example.repositories.OrderRepository;
import com.example.repositories.ServiceRepository;

/**
 * The OrderSoftApplication is the main class in OrderSoft web application.
 * 
 * 
 * @SpringBootApplication
 *                        <p>
 *                        This annotation replaces
 *                        the @Configure, @EnableAutoConfiguratrion and
 *                        ComponentScan.
 *                        </p>
 *                        {@link "http://docs.spring.io/autorepo/docs/spring-boot/current/reference/html/using-boot-using-springbootapplication-annotation.html"}
 * 
 * @author Ramin Esfandiari </br>
 *         7. aug. 2017 </br>
 */
@SpringBootApplication
public class OrderSoftApplication {

	private static final Logger log = LoggerFactory.getLogger(OrderSoftApplication.class); // A logger to output log
																							// info.

	public static void main(String[] args) {
		SpringApplication.run(OrderSoftApplication.class, args); // Run the application.
	}

	/**
	 * This method creates some sample entries and populates the database.
	 * 
	 * @param orderRepo
	 *            The repository to add, allowing CRUD-operations.
	 * @return The CommandLinRunner to return.
	 */
	@Bean
	public CommandLineRunner loadData(OrderRepository orderRepo, AddressRepository addressRepo,
			ServiceRepository serviceRepo, CustomerRepository customerRepo) {
		return (args) -> {

			Service s1 = new Service(ServiceTypes.CLEANING);
			Service s2 = new Service(ServiceTypes.MOVING);
			Service s3 = new Service(ServiceTypes.PACKING);
			Service s4 = new Service(ServiceTypes.CLEANING);
			Service s5 = new Service(ServiceTypes.PACKING);
			Service s6 = new Service(ServiceTypes.MOVING);

			serviceRepo.save(s1);
			serviceRepo.save(s2);
			serviceRepo.save(s3);
			serviceRepo.save(s4);
			serviceRepo.save(s5);
			serviceRepo.save(s6);

			// ArrayList<Service> list = new ArrayList<>(6);
			// for (int i = 0; i < 6; i++) {
			// list.add(new Service(ServiceTypes.CLEANING));
			// }
			// serviceRepo.save(list);

			// *******************************************************************************

			Address a1 = new Address("Kirkealleen", 86, "3470", "Slemmestad", "Norge");
			addressRepo.save(a1);

			Customer c1 = new Customer("Ramin Esfandiari", 48367767, "ramin_esfandiari_93@hotmail.com", a1);
			customerRepo.save(c1);

			List<Service> cleaning_packing_moving = new LinkedList<>();

			cleaning_packing_moving.add(s1);
			cleaning_packing_moving.add(s2);
			cleaning_packing_moving.add(s3);

			Address m1 = new Address("Nydalsveien", 33, "0184", "Oslo", "Norge");
			addressRepo.save(m1);

			Order o1 = new Order(c1, m1, "Kan ikke før 10:00.", LocalDate.of(2017, 9, 15), cleaning_packing_moving);
			orderRepo.save(o1);

			c1.addOrder(o1);
			customerRepo.save(c1);

			// ************************************************************************************************************

			Address a2 = new Address("Tevlingveien", 114, "1015", "Oslo", "Norge");
			addressRepo.save(a2);

			Customer c2 = new Customer("Golshdid Bahadorian", 94811015, "golshidb@hotmail.com", a2);
			customerRepo.save(c2);

			List<Service> packing_moving = new LinkedList<>();
			packing_moving.add(s4);
			packing_moving.add(s5);

			Address m2 = new Address("Nydalsveien", 33, "0184", "Oslo", "Norge");
			addressRepo.save(m2);

			Order o2 = new Order(c2, m2, "Kan ikke før 10:00.", LocalDate.of(2017, 9, 15), packing_moving);
			orderRepo.save(o2);

			c2.addOrder(o2);
			customerRepo.save(c2);

			// ************************************************************************************************************

			Address a3 = new Address("Norgesveien", 1, "0001", "Oslo", "Norge");
			addressRepo.save(a3);

			Customer c3 = new Customer("Ola Normann", 12345678, "ola.normann@hotmail.com", a3);
			customerRepo.save(c3);

			List<Service> moving = new LinkedList<>();
			moving.add(s6);

			Address m3 = new Address("Norgesveien", 1, "0001", "Oslo", "Norge");
			addressRepo.save(m3);

			Order o3 = new Order(c3, m3, "Kan ikke før 10:00.", LocalDate.of(2017, 9, 15), moving);
			orderRepo.save(o3);

			c3.addOrder(o3);
			customerRepo.save(c3);

			// ************************************************************************************************************

			// fetch all orders from database.
			log.info("Customers found with findAll():");

			log.info("-------------------------------");
			for (Order o : orderRepo.findAll()) {
				log.info(o.toString());
			}
			log.info("");

			log.info("");
		};
	}
}
