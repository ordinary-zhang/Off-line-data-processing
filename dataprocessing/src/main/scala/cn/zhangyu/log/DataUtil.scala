package cn.zhangyu.log

import java.text.{ParseException, SimpleDateFormat}
import java.util.Date

import org.apache.commons.lang3.time.FastDateFormat
import org.apache.spark.internal.Logging

/**
  * Created by grace on 2018/6/12.
  */
object DataUtil extends Logging{
  /**
    * 根据日志里面的时间转化为我们需要的时间格式
    * 1）时区解析
    * 2）时间解析
    *      2017/12/17 23:42 ===>201712172342
    * 3)按天进行分区
    *      获得天：===>20180813
    */

  //输入数据
  //val inputDate=new SimpleDateFormat("yyyy/MM/dd HH:mm")
  val inputDate=FastDateFormat.getInstance("yyyy/MM/dd HH:mm")
  //定义输出数据格式
  val outputDate=FastDateFormat.getInstance("yyyyMMddHHmm")

  //得到时间
  def getTime(Time: String):Long={
    inputDate.parse(Time).getTime
  }
  //解析时间
  //如果这里解析的时间格式不是按照我们所写的怎么办呢? try catch 抓取，使用log4j
  def parse(Time:String): String ={
    try {
      outputDate.format(new Date(getTime(Time)))
    }catch {
      case e:ParseException=>{
        logInfo("info")
        logError("日志格式不合格："+e.getMessage)
        //
        "日志格式不合格"
      }
    }

  }

  //获得天（分区）20171217
def getDay(time: String) ={
    val value= parse(time)
    value.substring(0,8)
}


  def main(args: Array[String]): Unit = {
    println(getTime("2017/12/17 23:42"))
    println(parse("2017/12/17 23:42"))
    println(parse("2017/12/17 23:42"))
    println(getDay("2017/12/17 23:42"))
  }
}
