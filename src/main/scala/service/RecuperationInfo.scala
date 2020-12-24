package service

import java.io.File
import scala.io.Source

import model.miseEnPlace.{Manoeuvre, Pelouse, Tondeuse}
import _root_.model.miseEnPlace.Orientation
import _root_.model.miseEnPlace.Coordonnees

/**
 * Il faut maintenant faire en sorte que le programme execute les informations qu'on lui donne en entreés:
 * La taille de la pelouse
 * La position de la tondeuse
 * La suite de manoeuvre d'exploration que la tondeuse doit effectuer
 *
 * Ces informations sont données ligne par ligne via un fichier texte test
 */
object RecuperationInfo extends App {

  class RecupPelouseTondeuse {

    //création d'une fonction qui récupère la taille de la pelouse (ligne 0 du fichier texte de test)
    def creationPelouse(list: List[String]): Option[Pelouse] = {
      val input = list
      if (input.nonEmpty) {
        val coord = input(0).split(" ")
        // on vérifie que les coordonnées de la pelouse se composent bien de deux chiffres puis on les récupèrent dans le array coord
        if (coord.length != 2) None else Some(Pelouse(Coordonnees(coord(0).toInt, coord(1).toInt)))
      } else None
    }

    //Ensuite on récupère la position et l'orientation des 2 objets tondeuse (ligne 1 et 3 du fichier test --> lignes impaires)
    def creationTondeuses(list: List[String]): List[Tondeuse] = {
      val input = list
      if (input.nonEmpty && input.length > 1) {
        val infoTondeuse = input.zipWithIndex.filter {
          // on prends uniquement les lignes impaires (et pas la ligne 0)
          case (item, index) => index != 0 && index % 2 != 0
        }.map(_._1)
        infoTondeuse.filter(_.split(" ").length == 3).map(ligne => {
          val ParamTondeuse = ligne.split(" ")
          //On met les parametres dans les objets correspondant et sous le format correspondant
          Tondeuse(Coordonnees(ParamTondeuse(0).toInt, ParamTondeuse(1).toInt), Orientation.withName(ParamTondeuse(2)))
        })
      } else List.empty[Tondeuse]
    }

    //Enfin on récupère les instruction de manoeuvre des deux tondeuses sur les ligne paires
    def getCommandes(list: List[String]): List[List[Manoeuvre.Value]] = {
      val input = list
      if (input.nonEmpty && input.length > 2) {
        val manoeuvreLines = input.zipWithIndex.filter {
          case (item, index) => index != 0 && index % 2 == 0
        }.map(_._1)
        manoeuvreLines.map(ligne => ligne.toList.map(mnv => Manoeuvre.withName(mnv.toString)))
      } else List.empty[List[Manoeuvre.Value]]
    }
  }

}