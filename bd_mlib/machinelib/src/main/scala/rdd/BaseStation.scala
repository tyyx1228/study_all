package rdd

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2016-5-11.
  */
object BaseStation {


  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("BaseStation")
    sparkConf.setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val file = sc.textFile("x:/aggregate_flickering.txt")
    val arr = file.collect()
    for(line<-arr){
      println(line)
    }



  }

}
