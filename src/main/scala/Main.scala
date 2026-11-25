// =====================================================================
// Ejercicio 6: Integración del sistema completo
// =====================================================================

object Main {
  def main(args: Array[String]): Unit = {
    // ------------------------------------------------------------------
    // Paso 1: Cargar diccionarios
    // ------------------------------------------------------------------
     val dictionary: List[NamedEntity] = Dictionary.loadAll()

     println(s"Diccionario cargado: ${dictionary.size} entidades.\n")

    // ------------------------------------------------------------------
    // Paso 2: Descargar posts
    // ------------------------------------------------------------------
     val subscriptions = FileIO.readSubscriptions()

     val allPosts: List[(String, List[String])] = subscriptions.map { url =>
      println(s"Descargando posts de: $url")
      val json   = FileIO.downloadFeed(url)
      val titles = FileIO.extractPostTitles(json)
     (url, titles)
    }

    // ------------------------------------------------------------------
    // Paso 3: Detectar entidades y mostrar resultados por post
    // ------------------------------------------------------------------
    allPosts.foreach { case (url, titles) =>
      titles.foreach { title => 
        val entities = Analyzer.detectEntities(title, dictionary)
        val format = Formatters.formatNERResult(title, entities)
        println(format)
      }
    }

    // ------------------------------------------------------------------
    // Paso 4: Estadísticas globales
    // ------------------------------------------------------------------
    val allEntities = allPosts.flatMap { case (url, titles) => 
      titles.flatMap{ title => 
        Analyzer.detectEntities(title, dictionary)
      }
    }
    val count = Analyzer.countByType(allEntities)
    println(Formatters.formatEntityStats(count))
  }
}
