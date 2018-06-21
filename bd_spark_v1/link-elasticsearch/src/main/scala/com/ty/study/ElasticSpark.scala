package com.ty.study

import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.spark._

/**
  *
  */
object ElasticSpark {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("ElasticSpark").setMaster("local")
    conf.set("es.nodes", "172.16.0.14,172.16.0.15,172.16.0.16")
    conf.set("es.port", "9200")
    conf.set("es.index.auto.create", "true")
    val sc = new SparkContext(conf)
    //val query: String = "{\"query\":{\"match_all\":{}}}"
    val start = 1472290002
    val end = 1472290047
    //    val query: String =
    //      s"""{
    //       "query": {"match_all": {}},
    //       "filter": {
    //         "bool": {
    //           "must": {
    //             "range": {
    //               "access.time": {
    //                 "gte": "$start",
    //                 "lte": "$end"
    //               }
    //             }
    //           }
    //         }
    //       }
    //     }"""

    val tp = 1
    val query: String =
      s"""{
       "query": {"match_all": {}},
       "filter" : {
          "bool": {
            "must": [
                {"term" : {"access.type" : $tp}},
                {
                "range": {
                  "access.time": {
                  "gte": "$start",
                  "lte": "$end"
                  }
                }
              }
            ]
          }
       }
     }"""
    val rdd1 = sc.esRDD("accesslog", query)

    println(rdd1.collect().toBuffer)
    println(rdd1.collect().size)
  }
}

