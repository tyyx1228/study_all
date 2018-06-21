package com.ty.study.tags

import java.io.{BufferedReader, InputStreamReader}
import java.net.URI
import org.arabidopsis.ahocorasick2.AhoCorasick
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FSDataInputStream, FileSystem, Path}
import org.apache.hadoop.io.IOUtils
import scala.collection.mutable

object Bund2AppName {
  val mappingColumn = 3
  var isLoad = false
  var ac = new AhoCorasick()
  val appNameMap = mutable.HashMap[String, String]()

  /**
   * 加载数据
   */
  def load(appMappingPath: String): Boolean = {
    var bundleIn: FSDataInputStream = null
    val conf = new Configuration()
    try {
      var line: String = null
      bundleIn = FileSystem.get(URI.create(appMappingPath), conf).open(new Path(appMappingPath))
      val bundleBf = new BufferedReader(new InputStreamReader(bundleIn))
      line = null
      do {
        line = bundleBf.readLine()
        if (null ne line) {
          val arr = line.split("\t", line.length())
          if ((arr.length == mappingColumn) && ("" ne arr(2))) {
            appNameMap.put(arr(2), arr(1))
            appNameMap.put(arr(1), arr(1))
            ac.add(arr(1).getBytes, arr(1))
          }
        }
      } while (null ne line)
      ac.prepare()
    } catch {
      case ex: Exception => return isLoad
    } finally {
      IOUtils.closeStream(bundleIn)
    }
    isLoad = true
    isLoad
  }

  def evaluateApp(bundleId: String, appName: String, args3: String): String = try {
    if (!isLoad) {
      load(args3)
    }
    if (appNameMap.contains(appName)) {
      appNameMap(appName)
    } else if (appNameMap.contains(bundleId)) {
      appNameMap(bundleId)
    } else {
      AcSearch.searchValue(ac, appName)
    }
  } catch {
    case e: Exception => "0"
  }

  def evaluateNewCategory(appType: String): String = {
    try {
      val sourceType = appType.toInt
      if (sourceType < 10) {
        "A0" + sourceType
      } else {
        "A" + sourceType
      }
    } catch {
      case e: Exception => "A999"
    }
  }

  /**
   * @param bundleId appId
   * @param appName  appName
   * @param sType    appname | newCategory
   * @param args3    appMappingPath
   * @return
   */
  def evaluate(bundleId: String, appName: String, sType: String, args3: String): String = {
    try {
      if ((bundleId == null) || (sType == null) || (appName == null)) {
        return "0"
      }
      if ("appname".equals(sType)) {
        evaluateApp(bundleId, appName, args3)
      } else if ("newCategory".equals(sType)) {
        evaluateNewCategory(bundleId)
      } else {
        "0"
      }
    } catch {
      case e: Exception => "0"
    }
  }
}
