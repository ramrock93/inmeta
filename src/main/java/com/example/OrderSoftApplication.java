package com.example;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.example.models.Order;
import com.example.models.Service;
import com.example.repositories.OrderRepository;

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
	 * @param repository
	 *            The repository to add, allowing CRUD-operations.
	 * @return The CommandLinRunner to return.
	 */
	@Bean
	public CommandLineRunner loadData(OrderRepository repository) {
		return (args) -> {
			// Create and save some orders.
			repository.save(new Order("Ramin Esfandiari", 48367767, "ramin_esfandiari_93@hotmail.com",
					"Vågavegen 29, 6008 Ålesund", "Kirkealleen 86, 3470 Slemmestad", Service.MOVING, "",
					LocalDate.of(2017, 8, 20)));

			repository.save(new Order("Ramin Esfandiari", 48367767, "ramin_esfandiari_93@hotmail.com",
					"VKirkealleen 86, 3470 Slemmestad", "Nydalsveien 33, 0484 Oslo", Service.CLEANING, "",
					LocalDate.of(2017, 8, 25)));
			repository.save(
					new Order("Golshid Bahadorian", 948110115, "golshidb@hotmail.com", "Vågavegen 29, 6008 Ålesund",
							"Tevlingveien 115E, 0180 Oslo", Service.MOVING, "", LocalDate.of(2017, 8, 9)));
			repository.save(new Order("Ola Normann", 45456321, "olaNor@hotmail.com", "Humleveien, 5001 Harstad",
					"Norgesveien 20, 0101 Oslo", Service.PACKING, "", LocalDate.of(2017, 8, 6)));
			repository.save(new Order("Inga Vertdal", 21025685, "inga@hotmail.com", "verdalsveien 1, 2021 Vartdal",
					"verdalsveien 1, 2021 Vartdal", Service.CLEANING, "", LocalDate.of(2017, 9, 1)));

			repository.save(
					new Order("Jamileh Zare", 45638585, "jamilehzare@gmail.com", "Kirkealleen 86, 3470 Slemmestad",
							"Kirkealleen 86, 3470 Slemmestad", Service.CLEANING, "", LocalDate.of(2017, 8, 10)));
			repository.save(new Order("Darius Zare Esfandiari", 41380761, "dariuszare94@hotmail.com",
					"VKirkealleen 86, 3470 Slemmestad", "Nydalsveien 50, 0484 Oslo", Service.MOVING, "",
					LocalDate.of(2017, 8, 12)));
			repository.save(new Order("Kenneth Fjukstad", 97106222, "k.fjukstad@gmail.com", "Voldavegen 1, 6200 Volda",
					"Nedre Strandgate 20, 6005 Ålesund", Service.MOVING, "", LocalDate.of(2017, 10, 2)));
			repository.save(new Order("Kenneth Fjukstad", 97106222, "k.fjukstad@gmail.com", "Voldavegen 1, 6200 Volda",
					"Nedre Strandgate 20, 6005 Ålesund", Service.CLEANING, "", LocalDate.of(2017, 10, 3)));
			repository.save(new Order("Kenneth Fjukstad", 97106222, "k.fjukstad@gmail.com", "Voldavegen 1, 6200 Volda",
					"Nedre Strandgate 20, 6005 Ålesund", Service.PACKING, "", LocalDate.of(2017, 10, 1)));

			// fetch all orders from database.
			log.info("Customers found with findAll():");

			log.info("-------------------------------");
			for (Order o : repository.findAll()) {
				log.info(o.toString());
			}
			log.info("");

			log.info("");
		};
	}
}
