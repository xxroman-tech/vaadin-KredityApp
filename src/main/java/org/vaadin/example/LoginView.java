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
import com.vaadin.flow.component.select.Select;
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
	private HorizontalLayout menuForUser = new HorizontalLayout();
	private HorizontalLayout pridaniePredmetuLayout = new HorizontalLayout();
	private HorizontalLayout zmazaniePredmetuLayout = new HorizontalLayout();
	private KredityTable kredityTabulka;

	private Select<String> select = new Select<>();
	
	private TextField userInput = new TextField("Meno");
	private PasswordField passInput= new PasswordField("Heslo");
	//private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
	private Button najstButton = new Button("Zobraz kredity", event -> this.podariloSaPrihlasit());
	private Button spat = new Button("Späť", evnet -> this.vratSaSpat());
	private Button pridajPredmet = new Button("Pridaj predmet", event -> this.pridaniePredmetu());
	private Button zmazPredmet = new Button("Zmaž predmet", event -> this.mazaniePredmetu());

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
		userinterface.setAlignItems(Alignment.START);
		this.userinterface.setWidth("100%");
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
			Notification.show("Vitaj " + this.loginHelper.getUsername());
			this.zmazUserInterface();
			this.zmazFormulare();
			this.zobrazTabulkuInterface();
		}
	}
	
	private void zobrazMenuForUser() {
		this.menuForUser.setAlignItems(Alignment.END);
		this.menuForUser.setSizeFull();
		this.menuForUser.setJustifyContentMode(JustifyContentMode.START);
		
		this.zobrazUserInterfaceHladanie();
		this.menuForUser.add(this.select);
		
		this.userinterface.add(this.menuForUser);
	}
	
	private void zobrazUserInterfaceHladanie() {
		this.select.setLabel("Trieď poďla");
		this.select.setItems("Povinne vol.", "Povinné",
		  "Výberové");
		this.select.setEmptySelectionAllowed(true);
	}
	
	private void pridaniePredmetu() {
		this.pridaniePredmetuLayout.setAlignItems(Alignment.END);
		this.pridaniePredmetuLayout.setJustifyContentMode(JustifyContentMode.CENTER);
		this.pridaniePredmetuLayout.setMargin(false);
		this.pridaniePredmetuLayout.setWidth("100%");
		
		this.prihlasovacia.add(this.spat);
		this.zmazUserInterface();
		TextField cisloPredmetuInput = new TextField("Číslo");
		TextField nazovPredmetuInput = new TextField("Názov");
		
		Select<PovinnostPredmetu> povinnostPredmetuInput = new Select<>();
		povinnostPredmetuInput.setLabel("Povinnosť");
		povinnostPredmetuInput.setItems(PovinnostPredmetu.POVINNY, PovinnostPredmetu.POVINNEVOL, PovinnostPredmetu.VYBEROVY);
		
		TextField bodyInput = new TextField("Body");
		
		Select<String> znamkaInput = new Select<>();
		znamkaInput.setLabel("Známka");
		znamkaInput.setItems("A", "B", "C", "D", "E", "Fx");
		
		TextField kredityInput = new TextField("Kredity");
		
		this.pridaniePredmetuLayout.add(cisloPredmetuInput, nazovPredmetuInput, povinnostPredmetuInput
				, bodyInput, znamkaInput, kredityInput);
		this.userinterface.add(this.pridaniePredmetuLayout);
		
		VkladacPredemtov vkladac = new VkladacPredemtov(this.loginHelper.getLogger(), this.loginHelper.getUserIDDb());
		
		
		Button vlozPredmet = new Button("Vlož", event -> {
			if (vkladac.vkladaj(cisloPredmetuInput.getValue(), nazovPredmetuInput.getValue(), 
					this.getPovinnost(povinnostPredmetuInput), Integer.parseInt(bodyInput.getValue()), 
					this.getZnamka(znamkaInput), Integer.parseInt(kredityInput.getValue()))) {
				Notification.show("Predmet " + cisloPredmetuInput.getValue() + " bol pridaný");
			} else {
				Notification.show("Predmet " + cisloPredmetuInput.getValue() + " sa nepodarilo pridať");
			}
		});
		
		this.pridaniePredmetuLayout.add(vlozPredmet);
	}
	
	private void mazaniePredmetu() {
		this.zmazaniePredmetuLayout.setAlignItems(Alignment.END);
		this.zmazaniePredmetuLayout.setJustifyContentMode(JustifyContentMode.CENTER);
		this.zmazaniePredmetuLayout.setMargin(false);
		this.zmazaniePredmetuLayout.setWidth("100%");
		this.prihlasovacia.add(this.spat);
		
		this.zmazUserInterface();
		TextField cisloPredmetuInput = new TextField("Číslo");
		TextField kredityInput = new TextField("Kredity");
		
		this.zmazaniePredmetuLayout.add(cisloPredmetuInput, kredityInput);
		this.userinterface.add(this.zmazaniePredmetuLayout);
		
		MazacPredmetov mazac = new MazacPredmetov(this.loginHelper.getLogger(), this.loginHelper.getUserIDDb());
		
		Button zmazPredmetButton = new Button("Zmaž", event -> {
			if (mazac.zmaz(cisloPredmetuInput.getValue(), Integer.parseInt(kredityInput.getValue()))) {
				Notification.show("Predmet " + cisloPredmetuInput.getValue() + " bol vymazaný");
				this.vratSaSpat();
			} else {
				Notification.show("Predmet " + cisloPredmetuInput.getValue() + " sa nepodarilo vymazat");
			}
		});
		
		this.zmazaniePredmetuLayout.add(zmazPredmetButton);
	}
	
	private void zobrazTabulkuInterface() {
		this.nacitajData();
		this.pridajKredityText(new H3("Váš počet kreditov: " + this.citac.getPocetKreditov() + " -----> P.V.: " + this.citac.getPocetPovVolPredmetov()));
		if (this.citac.getPocetPredmetov() > 0) {
			this.zobrazMenuForUser();
			this.pridajTabulku();
			this.userinterface.add(this.pridajPredmet, this.zmazPredmet);
		} else {
			this.userinterface.add(this.pridajPredmet);
		} 
	}
	
	private void vratSaSpat() {
		this.prihlasovacia.remove(this.spat);
		this.zobrazTabulkuInterface();
	}
	
	private void zmazUserInterface() {
		this.userinterface.removeAll();
		this.pridaniePredmetuLayout.removeAll();
		this.zmazaniePredmetuLayout.removeAll();
	}
	
	private void zmazFormulare() {
		this.userInput.clear();
		this.passInput.clear();
	}
	
	private boolean autentifikacia(String username, String password) {
		return this.loginHelper.isLoggedIn(username, password);
	}
	
	private PovinnostPredmetu getPovinnost(Select<PovinnostPredmetu> select) {
		return select.getValue();
	}
	
	private String getZnamka(Select<String> select) {
		return select.getValue();
	}
}