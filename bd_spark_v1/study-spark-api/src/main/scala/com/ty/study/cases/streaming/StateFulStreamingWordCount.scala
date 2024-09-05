package com.ty.study.cases.streaming

import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by tongyu on 2016/8/26.
  */
object StateFulStreamingWordCount {

  val updateFunc = (it : Iterator[(String, Seq[Int], Option[Int])]) => {
    //it.map(t => (t._1, t._2.sum + t._3.getOrElse(0)))
    it.map{case(x, y, z) => (x, y.sum + z.getOrElse(0))}
  }

  def main(args: Array[String]) {
    LoggerLevels.setStreamingLogLevels()
    val conf = new SparkConf().setAppName("StateFulStreamingWordCount").setMaster("local[2]")
    //创建StreamingContext并设置产生批次的间隔时间
    val ssc = new StreamingContext(conf, Seconds(5))
    //设置ck目录
    ssc.checkpoint("c://ck0826")
    //从Socket端口中创建RDD
    val lines: ReceiverInputDStream[String] = ssc.socketTextStream("172.16.0.201", 8888)
    val words: DStream[String] = lines.flatMap(_.split(" "))
    val wordAndOne: DStream[(String, Int)] = words.map((_, 1))
    val result: DStream[(String, Int)] = wordAndOne.updateStateByKey(updateFunc, new HashPartitioner(ssc.sparkContext.defaultParallelism), true)
    //打印
    result.print()
    //开启程序
    ssc.start()
    //等待结束
    ssc.awaitTermination()
  }
}
