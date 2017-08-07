package com.example;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.models.ChangeHandler;
import com.example.views.OrderFormLayout;
import com.example.views.OrdersGridLayout;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.UI;

/**
 * The OrderSoftUI is the main UI in the OrderSoft web application. This class
 * extends UI class in Vaadin. This class in responsible for presenting the
 * components to the view.
 * 
 * @author Ramin Esfandiari </br>
 *         7. aug. 2017 </br>
 *
 */
@SpringUI
@Theme("valo")
public class OrderSoftUI extends UI {

	private HorizontalSplitPanel splitPanel; // A splitpanel to split the form and the grid.

	@Autowired
	OrdersGridLayout ordersGridLayout; // A reference to OrdersGridLayout.

	@Autowired
	OrderFormLayout orderForm; // A reference to the form.

	/**
	 * Initializes this UI. This method is intended to be overridden by subclasses
	 * to build the view and configure non-component functionality. Performing the
	 * initialization in a constructor is not suggested as the state of the UI is
	 * not properly set up when the constructor is invoked.
	 */
	@Override
	protected void init(VaadinRequest request) {
		// Instantiate the split panel and set its width to fill the entire page.
		splitPanel = new HorizontalSplitPanel();
		splitPanel.setWidth("100%");

		// Add a single-select value change listener to the grid.
		ordersGridLayout.getGrid().asSingleSelect().addValueChangeListener(evt -> {
			orderForm.setOrder(evt.getValue()); // Pass the selected order from the grid to the setOrder method.
			ordersGridLayout.update(); // Update the grid.
		});
		ordersGridLayout.update(); // Update the grid regardless.

		// Set a new change handler to the form.
		orderForm.setChangeHandler(new ChangeHandler() {

			@Override
			public void onChange() {
				ordersGridLayout.update(); // Update the grid on any change.
			}
		});

		// Add ordersGridLayout as first component(left) and set its width to 100%.
		splitPanel.setFirstComponent(ordersGridLayout);
		orderForm.setWidth("80%");
		// Add the form as a the second component(right).
		splitPanel.setSecondComponent(orderForm);

		// Set the splitpanel as the conent of the page.
		setContent(splitPanel);
		ordersGridLayout.update();
	}
}
