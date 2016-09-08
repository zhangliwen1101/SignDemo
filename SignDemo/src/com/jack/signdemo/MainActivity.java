package com.jack.signdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
	
	private Button mButton;
	

	
	private GridView mGv_Calendar;
	private TextView mTv_Current_Time;
	private CalendarViewAdapter mCalendarViewAdapter;
	private Button mSign;
	private ImageView mDialogClose;
	
	private ArrayList<Integer> mSignList = new ArrayList<Integer>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mButton = (Button) findViewById(R.id.button);
		
		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showSignDialog();
			}
		});
		
		for (int i = 0; i < 10; i++) {
			if (i % 2 == 0) {
				mSignList.add(1);
			} else {
				mSignList.add(-1);
			}
		}
	}
	
	
	/**
	 * 签到的dialog
	 */
	private void showSignDialog() {
		LayoutInflater inflater = LayoutInflater.from(this);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_sign, null);
		final Dialog dialog = new AlertDialog.Builder(this).create();
		dialog.show();
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setContentView(layout);
		
		mTv_Current_Time = (TextView) layout.findViewById(R.id.tv_current_time);
		mGv_Calendar = (GridView) layout.findViewById(R.id.gv_calendar);
		mSign = (Button) layout.findViewById(R.id.btn_sign);
		mDialogClose = (ImageView) layout.findViewById(R.id.iv_close);
		
		mTv_Current_Time.setText(Utils.getTime(System.currentTimeMillis()));
		mCalendarViewAdapter = new CalendarViewAdapter(this, Utils.getCurrentMonthDay(), mTv_Current_Time);
		mGv_Calendar.setAdapter(mCalendarViewAdapter);
		mCalendarViewAdapter.onRefresh(getCurrentTimePosition() - 2, -1);
		mDialogClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		mSign.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCalendarViewAdapter.onRefresh(getCurrentTimePosition() - 1, 1);
//				MyToast.show(MainActivity.this,"签到成功!!!");
			}
		});
		
	}
	
	/**
	 * 获取当前时间
	 *
	 * @return
	 */
	public int getCurrentTime() {
		return Utils.getDayOfMonth();
	}
	
	/**
	 * 获取当前日前的位置
	 *
	 * @return
	 */
	public int getCurrentTimePosition() {
		int position;
		if (Utils.getCurrentMonthStart() == 7) {
			position = 0;
		} else
			position = Utils.getCurrentMonthStart();
		return getCurrentTime() + position;
		
	}
	
	
	private class CalendarViewAdapter extends BaseAdapter {
		
		private Context mContext;
		private int mCountDay = 42;
		private int mCurrent_mouth_Countday;
		private int mCurrent_Week;
		private ViewHolder mViewHolder;
		
		private ArrayList<Integer> mSignFlags;
		
		
		public CalendarViewAdapter(Context context, int countday, View view) {
			this.mContext = context;
			this.mCurrent_Week = Utils.getCurrentMonthStart();
			this.mCurrent_mouth_Countday = countday;
			mSignFlags = new ArrayList<Integer>();
			
			for (int i = 0; i < mCurrent_Week; i++) {
				mSignFlags.add(i, 0);
			}
			mSignFlags.addAll(mSignList);
			
			for (int i = mSignFlags.size(); i <mCountDay; i++) {
				mSignFlags.add(i, 0);
			}
			
		}
		
		
		@Override
		public int getCount() {
			return mCountDay;
		}
		
		@Override
		public Object getItem(int position) {
			return null;
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				mViewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.item_calendar, null);
				mViewHolder.mTv_calendar_day = (TextView) convertView
						.findViewById(R.id.tv_calendar_day);
				mViewHolder.mFl_calendar = (FrameLayout) convertView.findViewById(R.id.fl_calendar);
				convertView.setTag(mViewHolder);
			} else
				mViewHolder = (ViewHolder) convertView.getTag();
			
			if (mCurrent_Week == 7 && (position+1) <= mCurrent_mouth_Countday) {
				mViewHolder.mTv_calendar_day.setText(position +1 + "");
			} else if (position >= mCurrent_Week
					&& position - mCurrent_Week +1 <= mCurrent_mouth_Countday) {
				mViewHolder.mTv_calendar_day.setText(position - mCurrent_Week +1 + "");
				
			}
			
			
			if (1 == mSignFlags.get(position)) {
				mViewHolder.mTv_calendar_day.setBackgroundResource(R.drawable.content_pop_button_icon_02);
			}
			
			if (-1 == mSignFlags.get(position)) {
				mViewHolder.mTv_calendar_day.setBackgroundResource(R.drawable.content_pop_button_icon_01);
			}
			
			return convertView;
		}
		
		public void onRefresh(int position, Integer isClick) {
			
			if (mCurrent_Week == 7 && (position - 6) <= mCurrent_mouth_Countday) {
				
				mSignFlags.set(position, isClick);
				
			} else if (position - 7 >= mCurrent_Week
					&& position - mCurrent_Week - 6 <= mCurrent_mouth_Countday) {
				
				mSignFlags.set(position, isClick);
				
			}
			
			notifyDataSetChanged();
		}
		
		public class ViewHolder {
			TextView mTv_calendar_day;
			FrameLayout mFl_calendar;
		}
	}
}
