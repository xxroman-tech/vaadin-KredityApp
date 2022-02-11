package org.vaadin.example;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "")
@PageTitle("Login | Vaadin CRM")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	private final LoginForm login = new LoginForm();
	private Login loginHelper = new Login();

	public LoginView() {
		addClassName("login-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		
		this.podariloSaPrihlasit();
		

		add(new H1("Kredity App"), login);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// inform the user about an authentication error
		if(beforeEnterEvent.getLocation()
        .getQueryParameters()
        .getParameters()
        .containsKey("error")) {
            login.setError(true);
        }
	}
	
	private void podariloSaPrihlasit() {
		login.addLoginListener(event ->{
			if (this.loginHelper.isLoggedIn(event.getUsername(), event.getPassword())) {
				UI.getCurrent().navigate(UserView.class);
				Notification.show("Boli ste úspešne prihlásený");
			} else {
				Notification.show("Zle prihlasenie!");
				UI.getCurrent().getPage().reload();
			}
		});
	}
}