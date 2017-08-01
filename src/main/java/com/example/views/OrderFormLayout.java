package com.example.views;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.models.Order;
import com.example.models.Service;
import com.example.repositories.OrderRepository;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
public class OrderFormLayout extends VerticalLayout {

	private TextField costumerNameField;
	private TextField phoneNumberField;
	private TextField emailField;
	private TextField fromAddressField;
	private TextField toAddressField;
	private ComboBox<Service> servicesCombo;
	private DateField dateField;
	private TextArea textAreaField;

	private Button saveOrder;
	private Button deleteOrder;
	private Button cancelOrder;

	private HorizontalLayout nameAndPhoneLayout;
	private HorizontalLayout toAndFromAddressLayout;
	private HorizontalLayout servicesAndDateLayout;
	private HorizontalLayout buttonsLayout;

	@Autowired
	OrderRepository repo;

	private Order currentOrder;

	private Binder<Order> binder;

	@PostConstruct
	void init() {
		costumerNameField = new TextField("Full Name:");
		costumerNameField.setWidth("100%");

		phoneNumberField = new TextField("Phone Number:");
		phoneNumberField.setWidth("100%");

		emailField = new TextField("Email:");
		emailField.setWidth("100%");
		fromAddressField = new TextField("From Address:");
		fromAddressField.setWidth("100%");
		toAddressField = new TextField("To Address:");
		toAddressField.setWidth("100%");
		servicesCombo = new ComboBox<>("Choose Service:");
		servicesCombo.setWidth("100%");
		servicesCombo.setItems(Service.values());
		dateField = new DateField("Date");
		dateField.setWidth("100%");
		dateField.setDateFormat("dd.MM.yy");

		textAreaField = new TextArea("Description");
		textAreaField.setWidth("100%");

		saveOrder = new Button("Save", e -> {
			saveOrder(this.currentOrder);
			clearFields();
		});
		saveOrder.setIcon(VaadinIcons.DATABASE);

		cancelOrder = new Button("Cancel", VaadinIcons.CLOSE);
		cancelOrder.addClickListener(e -> {
			clearFields();
		});

		deleteOrder = new Button("Delete", VaadinIcons.TRASH);
		deleteOrder.addClickListener(e -> {
			deleteOrder(this.currentOrder);
		});

		nameAndPhoneLayout = new HorizontalLayout(costumerNameField, phoneNumberField);
		nameAndPhoneLayout.setWidth("100%");

		toAndFromAddressLayout = new HorizontalLayout(fromAddressField, toAddressField);
		toAndFromAddressLayout.setWidth("100%");

		servicesAndDateLayout = new HorizontalLayout(servicesCombo, dateField);
		servicesAndDateLayout.setWidth("100%");

		buttonsLayout = new HorizontalLayout(saveOrder, cancelOrder, deleteOrder);
		buttonsLayout.setExpandRatio(cancelOrder, 1.0f);
		buttonsLayout.setWidth("100%");
		buttonsLayout.setComponentAlignment(deleteOrder, Alignment.TOP_RIGHT);
		buttonsLayout.setComponentAlignment(saveOrder, Alignment.MIDDLE_LEFT);
		buttonsLayout.setComponentAlignment(cancelOrder, Alignment.MIDDLE_LEFT);

		addComponents(nameAndPhoneLayout, emailField, toAndFromAddressLayout, servicesAndDateLayout, textAreaField,
				buttonsLayout);

		this.binder = new Binder<>();
	}

	public void setOrder(Order order) {

		if (order != null) {

			this.currentOrder = order;
			setupFormBinding(this.currentOrder);
		}
	}

	private void setupFormBinding(Order order) {
		this.binder.setBean(order);
		this.binder.bind(this.costumerNameField, Order::getCostumerName, Order::setCostumerName);

		this.binder.forField(phoneNumberField)
				.withConverter(new StringToIntegerConverter("Input value should be an integer"))
				.withValidator(integer -> integer > 0, "Input must be a positive integer.")
				.bind(Order::getPhoneNumber, Order::setPhoneNumber);

		this.binder.bind(this.emailField, Order::getEmail, Order::setEmail);
		this.binder.bind(this.fromAddressField, Order::getFromAddress, Order::setFromAddress);
		this.binder.bind(this.toAddressField, Order::getFromAddress, Order::setFromAddress);
		this.binder.bind(this.servicesCombo, Order::getService, Order::setService);
		this.binder.bind(this.textAreaField, Order::getDescription, Order::setDescription);
	}

	public void saveOrder(Order order) {
		repo.save(order);
	}

	public void clearFields() {
		costumerNameField.clear();
		phoneNumberField.clear();
		emailField.clear();
		toAddressField.clear();
		fromAddressField.clear();
		servicesCombo.clear();
		dateField.clear();
		textAreaField.clear();
	}

	public void deleteOrder(Order order) {
		repo.deleteOrderById(order.getId());
		this.currentOrder = null;
		clearFields();
	}

}