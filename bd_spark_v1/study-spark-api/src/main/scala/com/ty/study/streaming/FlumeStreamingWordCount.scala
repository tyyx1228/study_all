package com.ty.study.streaming

import java.net.InetSocketAddress

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.flume.{FlumeUtils, SparkFlumeEvent}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by tongyu on 2016/8/26.
  */
object FlumeStreamingWordCount {

  def main(args: Array[String]) {
    LoggerLevels.setStreamingLogLevels()
    val conf = new SparkConf().setAppName("FlumeStreamingWordCount").setMaster("local[8]")
    //创建StreamingContext并设置产生批次的间隔时间
    val ssc = new StreamingContext(conf, Seconds(2))
    //从Socket端口中创建RDD
    val flumeStream: ReceiverInputDStream[SparkFlumeEvent] = FlumeUtils.createPollingStream(ssc, Array(new InetSocketAddress("hdp10", 8888)), StorageLevel.MEMORY_AND_DISK)
    //去取Flume中的数据
    val words = flumeStream.flatMap(x => new String(x.event.getBody().array()).split(" "))
    val wordAndOne: DStream[(String, Int)] = words.map((_, 1))
    val result: DStream[(String, Int)] = wordAndOne.reduceByKey(_+_)
    //打印
    result.print()
    //开启程序
    ssc.start()
    //等待结束
    ssc.awaitTermination()
  }
}
