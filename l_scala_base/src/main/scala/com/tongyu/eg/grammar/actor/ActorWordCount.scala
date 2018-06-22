package com.tongyu.eg.grammar.actor

import scala.actors.{Actor, Future}
import scala.collection.mutable.{HashSet, ListBuffer}
import scala.io.Source
/**
  * Created by root on 2016/5/11.
  */
class Task extends Actor {
  override def act(): Unit = {
    loop {
      react {
        case SubmitTask(filename) => {
          val result = Source.fromFile(filename).getLines().flatMap(_.split(" ")).map((_, 1)).toList.groupBy(_._1).mapValues(_.size)
          sender ! ResultTask(result)
        }
        case StopTask => {
          exit()
        }
      }
    }
  }
}

case class SubmitTask(filename: String)
case class ResultTask(reslut : Map[String, Int])
case object StopTask

object ActorWordCount {

  def main(args: Array[String]) {
    val replySet = new HashSet[Future[Any]]()
    val resultList = new ListBuffer[ResultTask]()

    val files = Array[String]("c://words.txt", "c://words.log")
    for (f <- files) {
      val actor = new Task
      val reply = actor.start() !! SubmitTask(f)
      replySet += reply
    }

    while(replySet.size > 0) {
      val toCompute = replySet.filter(_.isSet)
      for(f <- toCompute) {
        val result = f().asInstanceOf[ResultTask]
        resultList += result
        replySet -= f
      }
      Thread.sleep(100)
    }

    //汇总的功能
    //List((hello, 5), (tom,3), (helllo, 2), (jerry, 2))
    val fr = resultList.flatMap(_.reslut).groupBy(_._1).mapValues(_.foldLeft(0)(_+_._2))
    println(fr)

  }
}
