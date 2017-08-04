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

@SpringComponent
@Scope("prototype")
public class OrderFormLayout extends VerticalLayout {

	private TextField costumerNameField;
	private boolean validatedCostumerNameField;

	private TextField phoneNumberField;
	private boolean validatedPhoneNumberField;

	private TextField emailField;
	private boolean validatedEmailField;

	private TextField fromAddressField;
	private boolean validatedFromAddressField;

	private TextField toAddressField;
	private boolean validatedToAddressField;

	private ComboBox<Service> servicesCombo;
	private boolean validatedServicesComboField;

	private DateField dateField;
	private boolean validatedDateField;

	private TextArea textAreaField;

	private Button saveOrderButton;
	private Button deleteOrderButton;
	private Button cancelOrderButton;

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
		setupAndConfigureFormFields();
		setupAndConfigureFormButtons();
		setupAndConfigureFormLayouts();

		this.binder = new Binder<>();
	}

	private void setupAndConfigureFormLayouts() {
		nameAndPhoneLayout = new HorizontalLayout(costumerNameField, phoneNumberField);
		nameAndPhoneLayout.setWidth("100%");

		toAndFromAddressLayout = new HorizontalLayout(fromAddressField, toAddressField);
		toAndFromAddressLayout.setWidth("100%");

		servicesAndDateLayout = new HorizontalLayout(servicesCombo, dateField);
		servicesAndDateLayout.setWidth("100%");

		buttonsLayout = new HorizontalLayout(saveOrderButton, cancelOrderButton, deleteOrderButton);
		buttonsLayout.setExpandRatio(cancelOrderButton, 1.0f);
		buttonsLayout.setWidth("100%");
		buttonsLayout.setComponentAlignment(deleteOrderButton, Alignment.TOP_RIGHT);
		buttonsLayout.setComponentAlignment(saveOrderButton, Alignment.MIDDLE_LEFT);
		buttonsLayout.setComponentAlignment(cancelOrderButton, Alignment.MIDDLE_LEFT);

		addComponents(nameAndPhoneLayout, emailField, toAndFromAddressLayout, servicesAndDateLayout, textAreaField,
				buttonsLayout);
	}

	private void setupAndConfigureFormButtons() {
		setupAndConfigureSaveOrderButton();
		setupAndConfigureCancelOrderButton();
		setupAndConfigureDeleteOrderButton();
	}

	private void setupAndConfigureDeleteOrderButton() {
		deleteOrderButton = new Button("Delete", VaadinIcons.TRASH);
		deleteOrderButton.setStyleName(ValoTheme.BUTTON_DANGER);
		deleteOrderButton.addClickListener(e -> {
			deleteOrder(this.currentOrder);
		});
	}

	private void setupAndConfigureCancelOrderButton() {
		cancelOrderButton = new Button("Cancel", VaadinIcons.CLOSE);
		cancelOrderButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		cancelOrderButton.addClickListener(e -> {
			clearFields();
			this.currentOrder = null;
		});
	}

	private void setupAndConfigureSaveOrderButton() {
		saveOrderButton = new Button("Save", e -> {
			saveOrder(this.currentOrder);
			this.currentOrder = null;
			clearFields();
		});
		saveOrderButton.setIcon(VaadinIcons.DATABASE);
		saveOrderButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
	}

	private void setupAndConfigureFormFields() {
		setupAndConfigureCostumerNameField();
		setupAndConfigurePhoneNumberField();
		setupAndConfigureEmailField();
		setupAndConfigureFromAddressField();
		setupAndConfigureFromToAddressField();
		setupAndConfigureServicesComboField();
		setupAndConfigureDateField();
		setupAndConfigureTextAreaField();
	}

	private void setupAndConfigureTextAreaField() {
		textAreaField = new TextArea("Description (optional)");
		textAreaField.setWidth("100%");
	}

	private void setupAndConfigureDateField() {
		dateField = new DateField("Date (YYYY-MM-DD)");
		dateField.setRequiredIndicatorVisible(true);
		dateField.setWidth("100%");
		dateField.setDateFormat("yyyy-MM-dd");

		dateField.addBlurListener(e -> {
			validateDateField();
		});
	}

	private void setupAndConfigureServicesComboField() {
		servicesCombo = new ComboBox<>("Choose Service:");
		servicesCombo.setRequiredIndicatorVisible(true);
		servicesCombo.setWidth("100%");
		servicesCombo.setItems(Service.values());

		servicesCombo.addBlurListener(e -> {
			validateServicesComboField();
		});
	}

	private void setupAndConfigureFromToAddressField() {
		toAddressField = new TextField("To Address:");
		toAddressField.setRequiredIndicatorVisible(true);
		toAddressField.setWidth("100%");

		toAddressField.addBlurListener(e -> {
			validateToAddressField();
		});
	}

	private void setupAndConfigureFromAddressField() {
		fromAddressField = new TextField("From Address:");
		fromAddressField.setRequiredIndicatorVisible(true);
		fromAddressField.setWidth("100%");

		fromAddressField.addBlurListener(e -> {
			validateFromAddressField();
		});
	}

	private void setupAndConfigureEmailField() {
		emailField = new TextField("Email:");
		emailField.setRequiredIndicatorVisible(true);
		emailField.setWidth("100%");

		emailField.addBlurListener(e -> {
			validateEmailField();
		});
	}

	private void setupAndConfigurePhoneNumberField() {
		phoneNumberField = new TextField("Phone Number:");
		phoneNumberField.setRequiredIndicatorVisible(true);
		phoneNumberField.setWidth("100%");

		phoneNumberField.addBlurListener(e -> {
			validatePhoneNumberField();
		});
	}

	private void setupAndConfigureCostumerNameField() {
		costumerNameField = new TextField("Full Name:");
		costumerNameField.setRequiredIndicatorVisible(true);
		costumerNameField.setWidth("100%");

		costumerNameField.addBlurListener(e -> {
			validateCostumerNameField();
		});
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

		this.binder.forField(this.emailField).withValidator(new EmailValidator("Enter a valid email address."))
				.bind(Order::getEmail, Order::setEmail);

		this.binder.bind(this.fromAddressField, Order::getFromAddress, Order::setFromAddress);
		this.binder.bind(this.toAddressField, Order::getFromAddress, Order::setFromAddress);
		this.binder.bind(this.servicesCombo, Order::getService, Order::setService);
		this.binder.bind(this.textAreaField, Order::getDescription, Order::setDescription);
		this.binder.bind(this.dateField, Order::getDate, Order::setDate);
	}

	private boolean orderFormValidation() throws FormNotValidatedexception {
		boolean validated = false;

		if ((validatedCostumerNameField || validatedPhoneNumberField || validatedEmailField || validatedFromAddressField
				|| validatedToAddressField || validatedServicesComboField || validatedDateField) == false) {
			validated = false;
			throw new FormNotValidatedexception("Form not validated!");
		} else {
			validated = true;
		}
		return validated;
	}

	private boolean validateDateField() {
		if (dateField.getValue() == null) {
			dateField.setComponentError(new UserError("Please choose a valid date."));
			validatedDateField = false;
		} else {
			dateField.setComponentError(null);
			validatedDateField = true;
		}
		return validatedToAddressField;
	}

	private boolean validateServicesComboField() {
		if (servicesCombo.getValue() == null) {
			servicesCombo.setComponentError(new UserError("Please choose choose one of the services available."));
			validatedServicesComboField = false;
		} else {
			servicesCombo.setComponentError(null);
			validatedServicesComboField = true;
		}
		return validatedToAddressField;
	}

	private boolean validateToAddressField() {
		if (toAddressField.getValue().isEmpty()) {
			toAddressField.setComponentError(new UserError("Empty value is not allowed"));
			validatedToAddressField = false;
		} else {
			toAddressField.setComponentError(null);
			validatedToAddressField = true;
		}
		return validatedToAddressField;
	}

	private boolean validateFromAddressField() {
		if (fromAddressField.getValue().isEmpty()) {
			fromAddressField.setComponentError(new UserError("Empty value is not allowed"));
			validatedFromAddressField = false;
		} else {
			fromAddressField.setComponentError(null);
			validatedFromAddressField = true;
		}
		return validatedFromAddressField;
	}

	private boolean validateEmailField() {

		if (emailField.getValue().isEmpty()) {
			emailField.setComponentError(new UserError("Empty value is not allowed"));
			validatedEmailField = false;
		} else {
			emailField.setComponentError(null);
			validatedEmailField = true;
		}
		return validatedCostumerNameField;
	}

	private boolean validatePhoneNumberField() {

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

	private boolean validateCostumerNameField() {

		if (costumerNameField.getValue().isEmpty()) {
			costumerNameField.setComponentError(new UserError("Empty value not allowed"));
			validatedCostumerNameField = false;
		} else {
			costumerNameField.setComponentError(null);
			validatedCostumerNameField = true;
		}
		return validatedCostumerNameField;
	}

	public void saveOrder(Order order) {

		if (order != null) {
			repo.save(order);

		} else {
			try {
				if (orderFormValidation()) {
					Order newOrder = new Order(costumerNameField.getValue(),
							Integer.parseInt(phoneNumberField.getValue()), emailField.getValue(),
							fromAddressField.getValue(), toAddressField.getValue(), servicesCombo.getValue(),
							textAreaField.getValue(), dateField.getValue());
					repo.save(newOrder);
				}
			} catch (FormNotValidatedexception fnvex) {
				validateAllFields();
				Notification.show("Error", fnvex.getMessage(), Type.ERROR_MESSAGE);

			} catch (NumberFormatException nfex) {
				phoneNumberField.setComponentError(new UserError("Field contains invalid characters..."));
			}
		}
	}

	private void validateAllFields() {
		validateCostumerNameField();
		validatePhoneNumberField();
		validateEmailField();
		validateFromAddressField();
		validateToAddressField();
		validateServicesComboField();
		validateDateField();
	}

	public void setChangeHandler(ChangeHandler changeHandler) {
		saveOrderButton.addClickListener(e -> changeHandler.onChange());
		deleteOrderButton.addClickListener(e -> changeHandler.onChange());
	}

	public void clearFields() {
		costumerNameField.clear();
		costumerNameField.setComponentError(null);

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

	public void deleteOrder(Order order) {
		repo.deleteOrderById(order.getId());
		this.currentOrder = null;
		clearFields();
	}

}
