package com.ty.study.tags

import org.apache.spark.{SparkConf, SparkContext}

object MakeTags {

  def main(args: Array[String]) {

    if (args.length != 2) {
      println("Usage:MakeTags <inputPath> <outputPath>")
      System.exit(1)
    }
    //    val Array(inputPath, outputPath) = args
    val inputPath = "/home/hadoop/test/itcast/log.txt"
    val outputPath = "/home/hadoop/test/itcast/out"

    val conf = new SparkConf().setAppName("MakeTags").setMaster("local")
    val sc = new SparkContext(conf)
    sc.textFile(inputPath).map(_.split(",", 20))
      .map(log => (getIdentifyId(log(17), log(18), log(19), log(16), log(15)), make(log(4), log(5), log(14), log(13), log(12)).toList))
      .filter(a => a._1.length > 0 && a._2.length > 0)
      .reduceByKey(_ ++ _)
      .map {
        x =>
          x._1 + "\t" + x._2.groupBy(_._1).map {
            x =>
              x._1 + "\t" + x._2.map(_._2.toInt).sum
          }.mkString("\t")
      }
      .saveAsTextFile(outputPath)
    sc.stop()
  }

  // 获取一个识别id
  def getIdentifyId(idfa: String, openudid: String, androidid: String, mac: String, imei: String): String = {
    if (idfa.nonEmpty && (idfa ne "")) {
      "IDFA:" + idfa.replaceAll(":|-", "").toUpperCase
    } else if (openudid.nonEmpty && (openudid ne "")) {
      "OPENUDID:" + openudid.toUpperCase
    } else if (androidid.nonEmpty && (androidid ne "")) {
      "ANDROIDID:" + androidid.toUpperCase
    } else if (mac.nonEmpty && (mac ne "")) {
      "MAC:" + mac.replaceAll(":|-", "").toUpperCase
    } else if (imei.nonEmpty && (imei ne "")) {
      "IMEI:" + mac.replaceAll(":|-", "").toUpperCase
    } else {
      ""
    }
  }

  def make(appid: String, appname: String, apptype: String, networkmannername: String, ispname: String): Map[String, Int] = {
    val appNameTags = Tags4AppName.make(appid, appname, apptype)
    val netBehaviorTags = Tags4NetBehavior.make(networkmannername, ispname)
    netBehaviorTags ++ appNameTags
  }

}

