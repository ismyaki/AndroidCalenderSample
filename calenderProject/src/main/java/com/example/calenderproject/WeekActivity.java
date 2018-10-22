package com.example.calenderproject;

import java.util.Calendar;
import java.util.Date;

import pro.realtouchapp.wang.lib.calender.WeekView;

import tools.userinterface.UserInterfaceTool;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeekActivity extends Activity{
	
	private int heightPixels = 0;
	private int widthPixels = 0;
	
	WeekView weekView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_week);
		
		widthPixels = UserInterfaceTool.getScreenWidthPixels(this);
		heightPixels = UserInterfaceTool.getScreenHeightPixels(this);
		
		initWeekView();
		initButton();
	}
	
	private void initButton(){
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO 下一周
				weekView.nextWeek();
			}
		});
		findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO 上一周
				weekView.backWeek();
			}
		});
		findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO 這一周
				weekView.thisWeek();
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
				weekView.gotoWeek(year, month, day);
			}
		});
	}
	
	private void initWeekView(){
		weekView = (WeekView) findViewById(R.id.weekView);
		SampleAdapter adapter = new SampleAdapter(this);
		weekView.setAdapter(adapter);
	}
	
	private class SampleAdapter extends WeekView.Adapter{
		private Context context;
		private Calendar todayCalendar = Calendar.getInstance();
		
		public SampleAdapter(Context context) {
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
				viewHolder.tv_day.setTextColor(getResources().getColor(R.color.white));
				
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
			
			String s = year + "\n" + month + "/" + day_for_month + "\n(" + day_for_week + ")";
			viewHolder.tv_day.setText(s); 
			
			if(calender.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH) && 
					calender.get(Calendar.DAY_OF_MONTH) == todayCalendar.get(Calendar.DAY_OF_MONTH)){
				viewHolder.tv_day.setTextColor(getResources().getColor(R.color.yellow));
			}else if (calender.get(Calendar.MONTH) == getThisCalendar().get(Calendar.MONTH)) {
				viewHolder.tv_day.setTextColor(getResources().getColor(R.color.blue));
			}else{
				viewHolder.tv_day.setTextColor(getResources().getColor(R.color.gray));
			}
			
			return convertView;
		}
		
	}
}
