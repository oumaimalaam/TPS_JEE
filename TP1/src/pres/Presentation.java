package pres;

import dao.DaoImpl;
import ext.DaoImpl2;
import metier.MetierImpl;
// c'est la ou on fabrique des objets
public class Presentation {
    public static void main(String[] args) {
        /*
        Injenction des dépendaces par
        instanciation statique => new
        new est le vrai probleme de la maintenece
        * */
        DaoImpl2 dao=new DaoImpl2();
        MetierImpl metier=new MetierImpl();
        metier.setDao(dao);
        System.out.println("Resultat = "+ metier.calcul());
    }
}
