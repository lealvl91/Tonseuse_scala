package service

//import des objets et classes dont on va avoir besoin
import model.miseEnPlace.{Manoeuvre, Pelouse, Tondeuse}
import _root_.model.miseEnPlace.Orientation
import _root_.model.miseEnPlace.Coordonnees


object Parcours_tondeuse extends App {

  class ParcoursTondeuse {

    /** on definit une fonction qui donne la nouvelle orientation de la tondeuse en fonction de son oriantation actuelle
     * Comme les instructions de manoeuvre peuvent comporter differents changements d'orientation à la suite,
     * on précise dans cette fonction seulement les possibilités d'orientation
     * exemple : Si la tondeuse est orientée N alors la tondeuse est orientée soit W soit E */

    def gaucheDroite(directionInitiale: Orientation.Value): (Orientation.Value, Orientation.Value) = directionInitiale match {
      case Orientation.N => (Orientation.W, Orientation.E)
      case Orientation.E => (Orientation.N, Orientation.S)
      case Orientation.S => (Orientation.E, Orientation.W)
      case Orientation.W => (Orientation.S, Orientation.N)
      case _ => (directionInitiale, directionInitiale)
    }

    /** On créer une fonction qui calcule les nouvelles coordonées de la tondeuse à partir :
     * --> de ses coordonées actuelles
     * --> de son oriententation
     * Exemple : si la tondeuse est orientée N : alors avancer de +1 case = la coordonnées Y augmente de +1 */

    def calculCoord(coordInitiale: Coordonnees, orientation: Orientation.Value): Coordonnees = coordInitiale match {
      case Orientation.N => coordInitiale.copy(y = coordInitiale.y + 1)
      case Orientation.E => coordInitiale.copy(x = coordInitiale.x + 1)
      case Orientation.S => coordInitiale.copy(y = coordInitiale.y - 1)
      case Orientation.W => coordInitiale.copy(x = coordInitiale.x - 1)
      case _ => coordInitiale
    }

    /** on définit une fonction qui prends en entrée :
     * --> un objet tondeuse (postion + orientation)
     * --> un objet pelouse (limite du terrain)
     * --> un objet manoeuvre (suite d'instructions d'exploration fournis a la tondeuse)
     * Cette fonction permet de connaitre la position et l'orientation finale de la tondeuse
     * après execution des instructions (= un nouvel objet tondeuse) grace aux fonctions crées ci-dessus */

    def mouvemenTondeuse(tondeuseInitiale: Tondeuse, pelouse: Pelouse, manoeuvre: Manoeuvre.Value): Tondeuse = {
      manoeuvre match {
        //Choix de l'orientation en fonction de l'instruction (D ou G)
        case Manoeuvre.G => tondeuseInitiale.copy(orientation = gaucheDroite(tondeuseInitiale.orientation)._1)
        case Manoeuvre.D => tondeuseInitiale.copy(orientation = gaucheDroite(tondeuseInitiale.orientation)._2)
        // Calcul des coordonnées en cas d'avancée de + 1 case
        case Manoeuvre.A => {
          //création des nouveaux coordonnées de la tondeuse
          val nxvCoordonnees = calculCoord(tondeuseInitiale.coordonnees, tondeuseInitiale.orientation)
          //On vérifie que les coordonnées ne soient pas en dehors des limites de la pelouse
          if (nxvCoordonnees.x >= 0 && nxvCoordonnees.y >= 0 && nxvCoordonnees.x <= pelouse.superieurDroit.x && nxvCoordonnees.y <= pelouse.superieurDroit.y)
            tondeuseInitiale.copy(coordonnees = nxvCoordonnees) else tondeuseInitiale
        }
        case _ => tondeuseInitiale
      }
    }
  }

}