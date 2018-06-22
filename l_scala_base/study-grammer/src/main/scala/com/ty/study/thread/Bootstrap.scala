package com.ty.study.thread

/**
  * Created by ZX on 2016/3/29.
  */
object Bootstrap {

  def main(args: Array[String]) {
    val eventLoop = new TaskProcessEventLoop("task-event-loop")
    eventLoop.start()
    for (i <- 1 to 10) {
      eventLoop.post(TaskSubmitted(s"task-$i"))
    }
    Thread.sleep(10000)
  }
}
