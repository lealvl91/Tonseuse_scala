package model

object miseEnPlace extends App {
/**   CONSIGNE :
           N
           |
      W -------- E
           |
           S

 D = 90° à droite
 G = 90° à gauche
 A = la tondeuse avance d'une case dans sa direction

 y^                     (x,y)
  |
  |
  |
  |
  |
  |(0,0)
  ---------------------------> x*/

/** Enumeration est une alternative aux case class, représente un grp de constantes nommées,
 * permet de ne pas avoir une classe par membre */

  object Orientation extends Enumeration {
    val N, E, W, S = Value
  }

  object Manoeuvre extends Enumeration {
    val D, G, A = Value
  }

  /** Class "coord" = taille du rectangle de la pelouse:
   * --> x = longueur
   * --> y = hauteur
   * Ces attributs sont choisi par l'utilisateur en début de programme afin de construire l'objet pelouse
   * Le coin supérieur droit à donc pour coordonnées : (x,y)
   * le coin inférieur gauche :(0,0) */

  case class Coordonnees (x: Int, y: Int)
  case class Pelouse (superieurDroit: Coordonnees)

  /** La tondeuse est définie par :
   * sa position et son orientation (3,2,N) par exemple
   * les instructions d'exploration (suite de D,G et A) */

  case class Tondeuse (coordonnees: Coordonnees, orientation: Orientation.Value) {
    def print = println( this.coordonnees.x + " " + this.coordonnees.y + " " + this.orientation)
  }
}