package com.ty.study.rpc

/**
  * Created by tongyu on 2016/8/18.
  */
class WorkerInfo(val id: String, val cores: Int, val memory: Int) {

  //TODO
  var lastHeartbeatTime: Long = _

}
