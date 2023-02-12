package pres;
import dao.IDao;
import metier.IMetier;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;
// Instanciation dynamique
public class Presentation2 {
    public static void main(String[] args) throws Exception{
        Scanner scanner=new Scanner(new File("config.txt"));
        String daoClasseName=scanner.nextLine();
        /* Forname=> il chercher le nom de la classe
        au moment de l'execution.
        Si la classe existe il va charger le byte
        code en memoire.
        en java, toutes les classes et les interface
         qu'on utilse sont chargees sous forme
         d'un objet de type Classe.
         Si la classe n'existe pas il va generer une exception
         ClasseNotFoundException
        */
        Class cDao=Class.forName(daoClasseName);
        // il faut instancier la classe
        IDao dao=(IDao) cDao.newInstance();
        String metierClasseName=scanner.nextLine();
        Class cMetier=Class.forName(metierClasseName);
        // il faut instancier la classe
        IMetier metier=(IMetier) cMetier.newInstance();
        Method method=cMetier.getMethod("setDao", IDao.class);
        //metier.setDao(dao);
        method.invoke(metier,dao);
        System.out.println("Resultat=>"+metier.calcul());
    }
}
