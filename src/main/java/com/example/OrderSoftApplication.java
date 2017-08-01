package com.example;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.models.Order;
import com.example.models.Service;
import com.example.repositories.OrderRepository;

@SpringBootApplication
public class OrderSoftApplication {
	
	private static final Logger log = LoggerFactory.getLogger(OrderSoftApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(OrderSoftApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner loadData(OrderRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Order("Ramin Esfandiari", 48367767, "ramin_esfandiari_93@hotmail.com", "Vågavegen 29, 6008 Ålesund", "Kirkealleen 86, 3470 Slemmestad", Service.MOVING, "", "05.07.17"));
			repository.save(new Order("Ramin Esfandiari", 48367767, "ramin_esfandiari_93@hotmail.com", "VKirkealleen 86, 3470 Slemmestad", "Nydalsveien 33, 0484 Oslo", Service.CLEANING, "", "05.07.17"));
			repository.save(new Order("Golshid Bahadorian", 948110115, "golshidb@hotmail.com", "Vågavegen 29, 6008 Ålesund", "Tevlingveien 115E, 0180 Oslo", Service.MOVING, "", "05.07.17"));
			repository.save(new Order("Ola Normann", 45456321, "olaNor@hotmail.com", "Humleveien, 5001 Harstad", "Norgesveien 20, 0101 Oslo", Service.PACKING, "", "05.08.17"));
			repository.save(new Order("Inga Vertdal", 21025685, "inga@hotmail.com", "verdalsveien 1, 2021 Vartdal", "verdalsveien 1, 2021 Vartdal", Service.CLEANING, "", "01.08.17"));
			
			repository.save(new Order("Jamileh Zare", 45638585, "jamilehzare@gmail.com", "Kirkealleen 86, 3470 Slemmestad", "Kirkealleen 86, 3470 Slemmestad", Service.CLEANING, "", "10.08.17"));
			repository.save(new Order("Darius Zare Esfandiari", 41380761, "dariuszare94@hotmail.com", "VKirkealleen 86, 3470 Slemmestad", "Nydalsveien 50, 0484 Oslo", Service.MOVING, "", "05.07.17"));
			repository.save(new Order("Kenneth Fjukstad", 97106222, "k.fjukstad@gmail.com", "Voldavegen 1, 6200 Volda", "Nedre Strandgate 20, 6005 Ålesund", Service.MOVING, "", "05.07.17"));
			repository.save(new Order("Kenneth Fjukstad", 97106222, "k.fjukstad@gmail.com", "Voldavegen 1, 6200 Volda", "Nedre Strandgate 20, 6005 Ålesund", Service.CLEANING, "", "05.07.17"));
			repository.save(new Order("Kenneth Fjukstad", 97106222, "k.fjukstad@gmail.com", "Voldavegen 1, 6200 Volda", "Nedre Strandgate 20, 6005 Ålesund", Service.PACKING, "", "05.07.17"));

			// fetch all customers
			log.info("Customers found with findAll():");
			
			log.info("-------------------------------");
			for (Order o : repository.findAll()) {
				log.info(o.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			List<Order> order = repository.findAll();
			log.info("Order found with findAll():");
			log.info("--------------------------------");
			log.info(order.toString());
			log.info("");

			/*// fetch customers by last name
			log.info("Customer found with findByLastNameStartsWithIgnoreCase('Bauer'):");
			log.info("--------------------------------------------");
			for (Customer bauer : repository
					.findByLastNameStartsWithIgnoreCase("Bauer")) {
				log.info(bauer.toString());
			}*/
			log.info("");
		};
	}
}
