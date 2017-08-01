package cm.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.Order;
import com.example.repositories.OrderRepository;

@RestController
public class RestAPIController {

	@Autowired
	OrderRepository repo;

	@RequestMapping("/orders")
	public List<Order> findAll() {

		return repo.findAll();
	}

	@RequestMapping(value = "/orders/{costumerName}", method = RequestMethod.GET)
	public List<Order> findOrderByName(@PathVariable("costumerName") String costumerName) {

		return repo.findByCostumerName(costumerName);

	}
}
