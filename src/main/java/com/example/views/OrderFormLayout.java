package com.example.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.example.exceptions.FormNotValidatedexception;
import com.example.models.ChangeHandler;
import com.example.models.Order;
import com.example.models.Service;
import com.example.repositories.OrderRepository;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.UserError;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * The OrderFormLayout extends the {@link VerticalLayout} and represents the
 * form in which orders are to be created, edited and deleted.<br>
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
public class OrderFormLayout extends VerticalLayout {

	private TextField customerNameField; // Field to input customer name.
	private boolean validatedCostumerNameField; // A boolean value to indicate if the customer name is validated.

	private TextField phoneNumberField; // Field to input phone number.
	private boolean validatedPhoneNumberField; // A boolean value to indicate if the phone number is validated.

	private TextField emailField; // Field to input email address.
	private boolean validatedEmailField; // A boolean value to indicate if the email address is validated.

	private TextField fromAddressField; // Field to input the address the customer is moving from.
	private boolean validatedFromAddressField; // A boolean value to indicate if the from address is validated.

	private TextField toAddressField; // Field to input the address the customer is moving to.
	private boolean validatedToAddressField; // A boolean value to indicate if the to address is validated.

	private ComboBox<Service> servicesCombo; // A combo box to choose a service from.
	private boolean validatedServicesComboField; // // A boolean value to indicate if the service field is validated.

	private DateField dateField; // Field to choose a given date from for when the order is to be carried out.
	private boolean validatedDateField; // A boolean value to indicate if the date field is validated.

	private TextArea textAreaField; // Text area to input order description.

	private Button saveOrderButton; // A button to save the order.
	private Button deleteOrderButton; // A button to delete the order.
	private Button cancelOrderButton; // A button to cancel an order.

	private HorizontalLayout nameAndPhoneLayout; // A horizontal layout to holds the name and phone number fields.
	private HorizontalLayout toAndFromAddressLayout; // A horizontal layout to hold the to and from address fields.
	private HorizontalLayout servicesAndDateLayout; // A horizontal layout to hold the services and date fields.
	private HorizontalLayout buttonsLayout; // A horizontal layout to hold the save, delete and cancel buttons.

	@Autowired
	OrderRepository repo; // A reference to the OrderRepository to allow crud-operations to database.

	private Order currentOrder; // Holds a currently chosen order.

	private Binder<Order> binder; // A binder to use for field-validation.

	/**
	 * Setup and configure fields, buttons and layouts.
	 */
	@PostConstruct
	void init() {
		setupAndConfigureFormFields(); // Setup and configure form fields.
		setupAndConfigureFormButtons(); // Setup and configure form buttons.
		setupAndConfigureFormLayouts(); // Setup and configure form layouts.
		this.binder = new Binder<>(); // Instantiate a new Binder object.
	}

	/**
	 * Setup and configure the form layouts.
	 */
	private void setupAndConfigureFormLayouts() {
		/*
		 * Instantiate the layout and add customer name field and phone number fields.
		 * Set the width to 100% and fill the place allocated.
		 */
		nameAndPhoneLayout = new HorizontalLayout(customerNameField, phoneNumberField);
		nameAndPhoneLayout.setWidth("100%");

		/*
		 * Instantiate the layout and add the to and from address fields to it. Set the
		 * width to 100% and fill the place allocated.
		 */
		toAndFromAddressLayout = new HorizontalLayout(fromAddressField, toAddressField);
		toAndFromAddressLayout.setWidth("100%");

		/*
		 * Instantiate the layout and add the services and date fields to it. Set the
		 * width to 100% and fill the place allocated.
		 */
		servicesAndDateLayout = new HorizontalLayout(servicesCombo, dateField);
		servicesAndDateLayout.setWidth("100%");

		/*
		 * Instantiate the layout and add the save, cancel and delete button to it. Make
		 * the cancel button take rest of space allocated. Set the width to 100% and
		 * fill the place allocated.
		 */
		buttonsLayout = new HorizontalLayout(saveOrderButton, cancelOrderButton, deleteOrderButton);
		buttonsLayout.setExpandRatio(cancelOrderButton, 1.0f);
		buttonsLayout.setWidth("100%");

		/*
		 * Align the delete button in top-right position in the layout. Align the save
		 * button in middle-left position in the layout. Align the cancel button in
		 * middle-left position in the layout.
		 */
		buttonsLayout.setComponentAlignment(deleteOrderButton, Alignment.TOP_RIGHT);
		buttonsLayout.setComponentAlignment(saveOrderButton, Alignment.MIDDLE_LEFT);
		buttonsLayout.setComponentAlignment(cancelOrderButton, Alignment.MIDDLE_LEFT);

		// Add the layouts and fields to the form.
		addComponents(nameAndPhoneLayout, emailField, toAndFromAddressLayout, servicesAndDateLayout, textAreaField,
				buttonsLayout);
	}

