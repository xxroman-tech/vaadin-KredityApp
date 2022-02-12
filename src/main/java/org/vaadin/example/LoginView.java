package org.vaadin.example;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "")
@PageTitle("KredityApp")
public class LoginView extends VerticalLayout { 
	
	private Login loginHelper = new Login();
    private CitacPredmetov citac;
    private KredityTable kredityTable;

	private HorizontalLayout prihlasovacia = new HorizontalLayout();
	private VerticalLayout userinterface = new VerticalLayout();
	private KredityTable kredityTabulka;
	
	private TextField userInput = new TextField("Meno");
	private PasswordField passInput= new PasswordField("Heslo");
	//private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
	private Button najstButton = new Button("Zobraz kredity", event -> this.podariloSaPrihlasit());

	public LoginView() {
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		
		this.zobrazPrihlasovaciuCast();
		this.zobrazUserInterface();
	}
	
	private void zobrazPrihlasovaciuCast() {
		prihlasovacia.add(this.userInput, this.passInput, this.najstButton);
		prihlasovacia.setAlignItems(Alignment.END);
		prihlasovacia.setJustifyContentMode(JustifyContentMode.CENTER);
		add(prihlasovacia);
	}
	
	private void zobrazUserInterface() {
		userinterface.setAlignItems(Alignment.CENTER);
		userinterface.setJustifyContentMode(JustifyContentMode.CENTER);
		add(userinterface);
	}
	
	private void pridajKredityText(H3 text) {
		userinterface.removeAll();
		userinterface.add(text);
	}
	
	private void pridajTabulku() {
		this.kredityTable = new KredityTable(this.citac);
		this.userinterface.add(this.kredityTable.getGrid());
	}
	
	private void nacitajData() {
		this.citac = new CitacPredmetov(this.loginHelper.getLogger(), this.loginHelper.getUserIDDb());
	}
	
	private void podariloSaPrihlasit() {
		if (this.autentifikacia(this.userInput.getValue(), this.passInput.getValue())) {
			this.zmazFormulare();
			this.nacitajData();
			this.pridajKredityText(new H3("Váš počet kreditov: " + this.citac.getPocetKreditov()));
			this.pridajTabulku();
		}
	}
	
	private void zmazFormulare() {
		this.userInput.clear();
		this.passInput.clear();
	}
	
	private boolean autentifikacia(String username, String password) {
		return this.loginHelper.isLoggedIn(username, password);
	}
}