package cn.zhangyu.log

import org.apache.spark.internal.Logging

/**
  * Created by grace on 2018/6/12.
  */
object Log4jUtil extends Logging{

  def testLog4j(): Unit ={
    logDebug("Debug")
    logInfo("info")
  }

  def main(args: Array[String]): Unit = {
    testLog4j()
  }
}
