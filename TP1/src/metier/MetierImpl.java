package metier;

import dao.IDao;

public class MetierImpl implements IMetier{
    // Couplage faible
    // il y a pas de =new, car on va faire appel a une classe
    // si on utiliser une classe c'est du couplage fort
    private IDao dao;

    @Override
    public double calcul() {
        double tmp=dao.getData();
        return tmp*540/Math.cos(tmp*Math.PI);
    }
  /*
    Permet d'injecter dans la variable dao un objet
    d'une classe qui implement l'interface IDao
   */
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
