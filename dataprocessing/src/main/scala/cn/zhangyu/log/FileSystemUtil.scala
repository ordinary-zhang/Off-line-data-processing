package cn.zhangyu.log

import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.deploy.SparkHadoopUtil
/**
  * Created by grace on 2018/9/2.
  */
object FileSystemUtil {

  def main(args: Array[String]): Unit = {
    val conf=new Configuration()
    conf.set("fs.default.name","hdfs://localhost:8020")
    val fileSystem = FileSystem.get(URI.create("hdfs://localhost:8020/spark/emp/temp/201708081000/d=20170808/h=20"),conf)
    val outputPath = "hdfs://localhost:8020/spark/emp"
    val loadTime = "201708081000"
    val partition = "/d=20170808/h=20"

    changeFileName(fileSystem,outputPath,loadTime,partition)
  }

  def changeFileName(fileSystem: FileSystem , outputPath: String , loadTime: String , partition: String):Unit={
    val paths: Seq[Path] = SparkHadoopUtil.get.globPath(new Path(outputPath + "temp/" + loadTime + partition + "/*.txt"))
    var times=0;
    paths.map(x=>{
      println("--kakg")
      var newLocation = x.toString.replace(outputPath + "/temp/" + loadTime, outputPath + "data/")
      println("1:" + newLocation)
      newLocation = newLocation.replace("part-r-","")
      val index = newLocation.lastIndexOf("/")
      times +=1
      newLocation = newLocation.substring(0,index + 1) + loadTime + "-" +times + ".txt"
      println("2+" + newLocation)
      val officiaPath = new Path(newLocation)
      if (!fileSystem.exists(officiaPath.getParent)){
        fileSystem.mkdirs(officiaPath.getParent)
      }
      fileSystem.rename(x,officiaPath)
    })
  }
}
