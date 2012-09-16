package cas2.model;

// Pour les JComboBox, on veux y ajouter des objets
// comme des Harbor, mais on veux pas qu'il affiche
// le toString() de Harbor qui n'est pas adapté à l'utilisateur
// (on veux quand même garder le toString() généré par eclipse
public interface UserEditable {
    public abstract String toStringForUsers();
}
