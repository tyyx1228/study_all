package com.ty.study.cases.streaming

import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object UrlCount {

  val updateFunc = (iterator: Iterator[(String, Seq[Int], Option[Int])]) => {
    iterator.flatMap{case(x,y,z)=> Some(y.sum + z.getOrElse(0)).map(n=>(x, n))}
  }

  def main(args: Array[String]) {
    //接收命令行中的参数
    val Array(zkQuorum, groupId, topics, numThreads, hdfs) = args
    //创建SparkConf并设置AppName
    val conf = new SparkConf().setAppName("UrlCount")
    //创建StreamingContext
    val ssc = new StreamingContext(conf, Seconds(2))
    //设置检查点
    ssc.checkpoint(hdfs)
    //设置topic信息
    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    //重Kafka中拉取数据创建DStream
    val lines = KafkaUtils.createStream(ssc, zkQuorum ,groupId, topicMap, StorageLevel.MEMORY_AND_DISK).map(_._2)
    //切分数据，截取用户点击的url
    val urls = lines.map(x=>(x.split(" ")(6), 1))
    //统计URL点击量
    val result = urls.updateStateByKey(updateFunc, new HashPartitioner(ssc.sparkContext.defaultParallelism), true)
    //将结果打印到控制台
    result.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
