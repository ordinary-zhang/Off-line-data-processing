package cn.zhangyu.log

import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  * Created by grace on 2018/6/12.
  */
object LogETL {
  def main(args: Array[String]): Unit = {
    val spark=SparkSession.builder().config("spark.sql.parquet.compression.codec","lzo")
        .appName("LogETL").master("local[2]").getOrCreate()
    /*
    root
 |-- value: string (nullable = true)
 这时候因为读取的log文件文件返回一个df 他的结构只有一个value，并不是我们想要的，我们需要id，time ,day(根据time截取的)，
 所以我们要通过rdd转化为df定义我们想要的schemal结构
 我们定义了LogPaserUtil类，提供了StructType  解析日志后返回的Row
     */
    //"e:/data/input.log"   args(0)
    val dataDF=spark.read.format("text").load(args(0))
    //先把dateDF转化为RDD（dataDF.rdd）通过map并且调用解析log的LogPaserUtil类
    val dateRDD=dataDF.rdd.map(x=>LogPaserUtil.paserlog(x.getString(0)))

    //把rdd==>df(编程方式LogPaserUtil)
//   val resultDF=spark.createDataFrame(dateRDD,LogPaserUtil.struct)
//    /*
//root
// |-- id: string (nullable = true)
// |-- time: string (nullable = true)
// |-- day: long (nullable = true)
//     */
//    resultDF.printSchema()
//    resultDF.show(10)

    //反射方式LogPaserUtil2
    //dataDF.rdd 转化为rdd
    val rdd=dataDF.rdd.map(x=>LogPaseUtil2.paserlog(x.getString(0)))
    //导入隐式转换
    import spark.implicits._
    val resultDF=rdd.toDF()
   // resultDF.printSchema()
    resultDF.show(50)

    //在winds上保存会报错，打包进行提交，这时候通过传参指定输入（args(0)）和输出args(1)

    //format("parquet")改变存储格式
    resultDF.write.partitionBy("day").format("orc").
      save(args(1))

    spark.stop()
  }
}
