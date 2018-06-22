package com.tongyu.eg.rpc.akka

/**
  * Created by tongyu on 2016/8/18.
  */
trait Message extends Serializable

//Worker -> Master
case class RegisterWorker(id: String, cores: Int, memory: Int) extends Message

//Master -> Worker
case class RegisteredWorker(masterUrl: String) extends Message

//Worker -> Master
case class Heartbeat(id: String) extends Message

//Worker internal message
case object SendHeartbeat

//Master internal message
case object CheckTimeOutWorker