	/**
	 * Setup and configure the form buttons.
	 */
	private void setupAndConfigureFormButtons() {
		setupAndConfigureSaveOrderButton(); // Setup and configure the save button.
		setupAndConfigureCancelOrderButton(); // Setup and configure the cancel button.
		setupAndConfigureDeleteOrderButton(); // Setup and configure the delete button.
	}

	/**
	 * setup and configure the delete button.
	 */
	private void setupAndConfigureDeleteOrderButton() {
		/*
		 * Instantiate the delete button with "Delete" ad caption and Trash as icon. Set
		 * the style name to Danger(Red). Add a click listener to listen for click
		 * events.
		 */
		deleteOrderButton = new Button("Delete", VaadinIcons.TRASH);
		deleteOrderButton.setStyleName(ValoTheme.BUTTON_DANGER);
		deleteOrderButton.addClickListener(e -> {
			deleteOrder(this.currentOrder); // Delete the currently selected order.
		});
	}

	/**
	 * Setup and configure cancel button.
	 */
	private void setupAndConfigureCancelOrderButton() {
		/*
		 * Instantiate the cancel button with "Cancel" as caption and Close(cross) as
		 * icon. Set the style name to friendly(green). Add a click listener to listen
		 * for clicks.
		 */
		cancelOrderButton = new Button("Cancel", VaadinIcons.CLOSE);
		cancelOrderButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		cancelOrderButton.addClickListener(e -> {
			clearFields(); // Clear all fields.
			this.currentOrder = null; // Reset the current order.
		});
	}

	/**
	 * Setup and configure the save button.
	 */
	private void setupAndConfigureSaveOrderButton() {
		/*
		 * Instantiate the save button with "Save" as caption and a click listener to
		 * listen for clicks. Set the icon to database. Set style name to primary(blue).
		 */
		saveOrderButton = new Button("Save", e -> {
			saveOrder(this.currentOrder); // Save current order.
			this.currentOrder = null; // Reset current order.
			clearFields(); // And clear the fields.
		});
		saveOrderButton.setIcon(VaadinIcons.DATABASE);
		saveOrderButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
	}

	/**
	 * Setup and configure form fields.
	 */
	private void setupAndConfigureFormFields() {
		setupAndConfigureCostumerNameField(); // Setup and configure costumer name field.
		setupAndConfigurePhoneNumberField(); // Setup and configure phone number field.
		setupAndConfigureEmailField(); // Setup and configure email address field.
		setupAndConfigureFromAddressField(); // Setup and configure the from address field.
		setupAndConfigureToAddressField(); // Setup and configure the to address field.
		setupAndConfigureServicesComboField(); // Setup and configure services combo box field.
		setupAndConfigureDateField(); // Setup and configure the date field.
		setupAndConfigureTextAreaField(); // Setup and configure the text area.
	}

	/**
	 * Setup and configure the text area field.
	 */
	private void setupAndConfigureTextAreaField() {
		/*
		 * Instantiate the text area field with "Description (optional)" as caption. Set
		 * the width of the field to 100% and fill the allocated space.
		 */
		textAreaField = new TextArea("Description (optional)");
		textAreaField.setWidth("100%");
	}

	/**
	 * Setup and configure the date field.
	 */
	private void setupAndConfigureDateField() {
		/*
		 * Instantiate the date field with "Date (YYYY-MM-DD)" as caption. Set a red
		 * asterix (*) indicating a mandatory field. Set the width to 100% and fill the
		 * allocated space. Set the date format to "yyyy-MM-dd". Add a blur listener to
		 * listen for when the field loses focus.
		 */
		dateField = new DateField("Date (YYYY-MM-DD)");
		dateField.setRequiredIndicatorVisible(true);
		dateField.setWidth("100%");
		dateField.setDateFormat("yyyy-MM-dd");

		dateField.addBlurListener(e -> {
			validateDateField(); // Validate the date field.
		});
	}

