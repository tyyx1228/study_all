package com.ty.study.tags

/**
  * APP
  */
object Tags4AppName {
  def make(appid: String, appname: String, apptype: String): Map[String, Int] = {
    var brandMap = Map[String, Int]()
    val path2 = "hdfs://master:9000/user/app_mapping.txt"
    // 名称
    val name = Bund2AppName.evaluate(appid, appname, "appname", path2)
    // 类别
    val category = Bund2AppName.evaluate(apptype, "", "newCategory", null)
    if (!"0".equals(name)) {
      brandMap += ("APP" + name -> 1)
    }
    if (!"0".equals(category)) {
      brandMap += (category -> 1)
    }
    brandMap
  }
}
