package com.example.calenderproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import pro.realtouchapp.wang.lib.calender.WeekView;
import tools.date.DateTool;
import tools.userinterface.UserInterfaceTool;

public class WeekActivity2 extends Activity{
	private String TAG = this.getClass().getCanonicalName();
	
	private int heightPixels = 0;
	private int widthPixels = 0;
	
	private ViewPager vp_week;
	private WeekPagerAdapter weekPageAdapter;
	
	private long dateMilliseconds;
	private Date todayDate;
	private Date touchDate;
	
	private TextView tv_msg;
	private TextView tv_touch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_week2);
		
		widthPixels = UserInterfaceTool.getScreenWidthPixels(this);
		heightPixels = UserInterfaceTool.getScreenHeightPixels(this);
		
		getData();
		initWeekView();
		initButton();
	}
	
	private void getData(){
		dateMilliseconds = DateTool.getNowDate();
		todayDate = new Date();
		touchDate = new Date();
		//
		tv_msg = (TextView) findViewById(R.id.tv_msg);
		tv_touch = (TextView) findViewById(R.id.tv_touch);
	}
	
	private void initButton(){
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO 下一周
				Calendar calendar = weekPageAdapter.getItemView(vp_week.getCurrentItem());
				weekPageAdapter.setCurrentData(calendar.getTimeInMillis() + DateTool.oneWeek);
			}
		});
		findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO 上一周
				Calendar calendar = weekPageAdapter.getItemView(vp_week.getCurrentItem());
				weekPageAdapter.setCurrentData(calendar.getTimeInMillis() - DateTool.oneWeek);
			}
		});
		findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO 這一周
				weekPageAdapter.setCurrentData(DateTool.getNowDate());
			}
		});
		findViewById(R.id.btn_goto).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO 指定顯示
				EditText et_year = (EditText) findViewById(R.id.et_year);
				EditText et_month = (EditText) findViewById(R.id.et_month);
				EditText et_day = (EditText) findViewById(R.id.et_day);
				String sy = et_year.getText().toString().trim();
				String sm = et_month.getText().toString().trim();
				String sd = et_day.getText().toString().trim();
				int year = Integer.parseInt(sy);
				int month = Integer.parseInt(sm);
				int day = Integer.parseInt(sd);
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.set(year, month - 1, day);
				weekPageAdapter.setCurrentData(calendar.getTime().getTime() - 1);
			}
		});
	}
	
	private void initWeekView(){
		vp_week = (ViewPager) findViewById(R.id.vp_week);
		UserInterfaceTool.setViewSize(vp_week, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		weekPageAdapter = new WeekPagerAdapter(this);
		vp_week.setAdapter(weekPageAdapter);
		int position = weekPageAdapter.getCount()/2;
		if (dateMilliseconds != DateTool.getNowDate()) {
			long oneDay = 1000l * 60l * 60l * 24l;
			long nowDate = DateTool.getNowDate();
			long difDate = (nowDate - (DateTool.getDayOfWeek(nowDate) * oneDay)) - (dateMilliseconds - (DateTool.getDayOfWeek(dateMilliseconds) * oneDay));
			int difDay = (int)(difDate / oneDay);
			int difWeek = (int)(difDate / (oneDay * 7l));
			if (difDay <= -6) {
				difWeek -= 1;
			}
			Log.e(TAG, "difWeek " + difWeek + " difDay " + difDay + " " +DateTool.getDayOfWeek(dateMilliseconds));
			position = weekPageAdapter.getCount()/2 - difWeek;
			Log.e(TAG, "position " + position);
		}
		vp_week.setCurrentItem(position);
		Log.e(TAG, "position " + position + " " + weekPageAdapter.getCount());
		vp_week.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int state) {
			
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				//TODO 滑動周
				Calendar calendar = weekPageAdapter.getItemView(position);
				if (calendar != null) {
					long time = calendar.getTimeInMillis();
					int year = DateTool.getYear(time);
					int month = DateTool.getMonth(time);
					int week = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
					String txt = year + "/" + month + " 第" + week + "周";
//					txt = DateTool.getTime(calendar.getTimeInMillis() , "yyyy-MM-dd");
					tv_msg.setText(txt);
				}
			}
			
			@Override
			public void onPageScrollStateChanged(int position) {
			
			}
		});
	}
	
	private class WeekPagerAdapter extends PagerAdapter {
		private Context context;
		private LayoutInflater inflater;
		private ViewGroup viewGroup;
		
		public WeekPagerAdapter(Context context) {
			this.context = context;
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		
		@Override
		public int getCount() {
			return 1000;
		}
		
		/**
		 * 移動到指定日期
		 */
		public void setCurrentData(Date date){
			setCurrentData(date.getTime());
		}
		
		/**
		 * 移動到指定日期
		 */
		public void setCurrentData(long date){
			Log.e(TAG , "setCurrentData " + DateTool.getTime(date , "yyyy-MM-dd"));
			long oneDay = DateTool.oneDay;
			long nowDate = DateTool.getNowDate();
			long difDate = (nowDate - (DateTool.getDayOfWeek(nowDate) * oneDay)) - (date - (DateTool.getDayOfWeek(date) * oneDay));
			int difDay = (int)(difDate / oneDay);
			int difWeek = (int)(difDate / (oneDay * 7l));
			if (difDay <= -6) {
				difWeek -= 1;
			}
			Log.e(TAG, "difWeek " + difWeek + " difDay " + difDay + " " +DateTool.getDayOfWeek(dateMilliseconds));
			int position = getCount()/2 - difWeek;
			vp_week.setCurrentItem(position);
			//
			int touchPosition = DateTool.getDayOfWeek(date);
			touchDate = new Date(date);
			WeekView weekView = getItemWeekView(position);
			if (weekView != null) {
				((WeekAdapter)weekView.getAdapter()).touchReflashView(touchPosition);
			}
		}
		
		public WeekView getItemWeekView(int position){
			View view = null;
			for(int i = 0 ; viewGroup != null && i < viewGroup.getChildCount() ; i++){
				if (viewGroup.getChildAt(i).getTag().equals(position)) {
					view = viewGroup.getChildAt(i);
				}
			}
			if (view == null) {
				return null;
			}
			WeekView weekView = (WeekView) view.findViewById(R.id.weekView);
			return weekView;
		}
		
		public Calendar getItemView(int position){
			View view = null;
			for(int i = 0 ; viewGroup != null && i < viewGroup.getChildCount() ; i++){
				if (viewGroup.getChildAt(i).getTag().equals(position)) {
					view = viewGroup.getChildAt(i);
				}
			}
			if (view == null) {
				return null;
			}
			WeekView weekView = (WeekView) view.findViewById(R.id.weekView);
			UserInterfaceTool.setViewSize(weekView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//			weekView.setColumnWidth(widthPixels / 7 + 1);
			Calendar calendar = weekView.getThisCalendar();
			return calendar;
		}
		
		@Override
		public Object instantiateItem(ViewGroup view, final int position) {
			if (this.viewGroup == null) {
				this.viewGroup = view;
			}
			RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.adapter_vp_week2, view, false);
			layout.setTag(position);
			
			final WeekView weekView = (WeekView) layout.findViewById(R.id.weekView);
			final WeekAdapter adapter = new WeekAdapter(context);
			adapter.setTouchDate(dateMilliseconds);
			weekView.setAdapter(adapter);
			
			weekView.setWeek(position - (getCount()/2));
			weekView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
				                        int position, long id) {
					adapter.touchReflashView(position);
					Date touchDate = weekView.getDate(position);
					long time = touchDate.getTime();
					//TODO 點擊某天
					tv_touch.setText("TOUCH " + DateTool.getTime(time , "yyyy-MM-dd"));
				}
			});
			
			view.addView(layout, 0);
			return layout;
		}
		
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}
		
		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}
		
		@Override
		public Parcelable saveState() {
			return null;
		}
		
	}
	
	private class WeekAdapter extends WeekView.Adapter{
		private Context context;
		private Calendar todayCalendar = Calendar.getInstance();
		private LayoutInflater myInflater;
		private ViewGroup viewGroup;
		
		public WeekAdapter(Context context) {
			this.context = context;
			myInflater = LayoutInflater.from(context);
		}
		
		public void setTouchDate(long milliseconds){
			Date date = new Date(milliseconds);
			todayCalendar.setTime(date);
		}
		
		private class ViewHolder{
			View rl_main;
			TextView tv_week;
			TextView tv_day;
		}
		
		/**
		 * 更新指定 item view
		 */
		public void touchReflashView(int position){
			Date touchDate = getDate(position);
			Calendar touchCalender = DateToCalendar(touchDate);
			for(int i = 0 ; viewGroup != null && i < viewGroup.getChildCount() ; i++){
				View v = viewGroup.getChildAt(i);
				TextView tv = (TextView) v.findViewById(R.id.tv_day);
				
				Date date = getDate(i);
				Calendar calender = DateToCalendar(date);
				int day_for_week = calender.get(Calendar.DAY_OF_WEEK) - 1;
				//
				GradientDrawable bg;
				int strokeWidth = UserInterfaceTool.getPixelFromDpByDevice(context, 1);
				int colorID = 0;//填滿顏色
				int strokeColorID  = 0;//填滿顏色
				
				if(calender.get(Calendar.MONTH) == touchCalender.get(Calendar.MONTH) &&
						calender.get(Calendar.DAY_OF_MONTH) == touchCalender.get(Calendar.DAY_OF_MONTH)){
					strokeWidth = UserInterfaceTool.getPixelFromDpByDevice(context, 3);
					colorID = R.color.white;
					strokeColorID = R.color.calendar_stroke_today;
				}else if (calender.get(Calendar.MONTH) == getThisCalendar().get(Calendar.MONTH)) {
					colorID = R.color.white;
					strokeColorID = R.color.calendar_stroke;
				}else{
					colorID = R.color.white;
					strokeColorID = R.color.calendar_stroke;
				}
				bg = createShapeDrawable(context
						, colorID
						, new float[]{0,0,0,0,0,0,0,0}
						, strokeWidth
						, strokeColorID
						, GradientDrawable.RECTANGLE);
				
				//設定框線顯示邊
				LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{bg});
				int l = 0;
				int t = 0;
				int r = 0;
				int b = 0;
				if(position >= 0 && position <= 6){
					l = -strokeWidth;
				}else{
					l = -strokeWidth;
					t = -strokeWidth;
				}
				if(day_for_week == 0){
					l = 0;
				}
				if (calender.get(Calendar.MONTH) == touchCalender.get(Calendar.MONTH) &&
						calender.get(Calendar.DAY_OF_MONTH) == touchCalender.get(Calendar.DAY_OF_MONTH)) {
					l = 0;
					t = 0;
					r = 0;
					b = 0;
				}
				layerDrawable.setLayerInset(0, l, t, r, b);
				UserInterfaceTool.setBackground(v, layerDrawable);
			}
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (viewGroup == null) {
				viewGroup = parent;
			}
			ViewHolder viewHolder = null;
			if (convertView ==  null) {
				convertView = myInflater.inflate(R.layout.adapter_wv_week2, parent , false);
				
				viewHolder = new ViewHolder();
				
				viewHolder.rl_main = convertView.findViewById(R.id.rl_main);
				UserInterfaceTool.setViewSize(viewHolder.rl_main, ViewGroup.LayoutParams.MATCH_PARENT, (int)(heightPixels * 0.1));
//				int padding = UserInterfaceTool.getPixelFromDpByDevice(getActivity(), 5);
//				viewHolder.rl_main.setPadding(padding, padding, padding, padding);
				UserInterfaceTool.setMarginByDpUnit(viewHolder.rl_main, 5, 5, 5, 5);
				
				viewHolder.tv_week = (TextView) convertView.findViewById(R.id.tv_week);
				UserInterfaceTool.setTextSize(viewHolder.tv_week, 12);
				
				viewHolder.tv_day = (TextView) convertView.findViewById(R.id.tv_day);
				UserInterfaceTool.setTextSize(viewHolder.tv_day, 12);
				
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder)convertView.getTag();
			}
			
			//TODO 取值
			Date date = getDate(position);
			Calendar calender = DateToCalendar(date);
			
			//TODO 設定值
			int year = calender.get(Calendar.YEAR);
			int month = calender.get(Calendar.MONTH) + 1;
			int day_for_month = calender.get(Calendar.DAY_OF_MONTH);
			int day_for_week = calender.get(Calendar.DAY_OF_WEEK) - 1;
			
			viewHolder.tv_day.setText(year + "\n" + month + "/" + day_for_month);
			
			if (position >= 0 && position <= 6) {
				String week[] = getResources().getStringArray(R.array.date_week);
				viewHolder.tv_week.setText(week[day_for_week]);
				viewHolder.tv_week.setVisibility(View.VISIBLE);
			}else {
				viewHolder.tv_week.setVisibility(View.GONE);
			}
			
			//set text color
			if (day_for_week == 0) {
				viewHolder.tv_week.setTextColor(getResources().getColor(R.color.calendar_week));
			}else{
				viewHolder.tv_week.setTextColor(getResources().getColor(R.color.calendar_week2));
			}
			
			if (day_for_week == 0 && calender.get(Calendar.MONTH) == getThisCalendar().get(Calendar.MONTH)) {
				viewHolder.tv_day.setTextColor(getResources().getColor(R.color.calendar_week));
			}else if (calender.get(Calendar.MONTH) != getThisCalendar().get(Calendar.MONTH)) {
				viewHolder.tv_day.setTextColor(getResources().getColor(R.color.calendar_gray));
			}else{
				viewHolder.tv_day.setTextColor(getResources().getColor(R.color.calendar_day));
			}
			
			//設定背景框線顏色
			GradientDrawable bg;
			int strokeWidth = UserInterfaceTool.getPixelFromDpByDevice(context, 1);
			int colorID = 0;//填滿顏色
			int strokeColorID  = 0;//填滿顏色
			
			if(calender.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH) &&
					calender.get(Calendar.DAY_OF_MONTH) == todayCalendar.get(Calendar.DAY_OF_MONTH)){
				strokeWidth = UserInterfaceTool.getPixelFromDpByDevice(context, 3);
				colorID = R.color.white;
				strokeColorID = R.color.calendar_stroke_today;
			}else if (calender.get(Calendar.MONTH) == getThisCalendar().get(Calendar.MONTH)) {
				colorID = R.color.white;
				strokeColorID = R.color.calendar_stroke;
			}else{
				colorID = R.color.white;
				strokeColorID = R.color.calendar_stroke;
			}
			bg = createShapeDrawable(context
					, colorID
					, new float[]{0,0,0,0,0,0,0,0}
					, strokeWidth
					, strokeColorID
					, GradientDrawable.RECTANGLE);
			
			//設定框線顯示邊
			LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{bg});
			int l = 0;
			int t = 0;
			int r = 0;
			int b = 0;
			if(position >= 0 && position <= 6){
				l = -strokeWidth;
			}else{
				l = -strokeWidth;
				t = -strokeWidth;
			}
			if(day_for_week == 0){
				l = 0;
			}
			if (calender.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH) &&
					calender.get(Calendar.DAY_OF_MONTH) == todayCalendar.get(Calendar.DAY_OF_MONTH)) {
				l = 0;
				t = 0;
				r = 0;
				b = 0;
			}
			layerDrawable.setLayerInset(0, l, t, r, b);
			UserInterfaceTool.setBackground(convertView, layerDrawable);
			
			return convertView;
		}
		
	}
	
	/**
	 * <p>建立形狀的圖片並回傳 </p>
	 * @author Robert Chou didi31139@gmail.com
	 * @param context
	 * @param colorID ex:填滿顏色
	 * @param radii The corners are ordered top-left, top-right, bottom-right, bottom-left.
	 * @param strokeWidth 外框畫筆寬度
	 * @param strokeColorID 外框顏色
	 * @param gradientDrawableShape 圖片形狀 ex:GradientDrawable.RECTANGLE
	 * @date 2015/5/26 上午11:50:33
	 * @version
	 */
	public static GradientDrawable createShapeDrawable(Context context,int colorID ,float[] radii,int strokeWidth,int strokeColorID,int gradientDrawableShape){
		GradientDrawable gradientDrawable=new GradientDrawable();
		gradientDrawable.setColor(context.getResources().getColor(colorID));
		if (radii!=null) {
			gradientDrawable.setCornerRadii(radii);
		}
		if (strokeWidth!=0) {
			gradientDrawable.setStroke(strokeWidth, context.getResources().getColor(strokeColorID));
		}
		
		if (gradientDrawableShape!=0) {
			gradientDrawable.setShape(gradientDrawableShape);
		}
		return gradientDrawable;
	}
}
