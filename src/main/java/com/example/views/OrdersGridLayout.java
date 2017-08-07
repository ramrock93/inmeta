package com.example.views;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.example.models.ChangeHandler;
import com.example.models.Order;
import com.example.repositories.OrderRepository;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@Scope("prototype")
public class OrdersGridLayout extends VerticalLayout implements ChangeHandler{

	private Grid<Order> grid;
	private TextField searchField;

	private HorizontalLayout searchLayout;

	@Autowired
	private OrderRepository repo;

	private String event = "";

	@PostConstruct
	void init() {

		this.grid = new Grid<>(Order.class);
		this.grid.setWidth("100%");
		this.grid.setColumns("service", "costumerName", "date");

		searchField = new TextField();
		searchField.setPlaceholder("Filter by customer name...");
		searchField.setEnabled(true);
		searchField.setIcon(VaadinIcons.SEARCH);
		searchField.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		searchField.addValueChangeListener(e -> {
			grid.setItems(update(e.getValue()));
			if (e.getValue().isEmpty()) {
				update();
			}
		});

		searchField.setValueChangeMode(ValueChangeMode.EAGER);

		searchLayout = new HorizontalLayout();
		searchLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		searchLayout.setStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);

		searchLayout.addComponentsAndExpand(searchField);

		addComponent(searchLayout);
		addComponentsAndExpand(grid);
	}

	public void update() {
		grid.setItems(repo.findAll());
	}

	public List<Order> update(String searchString) {
		List<Order> allOrders = repo.findAll();
		List<Order> filterdOrder = new ArrayList<>();

		for (Iterator<Order> it = allOrders.iterator(); it.hasNext();) {
			Order order = (Order) it.next();

			if (order.getCustomerName().trim().toLowerCase().contains(searchString.toLowerCase().trim())) {
				filterdOrder.add(order);
			}
		}
		return filterdOrder;
	}

	public Grid<Order> getGrid() {
		return this.grid;
	}

	public void addSelectionListener(SelectionListener<Order> listener) {
		grid.addSelectionListener(listener);
	}

	@Override
	public void onChange() {
		update();
	}
}
