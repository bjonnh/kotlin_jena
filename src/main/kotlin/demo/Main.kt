package demo

import org.apache.jena.sparql.engine.http.QueryEngineHTTP
import org.apache.jena.query.ResultSetFormatter
import org.apache.jena.query.QueryExecutionFactory
import org.apache.jena.query.QueryFactory


fun main(args: Array<String>) {
            println("Hello !")

            val queryStr = "select distinct ?Concept where {[] a ?Concept} LIMIT 10"
            val query = QueryFactory.create(queryStr)

            // Remote execution.
            try {
                QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query).use { qexec ->
                    // Set the DBpedia specific timeout.
                    println("Adding param !")
                    (qexec as QueryEngineHTTP).addParam("timeout", "10000")

                    // Execute.
                    println("ready to exec !")
                    val rs = qexec.execSelect()
                    println("ready to format !")
                    ResultSetFormatter.out(System.out, rs, query)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

}
