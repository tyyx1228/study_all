package com.ty.study.cases.ip

import java.io.{BufferedReader, FileInputStream, InputStreamReader}

import scala.collection.mutable.ArrayBuffer

object IPLocationDemo {

  def ip2Long(ip: String): Long = {
    val fragments = ip.split("[.]")
    var ipNum = 0L
    for (i <- 0 until fragments.length){
      ipNum =  fragments(i).toLong | ipNum << 8L
    }
    ipNum
  }

  def readData(path: String) = {
    val br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))
    var s: String = null
    var flag = true
    val lines = new ArrayBuffer[String]()
    while (flag)
    {
      s = br.readLine()
      if (s != null)
        lines += s
      else
        flag = false
    }
    lines
  }

  def binarySearch(lines: ArrayBuffer[String], ip: Long) : Int = {
    val start = System.currentTimeMillis()
    println("start:"+start)
    var low = 0
    var high = lines.length - 1
    while (low <= high) {
      val middle = (low + high) / 2
      if ((ip >= lines(middle).split("\\|")(2).toLong) && (ip <= lines(middle).split("\\|")(3).toLong)){
        println("end"+ (System.currentTimeMillis() - start))
        return middle
      }
      if (ip < lines(middle).split("\\|")(2).toLong)
        high = middle - 1
      else {
        low = middle + 1
      }
    }
    -1
  }
  //
  def main(args: Array[String]) {
    //val ip = "120.55.185.61"
    val ip = "1.0.15.252"
    val ipNum = ip2Long(ip)
    println(ipNum)
    val lines = readData("E:\\wordcount\\spark\\iplistforchina\\ip.txt")
    val index = binarySearch(lines, ipNum)
    print(lines(index))
  }
}
