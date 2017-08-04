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

@SpringUI
@Theme("valo")
public class OrderSoftUI extends UI {

	private HorizontalSplitPanel splitPanel;

	@Autowired
	OrdersGridLayout ordersGridLayout;

	@Autowired
	OrderFormLayout orderForm;

	@Override
	protected void init(VaadinRequest request) {
		splitPanel = new HorizontalSplitPanel();
		splitPanel.setWidth("100%");

		ordersGridLayout.getGrid().asSingleSelect().addValueChangeListener(evt -> {
			orderForm.setOrder(evt.getValue());
			ordersGridLayout.update();
		});
		ordersGridLayout.update();

		orderForm.setChangeHandler(new ChangeHandler() {

			@Override
			public void onChange() {
				ordersGridLayout.update();
			}
		});

		splitPanel.setFirstComponent(ordersGridLayout);
		orderForm.setWidth("80%");
		splitPanel.setSecondComponent(orderForm);

		setContent(splitPanel);
		ordersGridLayout.update();
	}

}
