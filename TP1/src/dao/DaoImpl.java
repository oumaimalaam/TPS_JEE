package dao;
// implementer une interface => Heriter d'une interface les methodes abstraites
// quand je dis je vais cree une implementation => je pense au future, il se peut q'au future qu'on aura besoin d'autres implementations
// je vais cree l'implementation => je pense au present
public class DaoImpl implements IDao{

    @Override
    public double getData() {
        /*
        Se connecter a la BD pour recuperer la temperature
        */
        System.out.println("Version 1");
        double temp=Math.random()*40;
        return temp;
    }
}
