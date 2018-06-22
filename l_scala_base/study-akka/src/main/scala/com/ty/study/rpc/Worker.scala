package com.ty.study.rpc

import java.util.UUID

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

/**
  * Created by tongyu on 2016/8/18.
  */
class Worker(val cores: Int, val memory: Int, val masterHost: String, val masterPort: Int) extends Actor{

  //Master的引用
  var master: ActorSelection = _
  //Worker的ID
  val workerId = UUID.randomUUID().toString
  //masterUrl
  var masterUrl: String = _

  val HEARTBEAT_INTERVAL = 10000

  //preStart在构造器之后receive之前执行
  override def preStart(): Unit = {
    //首先跟Master建立连接
    master = context.actorSelection(s"akka.tcp://${Master.MASTER_SYSTEM}@$masterHost:$masterPort/user/${Master.MASTER_NAME}")
    //通过master的引用向Master发送注册消息
    master ! RegisterWorker(workerId, cores, memory)
  }

  override def receive: Receive = {
    //Master发送给Worker注册成功的消息
    case RegisteredWorker(masterUrl) => {
      this.masterUrl = masterUrl
      //启动定时任务，向Master发送心跳
      //导入隐式转换
      import context.dispatcher
      context.system.scheduler.schedule(0 millis, HEARTBEAT_INTERVAL millis, self, SendHeartbeat)
    }

    case SendHeartbeat => {
      //向Master发送心跳
      master ! Heartbeat(workerId)
    }
  }
}

object Worker {
  def main(args: Array[String]) {

    //Worker的地址和端口
    val host = args(0)
    val port = args(1).toInt
    val cores = args(2).toInt
    val memory = args(3).toInt
    //Master的地址和端口
    val masterHost = args(4)
    val masterPort = args(5).toInt

    val confStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    val conf = ConfigFactory.parseString(confStr)
    //单例的ActorSystem
    val actorSystem = ActorSystem("WorkerActorSystem", conf)
    //通过actorSystem来创建Actor
    val worker = actorSystem.actorOf(Props(new Worker(cores, memory, masterHost, masterPort)), "Worker")
    actorSystem.awaitTermination()
  }
}
