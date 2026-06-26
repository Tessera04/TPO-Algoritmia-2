package main;

import servicios.GestorTickets;
import ui.Menu;

public class Main {
    public static void main(String[] args) {
        GestorTickets gestor = new GestorTickets(); //Contiene los TDA's
        Menu menu = new Menu(gestor); //Contiene la UI del sistema
        menu.iniciar(); //Inicia el sistema completo
    }
}