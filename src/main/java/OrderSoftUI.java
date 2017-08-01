

import org.springframework.beans.factory.annotation.Autowired;

import com.example.views.OrderFormLayout;
import com.example.views.OrdersLayout;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.UI;

@SpringUI
@Theme("valo")
public class OrderSoftUI extends UI {

	private HorizontalSplitPanel splitPanel;
	
	@Autowired
	OrdersLayout ordersLayout;
	
	@Autowired
	OrderFormLayout orderForm;
	
	@Override
	protected void init(VaadinRequest request) {
		splitPanel = new HorizontalSplitPanel();
		splitPanel.setWidth("100%");
		
		ordersLayout.getGrid().asSingleSelect().addValueChangeListener(evt -> {
			orderForm.setOrder(evt.getValue());
		});
		ordersLayout.update();
		
		splitPanel.setFirstComponent(ordersLayout);
		orderForm.setWidth("80%");
		splitPanel.setSecondComponent(orderForm);		
		
		setContent(splitPanel);
		ordersLayout.update();
	}

}