	/**
	 * Setup and configure the services combo box field.
	 */
	private void setupAndConfigureServicesComboField() {
		/*
		 * Instantiate the services combo box field with "Choose Service" as caption.
		 * Set a red asterix (*) indicating a mandatory field. Set the width to 100% and
		 * fill the allocated space. Fill the combo box with options by calling
		 * Services.values(). Add a blur listener to listen for when the field looses
		 * focus.
		 */
		servicesCombo = new ComboBox<>("Choose Service:");
		servicesCombo.setRequiredIndicatorVisible(true);
		servicesCombo.setWidth("100%");
		servicesCombo.setItems(Service.values());

		servicesCombo.addBlurListener(e -> {
			validateServicesComboField(); // Validate the services combo box field.
		});
	}

	/**
	 * Setup and configure the to-address field.
	 */
	private void setupAndConfigureToAddressField() {
		/*
		 * Instantiate the field with "To Address:" as caption. Set a red asterix (*)
		 * indicating a mandatory field. Set the width to 100% and fill the allocated
		 * space. Add a blur listener to listen for when the field loses focus.
		 */
		toAddressField = new TextField("To Address:");
		toAddressField.setRequiredIndicatorVisible(true);
		toAddressField.setWidth("100%");

		toAddressField.addBlurListener(e -> {
			validateToAddressField(); // Validate the field.
		});
	}

	/**
	 * Setup and configure the from-address field.F
	 */
	private void setupAndConfigureFromAddressField() {
		/*
		 * Instantiate the field with "From Address:" as caption. Set a red asterix (*)
		 * indicating a mandatory field. Set the width to 100% and fill the allocated
		 * space. Add a blur listener to listen for when the field loses focus.
		 */
		fromAddressField = new TextField("From Address:");
		fromAddressField.setRequiredIndicatorVisible(true);
		fromAddressField.setWidth("100%");

		fromAddressField.addBlurListener(e -> {
			validateFromAddressField(); // Validate the field.
		});
	}

	/**
	 * Setup and configure the email address field.
	 */
	private void setupAndConfigureEmailField() {
		/*
		 * Instantiate the field with "Email:" as caption. Set a red asterix (*)
		 * indicating a mandatory field. Set the width to 100% and fill the allocated
		 * space. Add a blur listener to listen for when the field loses focus.
		 */
		emailField = new TextField("Email:");
		emailField.setRequiredIndicatorVisible(true);
		emailField.setWidth("100%");

		emailField.addBlurListener(e -> {
			validateEmailField(); // Validate the email address field.
		});
	}

	/**
	 * Setup and configure the phone number field.
	 */
	private void setupAndConfigurePhoneNumberField() {
		/*
		 * Instantiate the phone number fields with "Phone Number:" as caption. Set a
		 * red asterix (*) indicating a mandatory field. Set the width to 100% and fill
		 * the allocated space. Add a blur listener to listen for when the field loses
		 * focus.
		 */
		phoneNumberField = new TextField("Phone Number:");
		phoneNumberField.setRequiredIndicatorVisible(true);
		phoneNumberField.setWidth("100%");

		phoneNumberField.addBlurListener(e -> {
			validatePhoneNumberField(); // Validate the phone number field.
		});
	}

	/**
	 * Setup and configure the customer name field.
	 */
	private void setupAndConfigureCostumerNameField() {
		/*
		 * Instantiate the field with "Full Name:" as caption. Set a red asterix (*)
		 * indicating a mandatory field. Set the width to 100% and fill the allocated
		 * space. Add a blur listener to listen for when the field loses focus.
		 */
		customerNameField = new TextField("Full Name:");
		customerNameField.setRequiredIndicatorVisible(true);
		customerNameField.setWidth("100%");

		customerNameField.addBlurListener(e -> {
			validateCostumerNameField(); // Validate the customer name field.
		});
	}

	/**
	 * Set the current order
	 * 
	 * @param order
	 *            The order to pass.
	 */
	public void setOrder(Order order) {

		/*
		 * If the order passed throgh the parameter is not null, assign it to the field
		 * variable. Furthermore, pass the current order to setupFormBinding to bind the
		 * current order to the form.
		 */
		if (order != null) {
			this.currentOrder = order;
			setupFormBinding(this.currentOrder);
		}
	}

