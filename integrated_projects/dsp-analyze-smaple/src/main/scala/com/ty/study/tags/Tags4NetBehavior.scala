package com.ty.study.tags

import scala.collection.mutable

/**
  * 联网方式和运营商
  */
object Tags4NetBehavior {

  var netBehCodeMap = new mutable.HashMap[String, String]

  var isload = false
  if (!isload) {
    netBehCodeMap.put("WIFI", "N00030001")
    netBehCodeMap.put("4G", "N00030002")
    netBehCodeMap.put("3G", "N00030003")
    netBehCodeMap.put("2G", "N00030004")
    netBehCodeMap.put("移动", "N00040001")
    netBehCodeMap.put("联通", "N00040002")
    netBehCodeMap.put("电信", "N00040003")
    isload = true
  }

  def make(networkmannername: String, ispname: String): Map[String, Int] = {
    var netBehaviorMap = Map[String, Int]()
    if (networkmannername.nonEmpty) {
      if (netBehCodeMap.contains(networkmannername.toUpperCase())) {
        netBehaviorMap += (netBehCodeMap(networkmannername.toUpperCase) -> 1)
      }
    }
    if (ispname.nonEmpty) {
      if (netBehCodeMap.contains(ispname)) {
        netBehaviorMap += (netBehCodeMap(ispname) -> 1)
      }
    }
    netBehaviorMap
  }
}
