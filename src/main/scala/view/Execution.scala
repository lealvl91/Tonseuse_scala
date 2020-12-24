package view


import service.RecuperationInfo.RecupPelouseTondeuse
import service.Parcours_tondeuse.ParcoursTondeuse
import model.miseEnPlace.Tondeuse

import scala.io.Source


object Execution extends App {

  val fichier = "C:/Users/lealv/IdeaProjects/src/test/testFile.txt"
  var input = {
    val src = Source.fromFile(fichier).getLines().toList
    src
  }

  if (input != null) {
    val fileRecupInfo = new RecupPelouseTondeuse() //récupération des informations du fichier fournis
    val TestPelouse = fileRecupInfo.creationPelouse(input)
    TestPelouse match { //on vérifie que ce qu'on a récupérer correspond bien à la definition d'une pelouse
      case Some(pelouse) =>
        val tondeuses = fileRecupInfo.creationTondeuses(input)
        val commandes = fileRecupInfo.getCommandes(input)
        tondeuses.zipWithIndex.map { //création d'une paire avec index pour savoir quelle tondeuse on traite
          case (tondeuse, index) if commandes.length >= index + 1 => //Si on a bien une liste de commande
            val tondeuseService = new ParcoursTondeuse()
            //on utilise foldLeft() pour run consécutivement pour les 2 tondeuses
            val tondeuseFinale = commandes(index).foldLeft(tondeuse)((accumulator, command) => tondeuseService.mouvemenTondeuse(accumulator, pelouse, command))
            println(s"Position finale de la tondeuse numéro $index : ${tondeuseFinale.coordonnees.x} ${tondeuseFinale.coordonnees.y} ${tondeuseFinale.orientation}")
          case (tondeuse, index) => println(s"Position finale de la tondeuse numéro $index : ${tondeuse.coordonnees.x} ${tondeuse.coordonnees.y} ${tondeuse.orientation}") //si pas de liste de commande
        }
    }

  }





}