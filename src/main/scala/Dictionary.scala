// =====================================================================
// Ejercicio 2: Cargar diccionarios de entidades
// =====================================================================

/**
 * Responsable de cargar colecciones de entidades nombradas desde archivos.
 *
 * Un diccionario es un archivo de texto plano donde cada línea contiene
 * el nombre de una entidad conocida del mismo tipo.
 *
 * Ejemplo — data/people.txt:
 *   Martin Odersky
 *   Alan Turing
 *   Ada Lovelace
 *
 * Ejemplo — data/languages.txt:
 *   Scala
 *   Python
 *   Haskell
 */

import scala.io.Source
import scala.util.Try
object Dictionary {

  /**
   * Lee un archivo de diccionario y crea una lista de entidades del tipo indicado.
   *
   * @param filePath   ruta al archivo de diccionario (ej: "data/people.txt")
   * @param entityType tipo de entidad: "Person", "University", "ProgrammingLanguage", etc.
   * @return lista de NamedEntity del tipo correspondiente
   *
   * TODO (Ejercicio 2): Implementar este método.
   *
   *   Pasos sugeridos:
   *     1. Leer las líneas del archivo
   *     2. Para cada línea, crear la instancia de la clase correcta
   *     3. Retornar la lista de entidades creadas
   *
   *   Para crear la clase correcta según el tipo se puede usar match:
   *
   */
  def loadFromFile(filePath: String, entityType: String): List[NamedEntity] = {
    val lines: List[String] = Try {
      val source = Source.fromFile(filePath)
      val content = source.getLines.toList
      source.close()
      content
    }.getOrElse(List())
    lines
      .map(_.trim)
      .filter(_.nonEmpty)
      .map { text => 
        entityType match {
          case "Person" => new Person(text)
          case "Organization" => new Organization(text)
          case "University" => new University(text)
          case "Place" => new Place(text)
          case "Technology" => new Technology(text)
          case "ProgrammingLanguage" => new ProgrammingLanguage(text)
          case _ => sys.error("Error: entity type does not exist")
        }
      }
  }

  /**
   * Carga todos los diccionarios disponibles y combina sus entidades.
   *
   * @return lista con todas las entidades de todos los diccionarios
   *
   */
  def loadAll(): List[NamedEntity] = {
    loadFromFile("data/people.txt", "Person") :::
    loadFromFile("data/universities.txt", "University") :::
    loadFromFile("data/languages.txt ", "ProgrammingLanguage") :::
    loadFromFile("data/organizations.txt", "Organization") :::
    loadFromFile("data/places.txt", "Place") 
  }
}
