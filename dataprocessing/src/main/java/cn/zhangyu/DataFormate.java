package cn.zhangyu;
import org.apache.commons.lang3.time.FastDateFormat;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by grace on 2018/6/13.
 */
public class DataFormate {

    /*
    SimpleDateFormat非线程安全  FastDateFormat线程安全的
     */
    //时间转化工具类
   // public static final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static FastDateFormat DateFormat=FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    //根据指定的格式输入
    public static String format(Date date){

        return DateFormat.format(date);
    }

    public static Date parse(String s) throws ParseException {
       return   DateFormat.parse(s);
    }


    public static void main(String[] args) throws ParseException {
//        Date date=new Date();
//        System.out.println(date);
//        String s="1994-06-15 12:21:30";
//        System.out.println(parse(s));
//        System.out.println(format(parse(s)));

        Test test1=new Test();

        Test test2=new Test();
        test1.start();
        test2.start();
        }
    }


class Test extends Thread{
    @Override
    public void run() {
        while (true){
            try {
                System.out.println(Thread.currentThread().getName()+DataFormate.parse("1994-06-15 12:21:30"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}