	/**
	 * Setup the form binding. This method bind the form with an order object, and
	 * adds validators and converters to the field, where necessary.
	 * 
	 * @param order
	 *            The order to bind to the form.
	 */
	private void setupFormBinding(Order order) {
		this.binder.setBean(order); // Pass the order as a bean to the binder.

		// Bind the customer name field to the binder.
		this.binder.bind(this.customerNameField, Order::getCustomerName, Order::setCustomerName);

		/*
		 * For the phone number field, add a String to Integer converter with a
		 * validator that checks whether the integer is greater than 0. bind the field
		 * to form.
		 */
		this.binder.forField(phoneNumberField)
				.withConverter(new StringToIntegerConverter("Input value should be an integer"))
				.withValidator(integer -> integer > 0, "Input must be a positive integer.")
				.bind(Order::getPhoneNumber, Order::setPhoneNumber);

		// For the email field add a Email validator and bind it to the form.
		this.binder.forField(this.emailField).withValidator(new EmailValidator("Enter a valid email address."))
				.bind(Order::getEmail, Order::setEmail);

		// Bind the rest of the fields to the form.
		this.binder.bind(this.fromAddressField, Order::getFromAddress, Order::setFromAddress);
		this.binder.bind(this.toAddressField, Order::getFromAddress, Order::setFromAddress);
		this.binder.bind(this.servicesCombo, Order::getService, Order::setService);
		this.binder.bind(this.textAreaField, Order::getDescription, Order::setDescription);
		this.binder.bind(this.dateField, Order::getDate, Order::setDate);
	}

	/**
	 * Validates the entire form.
	 * 
	 * @return True if the form is validated, False otherwise.
	 * @throws FormNotValidatedexception
	 *             When the form is not validated.
	 */
	private boolean orderFormValidation() throws FormNotValidatedexception {
		boolean validated = false; // boolean value to see if the form is valdated.

		// If either of fields is not validated, besides text area...
		if ((validatedCostumerNameField || validatedPhoneNumberField || validatedEmailField || validatedFromAddressField
				|| validatedToAddressField || validatedServicesComboField || validatedDateField) == false) {
			validated = false; // set validated to false...
			throw new FormNotValidatedexception("Form not validated!"); // and throw an exception.
		} else {
			// If all the fields in the form is validated, set validated to true.
			validated = true;
		}
		return validated; // Return validated.
	}

	/**
	 * Validate date field.
	 * 
	 * @return True if data field is validated, false otherwise.
	 */
	private boolean validateDateField() {
		/*
		 * If the date field value is empty, set a component error with given error
		 * message. Else remove the error, and set the validateDateField variable to
		 * true.
		 */
		if (dateField.getValue() == null) {
			dateField.setComponentError(new UserError("Please choose a valid date."));
			validatedDateField = false;
		} else {
			dateField.setComponentError(null);
			validatedDateField = true;
		}
		return validatedToAddressField;
	}

	/**
	 * Validate the service combo box field.
	 * 
	 * @return True if the field is validated, false otherwise.
	 */
	private boolean validateServicesComboField() {
		/*
		 * If the service combo box field value is empty, set a component error with
		 * given error message. Else remove the error, and set the validateDateField
		 * variable to true.
		 */
		if (servicesCombo.getValue() == null) {
			servicesCombo.setComponentError(new UserError("Please choose choose one of the services available."));
			validatedServicesComboField = false;
		} else {
			servicesCombo.setComponentError(null);
			validatedServicesComboField = true;
		}
		return validatedToAddressField;
	}

	/**
	 * Validate the to-address field.
	 * 
	 * @return True if the to-address field is validated, false otherwise.
	 */
	private boolean validateToAddressField() {
		/*
		 * If the to-address field value is empty, set a component error with given
		 * error message. Else remove the error, and set the validateDateField variable
		 * to true.
		 */
		if (toAddressField.getValue().isEmpty()) {
			toAddressField.setComponentError(new UserError("Empty value is not allowed"));
			validatedToAddressField = false;
		} else {
			toAddressField.setComponentError(null);
			validatedToAddressField = true;
		}
		return validatedToAddressField;
	}

	/**
	 * Validate the from-address field.
	 * 
	 * @return True if the from-address field is validated, false otherwise.
	 */
	private boolean validateFromAddressField() {
		/*
		 * If the from-address field value is empty, set a component error with given
		 * error message. Else remove the error, and set the validateDateField variable
		 * to true.
		 */

		if (fromAddressField.getValue().isEmpty()) {
			fromAddressField.setComponentError(new UserError("Empty value is not allowed"));
			validatedFromAddressField = false;
		} else {
			fromAddressField.setComponentError(null);
			validatedFromAddressField = true;
		}
		return validatedFromAddressField;
	}

	/**
	 * Validate email field.
	 * 
	 * @return True if the email field is validated, false otherwise.
	 */
	private boolean validateEmailField() {
		/*
		 * If the email field value is empty, set a component error with given error
		 * message. Else remove the error, and set the validateDateField variable to
		 * true.
		 */
		if (emailField.getValue().isEmpty()) {
			emailField.setComponentError(new UserError("Empty value is not allowed"));
			validatedEmailField = false;
		} else {
			emailField.setComponentError(null);
			validatedEmailField = true;
		}
		return validatedCostumerNameField;
	}

