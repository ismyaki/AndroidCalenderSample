/**
 * 
 */
package tools.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;

/**
 * @author Wang
 *
 */
public class DateTool {
	/** 一秒*/
	public static final long oneSec = 1000l;
	/** 一分*/ 
	public static final long oneMin = oneSec * 60l;
	/** 一小時*/
	public static final long oneHour = oneMin * 60l;
	/** 一天*/
	public static final long oneDay = oneHour * 24l;
	/** 一周*/
	public static final long oneWeek = oneDay * 7l;
	/** 一月*/
	public static final long oneMonth = oneDay * 30l;
	/** 一年*/
	public static final long oneYear = oneMonth * 12l;
	
	/**
	* 取得現在時間
	* @author wang
	* @date 2016/3/1 下午2:22:39
	* @version 
	*/
	public static long getNowDate(){
		return System.currentTimeMillis();
	}
	
	/**
	* long 轉 date 
	* @author wang
	* @date 2016/3/1 下午2:22:39
	* @version 
	*/
	public static Date LongToDate(long milliseconds){
		Date date = new Date(milliseconds);
		return date;
	}
	
	/**
	* date 轉 long 
	* @author wang
	* @date 2016/3/1 下午2:22:39
	* @version 
	*/
	public static long DateToLong(String date , String format){
		long milliseconds = -1;
		try {
			SimpleDateFormat f = new SimpleDateFormat(format , Locale.getDefault());
			Date d = f.parse(date);
			milliseconds = d.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return milliseconds;
	}
	
	/**
	* long 轉 date 
	* @author wang
	* @date 2016/3/1 下午2:22:39
	* @version 
	*/
	public static long DateToLong(String date){
		return DateToLong(date, "yyyy/MM/dd HH:mm:ss");
	}
	
	
	/**
	* 取得時間 依照所給格式
	* @author wang
	* @date 2016/3/1 下午2:22:39
	* @version 
	*/
	public static long getMilliseconds(String dateString , String template){
		long milliseconds = 0;
		SimpleDateFormat format = new SimpleDateFormat(template , Locale.getDefault());  
		try {  
		    Date date = format.parse(dateString);
		    milliseconds = date.getTime();
		} catch (ParseException e) {   
		    e.printStackTrace();  
		}
		return milliseconds;
	}
	
	/**
	* 取得時間 依照所給格式
	* @author wang
	* @date 2016/3/1 下午2:22:39
	* @version 
	*/
	public static String getTime(long milliseconds , String template){
		Date date = new Date(milliseconds); 
		Calendar calender = Calendar.getInstance(); 
		calender.setTime(date);
		SimpleDateFormat sdf = new SimpleDateFormat(template , Locale.getDefault());
		return sdf.format(date);
	}
	
	/**
	* 取得年
	* @author wang
	* @date 2016/3/1 下午2:22:39
	* @version 
	*/
	public static int getYear(long milliseconds){
		Date date = new Date(milliseconds); 
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		return calender.get(Calendar.YEAR);
	}
	
	/**
	* 取得月
	* @author wang
	* @date 2016/3/1 下午2:22:39
	* @version 
	*/
	public static int getMonth(long milliseconds){
		Date date = new Date(milliseconds); 
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		return calender.get(Calendar.MONTH) + 1;
	}
	
	/**
	* 取得日(一個月中的第幾天)
	* @author wang
	* @date 2016/3/1 下午2:22:39
	* @version 
	*/
	public static int getDayOfMonth(long milliseconds){
		Date date = new Date(milliseconds); 
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		return calender.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	* 取得星期
	* @author wang
	* @date 2016/3/1 下午2:22:39
	* @version 
	*/
	public static int getDayOfWeek(long milliseconds){
		Date date = new Date(milliseconds); 
		Calendar calender = Calendar.getInstance(); 
		calender.setTime(date);
		return calender.get(Calendar.DAY_OF_WEEK) - 1;
	}
	
	/**
	* 取得日(一年中的第幾天)
	* @author wang
	* @date 2016/3/1 下午2:22:39
	* @version 
	*/
	public static int getDayOfYear(long milliseconds){
		Date date = new Date(milliseconds); 
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		return calender.get(Calendar.DAY_OF_YEAR);
	}
	
	/**
	* 取得時間 a hh:mm
	* @author wang
	* @date 2016/3/1 下午2:22:39
	* @version 
	*/
	public static String getTime(long milliseconds){
		return getTime(milliseconds, "a hh:mm");
	}
	
	/**
	* 取得小時 (24小時制)
	* @author wang
	* @date 2016/3/1 下午2:22:39
	* @version 
	*/
	public static int getHour(long milliseconds){
		return Integer.parseInt(getTime(milliseconds, "HH"));
	}
	
	/**
	* 取得分
	* @author wang
	* @date 2016/3/1 下午2:22:39
	* @version 
	*/
	public static int getMinute(long milliseconds){
		return Integer.parseInt(getTime(milliseconds, "mm"));
	}
	
	/**
	* 取得秒
	* @author wang
	* @date 2016/3/1 下午2:22:39
	* @version 
	*/
	public static int getSec(long milliseconds){
		return Integer.parseInt(getTime(milliseconds, "ss"));
	}

	/**
	 * local time to GMT time
	 * 輸入格式 : "yyyy-MM-dd HH:mm:ss" || "yyyy-MM-dd"
	 */
	public static String LocalTimeToGmtTime(String time){
		// local time to GMT time      
		String[] s = time.split(" ");
		String d = null;
		String t = null;
		if(s.length > 1){
			d = s[0];
			t = s[1]; 
		}
		if (d != null && t != null) {
			return string2TimezoneDefault(d + " " + t , TimeZone.getDefault().getID() , "GMT");
		}
		return string2TimezoneDefault(time , TimeZone.getDefault().getID() , "GMT");
	}
	
	/**
	 * GMT time to local time
	 * 輸入格式 : "yyyy-MM-dd HH:mm:ss" || "yyyy-MM-dd"
	 */
	public static String GmtTimeToLocalTime(String time){
		// GMT time to local time
		String[] s = time.split(" ");
		String d = null;
		String t = null;
		if(s.length > 1){
			d = s[0];
			t = s[1];
		}
		if (d != null && t != null) {
			return string2TimezoneDefault(d + " " + t , "GMT" , TimeZone.getDefault().getID());
		}
		return string2TimezoneDefault(time , "GMT" , TimeZone.getDefault().getID());
	}
	
//	/**
//	 * local date to GMT date
//	 * 輸入格式 : "yyyy-MM-dd"
//	 */
//	public static String LocalDateToGmtDate(String time){
//		// local time to GMT time      
//		return string2TimezoneDefault(time , TimeZone.getDefault().getID() , "GMT");
//	}
//	
//	/**
//	 * GMT date to local date
//	 * 輸入格式 : "yyyy-MM-dd"
//	 */
//	public static String GmtDateToLocalDate(String time){
//		// GMT time to local time
//		return string2TimezoneDefault(time , "GMT" , TimeZone.getDefault().getID());
//	}
	
	/**  
     * 对日期(时间)中的日进行加减计算. <br>  
     * 例子: <br>  
     * 如果Date类型的d为 2005年8月20日,那么 <br>  
     * calculateByDate(d,-10)的值为2005年8月10日 <br>  
     * 而calculateByDate(d,+10)的值为2005年8月30日 <br>  
     *   
     * @param d  
     *            日期(时间).  
     * @param amount  
     *            加减计算的幅度.+n=加n天;-n=减n天.  
     * @return 计算后的日期(时间).  
     */  
    public static Date calculateByDate(Date d, int amount) {   
        return calculate(d, GregorianCalendar.DATE, amount);   
    }   
    
    /**  
     * 对日期(时间)中的日进行加减计算. <br>  
     * 例子: <br>  
     * 如果Date类型的d为 2005年8月20日,那么 <br>  
     * calculateByDate(d,-10)的值为2005年8月10日 <br>  
     * 而calculateByDate(d,+10)的值为2005年8月30日 <br>  
     *   
     * @param d  
     *            日期(时间).  
     * @param amount  
     *            加减计算的幅度.+n=加n月;-n=减n月.  
     * @return 计算后的日期(时间).  
     */
    public static Date calculateByMinute(Date d, int amount) {   
        return calculate(d, GregorianCalendar.MINUTE, amount);   
    }   
    
    /**  
     * 对日期(时间)中的日进行加减计算. <br>  
     * 例子: <br>  
     * 如果Date类型的d为 2005年8月20日,那么 <br>  
     * calculateByDate(d,-10)的值为2005年8月10日 <br>  
     * 而calculateByDate(d,+10)的值为2005年8月30日 <br>  
     *   
     * @param d  
     *            日期(时间).  
     * @param amount  
     *            加减计算的幅度.+n=加n年;-n=减n年.  
     * @return 计算后的日期(时间).  
     */
    public static Date calculateByYear(Date d, int amount) {   
        return calculate(d, GregorianCalendar.YEAR, amount);   
    }   
  
    /**  
     * 对日期(时间)中由field参数指定的日期成员进行加减计算. <br>  
     * 例子: <br>  
     * 如果Date类型的d为 2005年8月20日,那么 <br>  
     * calculate(d,GregorianCalendar.YEAR,-10)的值为1995年8月20日 <br>  
     * 而calculate(d,GregorianCalendar.YEAR,+10)的值为2015年8月20日 <br>  
     *   
     * @param d  
     *            日期(时间).  
     * @param field  
     *            日期成员. <br>  
     *            日期成员主要有: <br>  
     *            年:GregorianCalendar.YEAR <br>  
     *            月:GregorianCalendar.MONTH <br>  
     *            日:GregorianCalendar.DATE <br>  
     *            时:GregorianCalendar.HOUR <br>  
     *            分:GregorianCalendar.MINUTE <br>  
     *            秒:GregorianCalendar.SECOND <br>  
     *            毫秒:GregorianCalendar.MILLISECOND <br>  
     * @param amount  
     *            加减计算的幅度.+n=加n个由参数field指定的日期成员值;-n=减n个由参数field代表的日期成员值.  
     * @return 计算后的日期(时间).  
     */  
    private static Date calculate(Date d, int field, int amount) {   
        if (d == null)   
            return null;   
        GregorianCalendar g = new GregorianCalendar();   
        g.setGregorianChange(d);   
        g.add(field, amount);   
        return g.getTime();   
    }   
  
    /**  
     * 日期(时间)转化为字符串.  
     *   
     * @param formater  
     *            日期或时间的格式.  
     * @param aDate  
     *            java.util.Date类的实例.  
     * @return 日期转化后的字符串.  
     */  
    public static String date2String(String formater, Date aDate) {   
        if (formater == null || "".equals(formater))   
            return null;   
        if (aDate == null)   
            return null;   
        return (new SimpleDateFormat(formater,Locale.getDefault())).format(aDate);   
    }   
  
    /**  
     * 当前日期(时间)转化为字符串.  
     *   
     * @param formater  
     *            日期或时间的格式.  
     * @return 日期转化后的字符串.  
     */  
    public static String date2String(String formater) {   
        return date2String(formater, new Date());   
    }   
       
    /**  
     * 获取当前日期对应的星期数.  
     * <br>1=星期天,2=星期一,3=星期二,4=星期三,5=星期四,6=星期五,7=星期六  
     * @return 当前日期对应的星期数  
     */  
    public static int dayOfWeek() {   
        GregorianCalendar g = new GregorianCalendar();   
        int ret = g.get(Calendar.DAY_OF_WEEK);
        g = null;   
        return ret;   
    }   
  
  
    /**  
     * 获取所有的时区编号. <br>  
     * 排序规则:按照ASCII字符的正序进行排序. <br>  
     * 排序时候忽略字符大小写.  
     *   
     * @return 所有的时区编号(时区编号已经按照字符[忽略大小写]排序).  
     */  
    public static String[] fecthAllTimeZoneIds() {   
        Vector<String> v = new Vector<String>();   
        String[] ids = TimeZone.getAvailableIDs();   
        for (int i = 0; i < ids.length; i++) {   
            v.add(ids[i]);   
        }   
        java.util.Collections.sort(v, String.CASE_INSENSITIVE_ORDER);   
        v.copyInto(ids);   
        v = null;   
        return ids;   
    }   
  
//    /**  
//     * 测试的main方法.  
//     *   
//     * @param argc  
//     */  
//    public static void main(String[] argc) {   
//           
//        String[] ids = fecthAllTimeZoneIds();   
//        String nowDateTime =date2String("yyyy-MM-dd HH:mm:ss");   
//        System.out.println("The time Asia/Shanhai is " + nowDateTime);//程序本地运行所在时区为[Asia/Shanhai]   
//        //显示世界每个时区当前的实际时间   
//        for(int i=0;i <ids.length;i++){   
//            System.out.println(" * " + ids[i] + "=" + string2TimezoneDefault(nowDateTime,ids[i]));    
//        }   
//        //显示程序运行所在地的时区   
//        System.out.println("TimeZone.getDefault().getID()=" +TimeZone.getDefault().getID());   
//    }   
  
    /**  
     * 将日期时间字符串根据转换为指定时区的日期时间.  
     *   
     * @param srcFormater  
     *            待转化的日期时间的格式.  
     * @param srcDateTime  
     *            待转化的日期时间. 
     * @param strTimeZoneId  
     *            待转化的时区编号.              
     * @param dstFormater  
     *            目标的日期时间的格式.  
     * @param dstTimeZoneId  
     *            目标的时区编号.  
     *   
     * @return 转化后的日期时间.  
     */  
    public static String string2Timezone(String srcFormater, String srcDateTime , String strTimeZoneId , String dstFormater, String dstTimeZoneId) {   
        if (srcFormater == null || "".equals(srcFormater))   
            return null;   
        if (srcDateTime == null || "".equals(srcDateTime))   
            return null;   
        if (dstFormater == null || "".equals(dstFormater))   
            return null;   
        if (dstTimeZoneId == null || "".equals(dstTimeZoneId))   
            return null;   
        SimpleDateFormat sdf = new SimpleDateFormat(srcFormater,Locale.getDefault());   
        sdf.setTimeZone(TimeZone.getTimeZone(strTimeZoneId)); 
        try {   
            int diffTime = getDiffTimeZoneRawOffset(dstTimeZoneId);   
            Date d = sdf.parse(srcDateTime);   
            long nowTime = d.getTime();   
            long newNowTime = nowTime - diffTime;   
            d = new Date(newNowTime);   
            return date2String(dstFormater, d);   
        } catch (ParseException e) {   
            e.printStackTrace();  
            return null;   
        } finally {   
            sdf = null;   
        }   
    }   
  
    /**  
     * 获取系统当前默认时区与指定时区的时间差.(单位:毫秒)  
     *   
     * @param timeZoneId  
     *            时区Id  
     * @return 系统当前默认时区与指定时区的时间差.(单位:毫秒)  
     */  
    private static int getDiffTimeZoneRawOffset(String timeZoneId) {   
        return TimeZone.getDefault().getRawOffset() - TimeZone.getTimeZone(timeZoneId).getRawOffset();   
    }   
  
    /**  
     * 将日期时间字符串根据转换为指定时区的日期时间.  
     *   
     * @param srcDateTime  
     *            待转化的日期时间. 
     * @param strTimeZoneId  
     *            待转化的时区编号.				 
     * @param dstTimeZoneId  
     *            目标的时区编号.  
     *   
     * @return 转化后的日期时间.  
     * @see #string2Timezone(String, String, String, String)  
     */  
    public static String string2TimezoneDefault(String srcDateTime, String strTimeZoneId, String dstTimeZoneId) {   
    	srcDateTime = srcDateTime.trim();
    	String date = null;//年月日
    	String time = null;//時分秒
    	
    	String yyyy = null;//年
    	String MM = null;//月
    	String dd = null;//日
    	String HH = null;//時
    	String mm = null;//分
    	String ss = null;//秒
    	
    	String format = "";
    	
    	if(srcDateTime.indexOf(" ") > -1){
    		String s[] = srcDateTime.split(" ");
    		if (s.length > 1) {
				date = s[0];
				time = s[1];
			}
    	}else{
    		if(srcDateTime.indexOf("-") > -1){
    			date = srcDateTime;
    		}
    		if(srcDateTime.indexOf(":") > -1){
    			time = srcDateTime;
    		}
    	}
    	if(date != null){
    		try{
    			String s[] = date.split("-");
    			yyyy = s[0];
        		MM = s[1];
        		dd = s[2];
    		}catch(Exception e){
    			
    		}
    		if(yyyy != null){
        		format += "yyyy";
        	}
        	if(MM != null){
        		if(format.length() > 0)
        			format += "-MM";
        		else
        			format += "MM";
        	}
        	if(dd != null){
        		if(format.length() > 0)
        			format += "-dd";
        		else
        			format += "dd";
        	}
    	}
    	if(time != null){
    		try{
	    		String s[] = time.split(":");
	    		HH = s[0];
	    		mm = s[1];
	    		ss = s[2];
    		}catch(Exception e){
    			
    		}
    		if (format.length() > 0) {
    			format += " ";
			}
    		if(HH != null){
        		format += "HH";
        	}
        	if(mm != null){
        		format += ":mm";
        	}
        	if(ss != null){
        		format += ":ss";
        	}
    	}
        return string2Timezone(format, srcDateTime , strTimeZoneId , format, dstTimeZoneId);   
    }  
    
}
