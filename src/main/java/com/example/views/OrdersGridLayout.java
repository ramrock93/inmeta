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

/**
 * The OrdersGridLayout extends the {@link VerticalLayout} and implements the
 * ChangeHandler interface. This class represents the presentation of the
 * orders, and a search field to search for orders..<br>
 * </br>
 * 
 * The Scope annotation {@code @Scope("prototype")} tells the spring container
 * that there shall be created a new Bean-instance of this class everytime<br>
 * it is requested. This annotation helps solve the issues with multiple
 * sessions. If this annotation is removed or replaced with
 * {@code @Singleton}<br>
 * a {@link java.lang.IllegalStateException} would be thrown if multiple session
 * would have opened. <br>
 * <br>
 * 
 * The {@link SpringComponent} is an alias for
 * org.springframework.stereotype.Component to prevent conflicts with
 * com.vaadin.ui.Component.
 * 
 * @author Ramin Esfandiari </br>
 *         7. aug. 2017 </br>
 *
 */
@SpringComponent
@Scope("prototype")
public class OrdersGridLayout extends VerticalLayout implements ChangeHandler {

	private Grid<Order> grid; // A grid to display all orders from database.
	private TextField searchField; // A search field to allow searching orders.

	private HorizontalLayout searchLayout;

	@Autowired
	private OrderRepository repo; // A reference to the Order repository to allow CRUD-operations.

	/**
	 * Setup and configure all fields.
	 */
	@PostConstruct
	void init() {

		/*
		 * Instantiate the grid, with Order.class passed as parameter to its
		 * constructor. Set the width to 100% and fill the allocated space. Only show
		 * these three columns.
		 */
		this.grid = new Grid<>(Order.class);
		this.grid.setWidth("100%");
		this.grid.setColumns("service", "customerName", "date");

		/*
		 * Instantiate the search field. Set a placeholder for the empty field. Add an
		 * in icon. Add a value change listener to listen for changes in the search
		 * field. Add an eager value change mode to the search field. Note! This may
		 * cause a lot of network traffic, change to LAZY if this becomes a problem.
		 */
		searchField = new TextField();
		searchField.setPlaceholder("Filter by customer name...");
		searchField.setEnabled(true);
		searchField.setIcon(VaadinIcons.SEARCH);
		searchField.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		searchField.addValueChangeListener(e -> {
			grid.setItems(update(e.getValue())); // Update the grid everytime value changes.
			if (e.getValue().isEmpty()) { // if the search field is empty...
				update(); // display all orders.
			}
		});
		searchField.setValueChangeMode(ValueChangeMode.EAGER);

		searchLayout = new HorizontalLayout();
		searchLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		searchLayout.setStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);

		searchLayout.addComponentsAndExpand(searchField);

		addComponent(searchLayout);// Add search layout to the view.
		addComponentsAndExpand(grid); // Add the grid to layout and take the space allocated.
	}

	/**
	 * Populate the grid with all the orders from database.
	 */
	public void update() {
		grid.setItems(repo.findAll());
	}

	/**
	 * Update the grid with orders containing the following search string.
	 * 
	 * @param searchString
	 *            The search string to enter in search field.
	 * @return
	 */
	public List<Order> update(String searchString) {
		List<Order> allOrders = repo.findAll(); // Store all the orders in allOrders.
		List<Order> filterdOrder = new ArrayList<>(); // Create a filterOrder list.

		// Iterate through the all the orders...
		for (Iterator<Order> it = allOrders.iterator(); it.hasNext();) {
			Order order = (Order) it.next(); // Get the next order...

			// If the customer name contains the same search string...
			if (order.getCustomerName().trim().toLowerCase().contains(searchString.toLowerCase().trim())) {
				filterdOrder.add(order); // add that order to the filtered order list.
			}
		}
		return filterdOrder; // Return the filtered order list.
	}

	/**
	 * Gets the grid.
	 * 
	 * @return The grid to return.
	 */
	public Grid<Order> getGrid() {
		return this.grid;
	}

	/**
	 * Add a selection listener to the grid.
	 * 
	 * @param listener
	 *            The selection listener to add.
	 */
	public void addSelectionListener(SelectionListener<Order> listener) {
		grid.addSelectionListener(listener);
	}

	/**
	 * When the onChange method is called, the grid updates with all the orders.
	 */
	@Override
	public void onChange() {
		update();
	}
}
