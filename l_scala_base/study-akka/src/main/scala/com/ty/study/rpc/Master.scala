package com.ty.study.rpc

import akka.actor.{Actor, ActorSystem, Props}
import akka.actor.Actor.Receive
import com.typesafe.config.ConfigFactory

import scala.collection.mutable

import scala.concurrent.duration._

/**
  * Created by tongyu on 2016/8/18.
  */
class Master(val host: String, val port: Int) extends Actor{

  //保存WorkerID 到 WorkerInfo的映射
  val idToWorker = new mutable.HashMap[String, WorkerInfo]()
  //保存所的WorkerInfo信息
  val workers = new mutable.HashSet[WorkerInfo]()

  val CHECK_INTERVAL = 15000

  override def preStart(): Unit = {
    //导入隐式转换
    import context.dispatcher
    context.system.scheduler.schedule(0 millis, CHECK_INTERVAL millis, self, CheckTimeOutWorker)
  }

  override def receive: Receive = {
    //Worker发送个Mater的注册消息
    case RegisterWorker(workerId, cores, memory) => {
      if(!idToWorker.contains(workerId)) {
        //封装worker发送的信息
        val workerInfo = new WorkerInfo(workerId, cores, memory)
        //保存workerInfo
        idToWorker(workerId) = workerInfo
        workers += workerInfo
        //Master向Worker反馈注册成功的消息
        sender ! RegisteredWorker(s"akka.tcp://${Master.MASTER_SYSTEM}@$host:$port/user/${Master.MASTER_NAME}")
      }
    }

      //Worker发送给Master的心跳信息
    case Heartbeat(workerId) => {
      if(idToWorker.contains(workerId)) {
        val workerInfo = idToWorker(workerId)
        val currentTime = System.currentTimeMillis()
        //更新上一次心跳时间
        workerInfo.lastHeartbeatTime = currentTime
      }
    }

      //检测超时的Worker
    case CheckTimeOutWorker => {
      val currentTime = System.currentTimeMillis()
      val deadWorkers: mutable.HashSet[WorkerInfo] = workers.filter(w => currentTime - w.lastHeartbeatTime > CHECK_INTERVAL)
//      for(w <- deadWorkers) {
//        idToWorker -= w.id
//        workers -= w
//      }
      deadWorkers.foreach(w => {
        idToWorker -= w.id
        workers -= w
      })
      println("alive worker size : " + workers.size)
    }
  }
}

object Master {

  val MASTER_SYSTEM = "MaterActorSystem"
  val MASTER_NAME = "Master"

  def main(args: Array[String]) {

    val host = args(0)
    val port = args(1).toInt
    val confStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin
    val conf = ConfigFactory.parseString(confStr)
    //ActorSystem是单例的，用于创建Acotor并监控actor
    val actorSystem = ActorSystem(MASTER_SYSTEM, conf)
    //通过ActorSystem创建Actor
    actorSystem.actorOf(Props(new Master(host, port)), MASTER_NAME)
    actorSystem.awaitTermination()

  }
}
