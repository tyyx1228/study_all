package com.ty.study.tags

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object MergeUserTag {

  def main(args: Array[String]) {

    if (args.length < 3) {
      println(" Usage: <userMarkFile> <userIDFile>  <savePath> ")
      System.exit(1)
    }

    val Array(userMarkInputFile, userIDInputFile, saveResultPath) = args

    val sparkConf = new SparkConf().setAppName("MergeUserTag").setMaster("local")
    val sc = new SparkContext(sparkConf)
    val userMarkData = sc.textFile(userMarkInputFile)
    val userUniqueData = sc.textFile(userIDInputFile)


    val userMark = userMarkData
      .map(
        line => {
          val userMarks = line.split("\t", line.length)
          var tagValue = scala.collection.mutable.Map[String, Int]()
          (1 until (userMarks.length)).foreach {
            index => {
              if (index % 2 != 0) {
                tagValue += (userMarks(index) -> userMarks(index + 1).toInt)
              }
            }
          }
          (userMarks(0), tagValue.toSeq)
        }
      )

    val discUser = userUniqueData.map(line => line.split("\t", line.length)).cache()
    val URDD = discUser.flatMap(
      t => t.slice(1, t.length).filter(!_.startsWith("UID:")).map(k => (k, t(0)))
    )

    URDD.join(userMark).map {
      x => (x._2._1, x._2._2)
    }.reduceByKey(_ ++ _).map {
      x =>
        (x._1, x._2.groupBy(_._1).map {
          x =>
            x._1 + ":" + x._2.map(
              _._2.toInt
            ).sum.toString
        }.toList)
    }.saveAsTextFile(saveResultPath)
    sc.stop()
  }

}
