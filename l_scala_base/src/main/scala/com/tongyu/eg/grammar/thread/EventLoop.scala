package com.tongyu.eg.grammar.thread

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.{BlockingQueue, LinkedBlockingDeque}

import scala.util.control.NonFatal

/**
  * Created by ZX on 2016/3/29.
  */
abstract class EventLoop[E](name: String) {

  private val eventQueue: BlockingQueue[E] = new LinkedBlockingDeque[E]()

  private val stopped = new AtomicBoolean(false)

  private val eventThread = new Thread(name) {
    //setDaemon(true)

    override def run(): Unit = {
      try {
        while (!stopped.get) {
          val event = eventQueue.take
          try {
            onReceive(event)
          } catch {
            case NonFatal(e) => {
              try {
                onError(e)
              } catch {
                case NonFatal(e) => println("Unexpected error in " + name, e)
              }
            }
          }
        }
      } catch {
        case ie: InterruptedException => // exit even if eventQueue is not empty
        case NonFatal(e) => println("Unexpected error in " + name, e)
      }
    }

  }

  def start(): Unit = {
    if (stopped.get) {
      throw new IllegalStateException(name + " has already been stopped")
    }
    // Call onStart before starting the event thread to make sure it happens before onReceive
    onStart()
    eventThread.start()
  }

  def stop(): Unit = {
    if (stopped.compareAndSet(false, true)) {
      eventThread.interrupt()
      var onStopCalled = false
      try {
        eventThread.join()
        // Call onStop after the event thread exits to make sure onReceive happens before onStop
        onStopCalled = true
        onStop()
      } catch {
        case ie: InterruptedException =>
          Thread.currentThread().interrupt()
          if (!onStopCalled) {
            // ie is thrown from `eventThread.join()`. Otherwise, we should not call `onStop` since
            // it's already called.
            onStop()
          }
      }
    } else {
      // Keep quiet to allow calling `stop` multiple times.
    }
  }

  /**
    * Put the event into the event queue. The event thread will process it later.
    */
  def post(event: E): Unit = {
    eventQueue.put(event)
  }

  /**
    * Return if the event thread has already been started but not yet stopped.
    */
  def isActive: Boolean = eventThread.isAlive

  /**
    * Invoked when `start()` is called but before the event thread starts.
    */
  protected def onStart(): Unit = {}

  /**
    * Invoked when `stop()` is called and the event thread exits.
    */
  protected def onStop(): Unit = {}

  /**
    * Invoked in the event thread when polling events from the event queue.
    *
    * Note: Should avoid calling blocking actions in `onReceive`, or the event thread will be blocked
    * and cannot process events in time. If you want to call some blocking actions, run them in
    * another thread.
    */
  //protected def onReceive(event: E): Unit

  protected def onReceive: PartialFunction[E, Unit]

  /**
    * Invoked if `onReceive` throws any non fatal error. Any non fatal error thrown from `onError`
    * will be ignored.
    */
  protected def onError(e: Throwable): Unit

}