	/**
	 * Validate phone number field.
	 * 
	 * @return True if the phone number field is validated, false otherwise.
	 */
	private boolean validatePhoneNumberField() {
		/*
		 * If the phone number field value is empty, set a component error with given
		 * error message. Else remove the error, and set the validateDateField variable
		 * to true.
		 */
		if (phoneNumberField.getValue().isEmpty()) {
			phoneNumberField.setComponentError(new UserError("Empty value not allowed"));
			validatedPhoneNumberField = false;
		} else if (phoneNumberField.getValue().length() != 8) {
			phoneNumberField.setComponentError(new UserError("A valid phone number contains 8 integers."));
			validatedPhoneNumberField = false;
		} else {
			phoneNumberField.setComponentError(null);
			validatedPhoneNumberField = true;
		}
		return validatedCostumerNameField;
	}

	/**
	 * Validate customer name field.
	 * 
	 * @return True if customer name field is validated, false otherwise.
	 */
	private boolean validateCostumerNameField() {
		/*
		 * If the customer name field value is empty, set a component error with given
		 * error message. Else remove the error, and set the validateDateField variable
		 * to true.
		 */
		if (customerNameField.getValue().isEmpty()) {
			customerNameField.setComponentError(new UserError("Empty value not allowed"));
			validatedCostumerNameField = false;
		} else {
			customerNameField.setComponentError(null);
			validatedCostumerNameField = true;
		}
		return validatedCostumerNameField;
	}

	/**
	 * Save the order. If the order is null, a new order is created from the values
	 * in the form, else the order which is not null is saved to the database.
	 * 
	 * @param order
	 *            The order to save.
	 */
	public void saveOrder(Order order) {

		if (order != null) { // If the order is not null...
			repo.save(order); // save it to the database.

		} else { // else if the orders is null....
			try {
				if (orderFormValidation()) { // if the form is validated...
					// Create a new order, and pass the values from the form to its constructor...
					Order newOrder = new Order(customerNameField.getValue(),
							Integer.parseInt(phoneNumberField.getValue()), emailField.getValue(),
							fromAddressField.getValue(), toAddressField.getValue(), servicesCombo.getValue(),
							textAreaField.getValue(), dateField.getValue());
					repo.save(newOrder); // finally save the order to database.
				}
			} catch (FormNotValidatedexception fnvex) {
				validateAllFields(); // If the form is not validated, show which fields is not validated.
				Notification.show("Error", fnvex.getMessage(), Type.ERROR_MESSAGE); // Show a error notification.

			} catch (NumberFormatException nfex) {
				// If anything else than a number is passed in the phone number field, set
				// component error.
				phoneNumberField.setComponentError(new UserError("Field contains invalid characters..."));
			}
		}
	}

	/**
	 * Validate all fields.
	 */
	private void validateAllFields() {
		validateCostumerNameField();
		validatePhoneNumberField();
		validateEmailField();
		validateFromAddressField();
		validateToAddressField();
		validateServicesComboField();
		validateDateField();
	}

	/**
	 * Set change handler.
	 * 
	 * @param changeHandler
	 *            The change handler to set.
	 */
	public void setChangeHandler(ChangeHandler changeHandler) {
		// On any click event, call the onChange method in change handler.
		saveOrderButton.addClickListener(e -> changeHandler.onChange());
		deleteOrderButton.addClickListener(e -> changeHandler.onChange());
	}

	/**
	 * Clear all fields.
	 */
	public void clearFields() {
		customerNameField.clear();
		customerNameField.setComponentError(null);

		phoneNumberField.clear();
		phoneNumberField.setComponentError(null);

		emailField.clear();
		emailField.setComponentError(null);

		toAddressField.clear();
		toAddressField.setComponentError(null);

		fromAddressField.clear();
		fromAddressField.setComponentError(null);

		servicesCombo.clear();
		servicesCombo.setComponentError(null);

		dateField.clear();
		dateField.setComponentError(null);

		textAreaField.clear();
	}

	/**
	 * Delete a given order.
	 * 
	 * @param order
	 *            The order to delete.s
	 */
	public void deleteOrder(Order order) {
		repo.deleteOrderById(order.getId()); // Delete the order, given its id.
		this.currentOrder = null; // Reset current order.
		clearFields(); // And clear all fields.
	}
}
