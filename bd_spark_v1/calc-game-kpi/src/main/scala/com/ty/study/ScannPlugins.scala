package com.ty.study


import kafka.serializer.StringDecoder
import org.apache.commons.lang3.time.FastDateFormat
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Milliseconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by root on 2016/5/24.
  */
object ScannPlugins {

  def main(args: Array[String]) {
    val Array(zkQuorum, group, topics, numThreads) = Array("node-1.itcast.cn:2181,node-2.itcast.cn:2181,node-3.itcast.cn:2181", "g0", "gamelog", "1")
    val dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss")
    val conf = new SparkConf().setAppName("ScannPlugins").setMaster("local[4]")
    val sc = new SparkContext(conf)
    //C产生数据批次的时间间隔10s
    val ssc = new StreamingContext(sc, Milliseconds(10000))
    //如果想要在集群中运行该程序，CheckpointDir设置一个共享存储的目录：HDFS
    sc.setCheckpointDir("c://ck0710")

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val kafkaParams = Map[String, String](
      "zookeeper.connect" -> zkQuorum,
      "group.id" -> group,
      "auto.offset.reset" -> "smallest"
    )
    val dstream: ReceiverInputDStream[(String, String)] = KafkaUtils.createStream(ssc, kafkaParams, topicMap, StorageLevel.MEMORY_AND_DISK_SER)
    //取出kafka的内容
    val lines = dstream.map(_._2)
    //用制表符切分数据
    val splitedLines = lines.map(_.split("\t"))

    val filteredLines = splitedLines.filter(f => {
      val et = f(3)
      val item = f(8)
      et == "11" && item == "强效太阳水"
    })

    val userAndTime: DStream[(String, Long)] = filteredLines.map(f => (f(7), dateFormat.parse(f(12)).getTime))

    //安装时间窗口进行分组
    val grouedWindow: DStream[(String, Iterable[Long])] = userAndTime.groupByKeyAndWindow(Milliseconds(30000), Milliseconds(20000))
    val filtered: DStream[(String, Iterable[Long])] = grouedWindow.filter(_._2.size >= 5)

    val itemAvgTime = filtered.mapValues(it => {
      val list = it.toList.sorted
      val size = list.size
      val first = list(0)
      val last = list(size - 1)
      val cha: Double = last - first
      cha / size
    })

    val badUser: DStream[(String, Double)] = itemAvgTime.filter(_._2 < 10000)

    badUser.foreachRDD(rdd => {
      rdd.foreachPartition(it => {
//        val connection = JedisConnectionPool.getConnection()
//        it.foreach(t => {
//          val user = t._1
//          val avgTime = t._2
//          val currentTime = System.currentTimeMillis()
//          connection.set(user + "_" + currentTime, avgTime.toString)
//        })
//        connection.close()

        it.foreach(t => {
          println(t._1)
        })
      })
    })



    //filteredLines.print()

    ssc.start()
    ssc.awaitTermination()

  }

}
