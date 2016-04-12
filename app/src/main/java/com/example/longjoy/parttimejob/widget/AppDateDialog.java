/**   
 * @{#} AppBuilderAlertDialog.java Create on 2014-1-11 PM 5:36:16   
 * @author tuxiaohui
 * Copyright (c) 2013 by loongjoy.
 * Effect: The Default Alert Dialog
 */
package com.example.longjoy.parttimejob.widget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.widget.wheel.AbstractWheelTextAdapter;
import com.example.longjoy.parttimejob.widget.wheel.OnWheelScrollListener;
import com.example.longjoy.parttimejob.widget.wheel.WheelView;


/**
 * @{# AppBuilderAlertDialog.java Create on 2014-1-11 下午5:36:16
 * @author xiaohui_tu Effect: The Default Alert Dialog
 */
public class AppDateDialog extends AlertDialog implements OnClickListener {
	private TextView alertTitleView;
	private Button positiveButton; // confirm button
	private Button negativeButton; // cancel button
	private String positiveButtonStr;
	private String negativeButtonStr;
	private Context context;
	private String titleStr;

	TimeSelectListenner listenner;

	WheelView wheelYear;
	WheelView wheelMonth;
	WheelView wheelDay;
	TextView babyInfoSure;
	TextView motherInfoSure;
	TextView babySex;
	TextView babyBirthday;
	TextView planBirthday;
	RadioGroup babySexGroup;
	private String year;
	private String month;
	private String day;
	List<String> listDay;
	YearAdapter dayAdapter;
	String startDate;
	boolean isStart;

