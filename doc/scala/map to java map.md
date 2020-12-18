```scala
val params: Map[String, String] = Map("k1" -> "v1", "k2" -> "v2")
// --convert scala map to java map
import scala.collection.JavaConverters.mapAsJavaMapConverter
val paramsJavaMap = params.map{ case (k, v) => k -> v }.asJava
```