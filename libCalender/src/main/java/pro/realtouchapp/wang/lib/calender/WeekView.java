package pro.realtouchapp.wang.lib.calender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

public class WeekView extends GridView{
	
	private Adapter adapter;
	
	public WeekView(Context context) {
		super(context);
		initView();
	}
	
	public WeekView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public WeekView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}
	
	private void initView(){
		setNumColumns(7);
		adapter = new SampleAdapter(getContext());
		setAdapter(adapter);
	}
	
	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		if (adapter instanceof Adapter) {
			this.adapter = (Adapter) adapter;
		}
	}
	
	/**
	 * @return 目前顯示的日期
	 * @author Wang
	 * @date 2015/8/19 下午3:59:35
	 * @version 
	 */
	public Calendar getThisCalendar(){
		return adapter.getThisCalendar();
	}
	
	/**
	 * @return 顯示區段的list
	 * @author Wang
	 * @date 2015/8/19 下午4:00:37
	 * @version 
	 */
	public ArrayList<Date> getDateList(){
		return adapter.getDateList();
	}
	
	/**
	 * 回傳list指定index日期
	 * @param position
	 * @return
	 * @author Wang
	 * @date 2015/8/19 下午4:01:44
	 * @version 
	 */
	public Date getDate(int position){
		return adapter.getDate(position);
	}

	/**
	 * 顯示本月(依照機器時間)
	 * @author Wang
	 * @date 2015/8/18 下午3:25:38
	 * @version 
	 */
	public void thisWeek(){
		adapter.thisWeek();
	}
	
	/**
	 * 指定加減多少周
	 * @author Wang
	 * @date 2015/8/18 下午3:26:01
	 * @version 
	 */
	public void setWeek(int value){
		adapter.setWeek(value);
	}
	
	/**
	 * 顯示下個月
	 * @author Wang
	 * @date 2015/8/18 下午3:25:54
	 * @version 
	 */
	public void nextWeek(){
		adapter.nextWeek();
	}
	
	/**
	 * 顯示上個月
	 * @author Wang
	 * @date 2015/8/18 下午3:26:01
	 * @version 
	 */
	public void backWeek(){
		adapter.backWeek();
	}
	
	/**
	 * 顯示指定月份
	 * @param year
	 * @param month
	 * @param date
	 * @author Wang
	 * @date 2015/8/18 下午3:30:22
	 * @version 
	 */
	public void gotoWeek(int year, int month,int date){
		adapter.gotoWeek(year, month, date);
	}
	
	public static abstract class Adapter extends BaseAdapter{
		private ArrayList<Date> dateList = new ArrayList<Date>();
		/** 目前指定顯示日期*/
		private Calendar thisCalendar = Calendar.getInstance();
		
		public Adapter(){
			dateList = CalendarTool.getThisWeekDates();
		}
		
		/**
		 * @return 目前顯示的日期
		 * @author Wang
		 * @date 2015/8/19 下午3:59:35
		 * @version 
		 */
		public Calendar getThisCalendar(){
			return thisCalendar;
		}
		
		/**
		 * @return 顯示區段的list
		 * @author Wang
		 * @date 2015/8/19 下午4:00:37
		 * @version 
		 */
		public ArrayList<Date> getDateList(){
			return dateList;
		}
		
		/**
		 * 回傳list指定index日期
		 * @param position
		 * @return
		 * @author Wang
		 * @date 2015/8/19 下午4:01:44
		 * @version 
		 */
		public Date getDate(int position){
			return dateList.get(position);
		}
		
		/**
		 * 顯示本月(依照機器時間)
		 * @author Wang
		 * @date 2015/8/18 下午3:25:38
		 * @version 
		 */
		public void thisWeek(){
			thisCalendar = Calendar.getInstance();
			int year = thisCalendar.get(Calendar.YEAR);
			int month = thisCalendar.get(Calendar.MONTH)+1;
			int date = thisCalendar.get(Calendar.DAY_OF_MONTH);
			dateList = CalendarTool.getWeekDates(year, month, date);
			notifyDataSetChanged();
		}
		
		/**
		 * 指定加減多少周
		 * @author Wang
		 * @date 2015/8/18 下午3:26:01
		 * @version 
		 */
		public void setWeek(int value){
			thisCalendar.add(Calendar.WEEK_OF_MONTH, value);
			int year = thisCalendar.get(Calendar.YEAR);
			int month = thisCalendar.get(Calendar.MONTH)+1;
			int date = thisCalendar.get(Calendar.DAY_OF_MONTH);
			dateList = CalendarTool.getWeekDates(year, month, date);
			notifyDataSetChanged();
		}
		
		/**
		 * 顯示下個月
		 * @author Wang
		 * @date 2015/8/18 下午3:25:54
		 * @version 
		 */
		public void nextWeek(){
			thisCalendar.add(Calendar.WEEK_OF_MONTH, 1);
			int year = thisCalendar.get(Calendar.YEAR);
			int month = thisCalendar.get(Calendar.MONTH)+1;
			int date = thisCalendar.get(Calendar.DAY_OF_MONTH);
			dateList = CalendarTool.getWeekDates(year, month, date);
			notifyDataSetChanged();
		}
		
		/**
		 * 顯示上個月
		 * @author Wang
		 * @date 2015/8/18 下午3:26:01
		 * @version 
		 */
		public void backWeek(){
			thisCalendar.add(Calendar.WEEK_OF_MONTH, -1);
			int year = thisCalendar.get(Calendar.YEAR);
			int month = thisCalendar.get(Calendar.MONTH)+1;
			int date = thisCalendar.get(Calendar.DAY_OF_MONTH);
			dateList = CalendarTool.getWeekDates(year, month, date);
			notifyDataSetChanged();
		}
		
		/**
		 * 顯示指定月份
		 * @param year
		 * @param month
		 * @param date
		 * @author Wang
		 * @date 2015/8/18 下午3:30:22
		 * @version 
		 */
		public void gotoWeek(int year, int month ,int date){
			thisCalendar.set(year, month-1, 1);
			dateList = CalendarTool.getWeekDates(year, month, date);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return dateList.size();
		}

		@Override
		public Object getItem(int position) {
			return dateList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		/**
		 * Date 轉換成 Calendar
		 * @param date
		 * @return
		 * @author Wang
		 * @date 2015/8/18 下午7:32:44
		 * @version 
		 */
		public Calendar DateToCalendar(Date date){
			Calendar calender = Calendar.getInstance();
			calender.setTime(date);
			return calender;
		}

		@Override
		public abstract View getView(int position, View convertView, ViewGroup parent);
		
	}
	
	/**
	 * 範例
	 * @author Wang
	 * @date 2015/8/19 下午3:26:05
	 */
	public class SampleAdapter extends Adapter{
		private Context context;
		
		public SampleAdapter(Context context) {
			super();
			this.context = context;
		}
		
		private class ViewHolder{
			LinearLayout ll_main;
			TextView tv_day;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView ==  null) {
				convertView = new LinearLayout(context);
				
				viewHolder = new ViewHolder();		
				
				viewHolder.ll_main = new LinearLayout(context);
				((LinearLayout)convertView).addView(viewHolder.ll_main);
				
				UserInterfaceTool.setViewSize(viewHolder.ll_main, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				UserInterfaceTool.setMarginByDpUnit(viewHolder.ll_main, 10, 5, 0, 5);
				
				viewHolder.tv_day = new TextView(context);
				viewHolder.ll_main.addView(viewHolder.tv_day);
				
				UserInterfaceTool.setTextSize(viewHolder.tv_day, 12);
				viewHolder.tv_day.setTextColor(getResources().getColor(android.R.color.white));
				
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder)convertView.getTag();
			}
			
			Date date = getDate(position);
			Calendar calender = DateToCalendar(date);
			
			int month = calender.get(Calendar.MONTH) + 1;
			int day_for_month = calender.get(Calendar.DAY_OF_MONTH);
			int day_for_week = calender.get(Calendar.DAY_OF_WEEK) - 1;
			
			String s = month + "/" + day_for_month + "\n(" + day_for_week + ")";
			viewHolder.tv_day.setText(s);
			
			if (calender.get(Calendar.MONTH) != getThisCalendar().get(Calendar.MONTH)) {
				viewHolder.tv_day.setTextColor(getResources().getColor(android.R.color.darker_gray));
			}else{
				viewHolder.tv_day.setTextColor(getResources().getColor(android.R.color.white));
			}
			
			return convertView;
		}
		
	}
}
