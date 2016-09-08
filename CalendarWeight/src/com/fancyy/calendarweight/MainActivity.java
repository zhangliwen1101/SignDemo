package com.fancyy.calendarweight;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.fancyy.calendarweight.KCalendar.OnCalendarClickListener;
import com.fancyy.calendarweight.KCalendar.OnCalendarDateChangedListener;

public class MainActivity extends Activity {

	private String date = null;// 设置默认选中的日期  格式为 “2014-04-05” 标准DATE格式   
	private TextView popupwindow_calendar_month;
	private KCalendar calendar;
	private List<String> list = new ArrayList<String>(); //设置标记列表
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		popupwindow_calendar_month = (TextView) findViewById(R.id.popupwindow_calendar_month);
		calendar = (KCalendar) findViewById(R.id.popupwindow_calendar);
		popupwindow_calendar_month.setText(calendar.getCalendarYear() + "年"
				+ calendar.getCalendarMonth() + "月");
		if (null != date) {

			int years = Integer.parseInt(date.substring(0,
					date.indexOf("-")));
			int month = Integer.parseInt(date.substring(
					date.indexOf("-") + 1, date.lastIndexOf("-")));
			popupwindow_calendar_month.setText(years + "年" + month + "月");

			calendar.showCalendar(years, month);
			calendar.setCalendarDayBgColor(date,
					R.drawable.calendar_date_focused);				
		}


		list.add("2015-05-01");
		list.add("2015-05-02");
		calendar.addMarks(list, 0);

		//监听所选中的日期
		calendar.setOnCalendarClickListener(new OnCalendarClickListener() {

			public void onCalendarClick(int row, int col, String dateFormat) {
				int month = Integer.parseInt(dateFormat.substring(
						dateFormat.indexOf("-") + 1,
						dateFormat.lastIndexOf("-")));

				if (calendar.getCalendarMonth() - month == 1//跨年跳转
						|| calendar.getCalendarMonth() - month == -11) {
					calendar.lastMonth();

				} else if (month - calendar.getCalendarMonth() == 1 //跨年跳转
						|| month - calendar.getCalendarMonth() == -11) {
					calendar.nextMonth();

				} else {
					list.add(dateFormat);
					calendar.addMarks(list, 0);
					calendar.removeAllBgColor(); 
					calendar.setCalendarDayBgColor(dateFormat,
							R.drawable.calendar_date_focused);
					date = dateFormat;//最后返回给全局 date
				}
			}
		});

		//监听当前月份
		calendar.setOnCalendarDateChangedListener(new OnCalendarDateChangedListener() {
			public void onCalendarDateChanged(int year, int month) {
				popupwindow_calendar_month
				.setText(year + "年" + month + "月");
			}
		});
	}

}
