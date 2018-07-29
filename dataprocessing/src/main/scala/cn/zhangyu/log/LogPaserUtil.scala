package cn.zhangyu.log

import org.apache.spark.sql.{Row, types}
import org.apache.spark.sql.types._

import scala.io.Source

/**
  * Created by grace on 2018/6/13.
  */
object LogPaserUtil {

  //编程方式 把RDD--->DF

//定义StructType
//  val schemalString="id,time,day"
//  val fields=schemalString.split(",").map(filedName=>StructField(filedName,StringType,nullable =true ))
//  val schema=StructType(fields)
  val struct=StructType(Array(
    StructField("id",IntegerType),
  StructField("time",StringType),
  StructField("day",LongType)
  ))

  //解析日志
  def paserlog(log:String)={
    val col=log.split("  ")
    Row(col(0).toInt,DataUtil.parse(col(1)),DataUtil.getDay(col(1)).toLong)
  }

  def main(args: Array[String]): Unit = {

    //读取文件
    val file=Source.fromFile("e:/data/input.log")
    for(line<-file.getLines()){
      //println(line)

      //读取我们需要的字段
      val col=line.split("  ")
      //0:id 1:time 3:day
      println(col(0)+" "+DataUtil.parse(col(1))+" "+DataUtil.getDay(col(1)))
    }
  }


}
