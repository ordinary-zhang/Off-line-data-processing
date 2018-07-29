package cn.zhangyu.log

import org.apache.spark.sql.Row

import scala.io.Source

/**
  * Created by grace on 2018/6/13.
  */

//使用反射方式把RDD--->DF
object LogPaseUtil2 {

  case class Date(id:Int,time:String,day:Long)

  def paserlog(log:String) ={
    val col=log.split("  ")
    Date(col(0).toInt,DataUtil.parse(col(1)),DataUtil.getDay(col(1)).toLong)
  }
  def main(args: Array[String]): Unit = {

    //读取文件
    val file = Source.fromFile("e:/data/input.log")
    for (line <- file.getLines()) {
      //println(line)

      //读取我们需要的字段
      val col = line.split("  ")
      //0:id 1:time 3:day
      println(col(0) + " " + DataUtil.parse(col(1)) + " " + DataUtil.getDay(col(1)))
    }
  }
}
