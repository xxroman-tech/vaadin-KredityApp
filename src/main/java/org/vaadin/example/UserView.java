package org.vaadin.example;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("user")
public class UserView extends VerticalLayout{
	
	private Button logOut = new Button("Odhlásiť", event -> {
		UI.getCurrent().navigate(LoginView.class);
		this.odhlasMa();
	});
	
	public UserView() {
		this.naplnView();
		
	}
	
	private void naplnView() {
		add(new H1("Vitaj"));
		
		this.logOut.setAutofocus(true);
		this.logOut.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		add(this.logOut);
	}
	
	private void odhlasMa() {
		Notification.show("Boli ste odhlásený");
	}
	
}