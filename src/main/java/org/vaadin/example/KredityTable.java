package org.vaadin.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mysql.cj.xdevapi.Table;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class KredityTable {
    private HashMap<String, Predmet> zoznamPredmetov;
    private Grid<Predmet> grid = new Grid<Predmet>();
	
	public KredityTable(CitacPredmetov citac) {
		
       	this.zoznamPredmetov = new HashMap<>(citac.getZoznamPredmetov());
       	
       	List <Predmet> predmety = this.zoznamPredmetov.values().stream().collect(Collectors.toList());
       	
       	System.out.println(predmety.get(0).toString());
        
        this.grid.setItems(predmety);
        
        this.grid.addColumn(Predmet::getCislo).setHeader("Číslo predmetu");
        this.grid.addColumn(Predmet::getNazov).setHeader("Názov");
        this.grid.addColumn(Predmet::getPovinnost).setHeader("Povinnosť");
        this.grid.addColumn(Predmet::getBody).setHeader("Body");
        this.grid.addColumn(Predmet::getZnamka).setHeader("Známka");
        this.grid.addColumn(Predmet::getKredity).setHeader("Kredity");
        
        this.grid.setWidth("100%");
        this.grid.setHeight("75vh");
        this.grid.setWidthFull();
	}
	
	public Grid getGrid() {
		return this.grid;
	}
}