	public AppDateDialog(Context context, String titleStr, String positiveButtonStr, String negativeButtonStr,
			int theme, boolean isStart, String startDate, TimeSelectListenner listenner) {
		super(context, theme);
		this.context = context;
		this.listenner = listenner;
		this.isStart = isStart;
		this.titleStr = titleStr;
		this.positiveButtonStr = positiveButtonStr;
		this.negativeButtonStr = negativeButtonStr;
		this.startDate = startDate;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_date_dialog);
		initViewIds();
	}

	private void initViewIds() {
		// click other place for missing dialog
		this.setCanceledOnTouchOutside(false);
		alertTitleView = (TextView) findViewById(R.id.alertTitleView);
		positiveButton = (Button) findViewById(R.id.positiveButton);
		negativeButton = (Button) findViewById(R.id.negativeButton);
		negativeButton.setVisibility(View.VISIBLE);

		if (titleStr != null) {
			alertTitleView.setText(titleStr);
		}

		if (positiveButtonStr != null) {
			positiveButton.setText(positiveButtonStr);
		}

		if (negativeButtonStr != null) {
			negativeButton.setText(negativeButtonStr);
		}

		wheelYear = (WheelView) findViewById(R.id.wheelYear2);
		wheelMonth = (WheelView) findViewById(R.id.wheelMonth2);
		wheelDay = (WheelView) findViewById(R.id.wheelDay2);

		List<String> listYear = new ArrayList<String>();
		int j = 1900;
		for (int i = 0; i < 200; i++) {
			listYear.add(String.valueOf(j));
			j++;
		}
		wheelYear.setViewAdapter(new YearAdapter(context, listYear));
		List<String> listMonth = new ArrayList<String>();
		for (int i = 1; i < 13; i++) {
			if (i < 10) {
				listMonth.add("0" + i);
			} else {
				listMonth.add(String.valueOf(i));
			}

		}
		wheelMonth.setViewAdapter(new YearAdapter(context, listMonth));
		wheelMonth.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {
			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				int month = wheel.getCurrentItem() + 1;
				if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
					listDay.clear();
					for (int i = 1; i < 32; i++) {
						if (i < 10) {
							listDay.add("0" + i);
						} else {
							listDay.add(String.valueOf(i));
						}
					}
				} else if (month == 4 || month == 6 || month == 9 || month == 11) {
					listDay.clear();
					for (int i = 1; i < 31; i++) {
						if (i < 10) {
							listDay.add("0" + i);
						} else {
							listDay.add(String.valueOf(i));
						}
					}
				} else if (month == 2) {
					int cy = wheelYear.getCurrentItem() + 1990;
					int j = 28;
					if (cy % 4 == 0 && cy % 100 != 0) {
						j = 29 + 1;
					}
					listDay.clear();
					for (int i = 1; i < j; i++) {
						if (i < 10) {
							listDay.add("0" + i);
						} else {
							listDay.add(String.valueOf(i));
						}
					}
				}
				dayAdapter.updata(listDay);
				wheelDay.setCurrentItem(0);
			}
		});
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Date date;
		date = new Date();
		wheelYear.setCurrentItem(date.getYear());
		wheelMonth.setCurrentItem(date.getMonth());
		listDay = new ArrayList<String>();
		int month = wheelMonth.getCurrentItem() + 1;
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			listDay.clear();
			for (int i = 1; i < 32; i++) {
				if (i < 10) {
					listDay.add("0" + i);
				} else {
					listDay.add(String.valueOf(i));
				}
			}
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			listDay.clear();
			for (int i = 1; i < 31; i++) {
				if (i < 10) {
					listDay.add("0" + i);
				} else {
					listDay.add(String.valueOf(i));
				}
			}
		} else if (month == 2) {
			int cy = wheelYear.getCurrentItem() + 1990;
			int dj = 28;
			if (cy % 4 == 0 && cy % 100 != 0) {
				dj = 29 + 1;
			}
			listDay.clear();
			for (int i = 1; i < dj; i++) {
				if (i < 10) {
					listDay.add("0" + i);
				} else {
					listDay.add(String.valueOf(i));
				}
			}
		}
		dayAdapter = new YearAdapter(context, listDay);
		wheelDay.setViewAdapter(dayAdapter);
		wheelDay.setCurrentItem(date.getDate() - 1);

		positiveButton.setOnClickListener(this);
		negativeButton.setOnClickListener(this);
	}

	// Dialog listener interface
	public interface AppAlertDialogListener {
		public void onClick(View view);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.positiveButton:
			if (isStart) {
				year = String.valueOf(wheelYear.getCurrentItem() + 1900);
				if (wheelMonth.getCurrentItem() + 1 < 10) {
					month = "0" + (wheelMonth.getCurrentItem() + 1);
				} else {
					month = String.valueOf(wheelMonth.getCurrentItem() + 1);
				}
				if (wheelDay.getCurrentItem() + 1 < 10) {
					day = "0" + (wheelDay.getCurrentItem() + 1);
				} else {
					day = String.valueOf(wheelDay.getCurrentItem() + 1);
				}
				/*if (getTwoDay(year + "-" + month + "-" + day) < 0) {
					Toast.makeText(context, "开始日期不能小于当前日期", Toast.LENGTH_SHORT).show();
					return;
				}*/
				listenner.callBack(year + "-" + month + "-" + day);
			} else {
				year = String.valueOf(wheelYear.getCurrentItem() + 1900);
				if (wheelMonth.getCurrentItem() + 1 < 10) {
					month = "0" + (wheelMonth.getCurrentItem() + 1);
				} else {
					month = String.valueOf(wheelMonth.getCurrentItem() + 1);
				}
				if (wheelDay.getCurrentItem() + 1 < 10) {
					day = "0" + (wheelDay.getCurrentItem() + 1);
				} else {
					day = String.valueOf(wheelDay.getCurrentItem() + 1);
				}

				if (getEndDay(year + "-" + month + "-" + day, startDate) <= 0) {
					Toast.makeText(context, "结束日期不能小于开始日期", Toast.LENGTH_SHORT).show();
					return;
				}
				// if (getTwoDay(year + "-" + month + "-" + day) < 0) {
				// Toast.makeText(context, "日期不能小于当前日期",
				// Toast.LENGTH_SHORT).show();
				// return;
				// }
				listenner.callBack(year + "-" + month + "-" + day);
			}
			dismiss();
			break;
		case R.id.negativeButton:
			dismiss();
			break;
		default:
			break;
		}
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public long getTwoDay(String sj1) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = new Date();
		String s = myFormatter.format(date2);
		long day = 0;
		try {
			Date dates = myFormatter.parse(s);
			Date date = myFormatter.parse(sj1);
			day = date.getTime() - dates.getTime();
		} catch (Exception e) {
			return 0;
		}
		return day;
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public long getEndDay(String sj1, String startDate) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			Date date = myFormatter.parse(sj1);
			Date date1 = myFormatter.parse(startDate);
			day = date.getTime() - date1.getTime();
		} catch (Exception e) {
			return 0;
		}
		return day;
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	private long getStartDay(String sj1, String endDate) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			Date date = myFormatter.parse(sj1);
			Date date1 = myFormatter.parse(endDate);
			day = date.getTime() - date1.getTime();
		} catch (Exception e) {
			return 0;
		}
		return day;
	}

	public class YearAdapter extends AbstractWheelTextAdapter {
		/**
		 * Constructor
		 */
		public List<String> list;

		protected YearAdapter(Context context, List<String> list) {
			super(context, R.layout.wheel_text, NO_RESOURCE);
			this.list = list;
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);

			TextView textCity = (TextView) view.findViewById(R.id.wheel_text);

			textCity.setText(list.get(index));
			return view;
		}

		public int getItemsCount() {
			return list.size();
		}

		public void updata(List<String> list) {
			this.list = list;
			notifyDataInvalidatedEvent();
		}

		@Override
		protected String getItemText(int index) {
			return list.get(index);
		}
	}

	public interface TimeSelectListenner {
		public void callBack(String date);
	}
}
