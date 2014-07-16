package com.example.timeful;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TodoAdapter extends BaseAdapter {
	private Context context;

	private List<TodoItem> list;
	private LayoutInflater inflater;

	private enum ViewType {
		TODO, HEADER;
	}

	public TodoAdapter(List<TodoItem> list, Context context) {
		this.list = list;
		Collections.sort(list, new TodoComparator());
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public TodoItem getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

//	@Override
//	public int getViewTypeCount() {
//		return ViewType.values().length;
//	}
//
//	@Override
//	public int getItemViewType(int position) {
//		return 1;
//	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		Holder holder = null;
		if (view == null) {
			view = inflater.inflate(R.layout.list_item, null);
			holder = new Holder();
			holder.title = (TextView) view.findViewById(R.id.title);
			holder.due = (TextView) view.findViewById(R.id.due);
			holder.day = (TextView) view.findViewById(R.id.day);
			view.setTag(holder);
		} else {
			holder = (Holder) view.getTag();
		}

		TodoItem item = getItem(position);
		holder.title.setText(item.getTitle());

		Date dueDate = item.getDueDate();
		if (dueDate == null) {
			holder.due.setVisibility(View.GONE);
		} else {
			holder.due.setVisibility(View.VISIBLE);
			holder.due.setText(getDueText(dueDate));
		}

		if (isFirstInDay(position)) {
			holder.day.setVisibility(View.VISIBLE);
			if (dueDate == null) {
				holder.day.setText(context.getResources().getString(R.string.unscheduled_header));
			} else {				
				holder.day.setText(new SimpleDateFormat("EE").format(dueDate));
			}
		} else {
			holder.day.setVisibility(View.GONE);
		}

		return view;
	}

	private boolean isFirstInDay(int position) {
		TodoItem item = getItem(position);

		if (position == 0) {
			return true;
		} else if (item.getDueDate() == null) {
			return getItem(position - 1).getDueDate() != null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(item.getDueDate());
		int today = calendar.get(Calendar.DAY_OF_YEAR);

		TodoItem prev = getItem(position - 1);
		calendar.setTime(prev.getDueDate());
		int prevDay = calendar.get(Calendar.DAY_OF_YEAR);

		return today != prevDay;
	}

	private CharSequence getDueText(Date dueDate) {
		String result = "Due: ";

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dueDate);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);

		return result + hours + ":00";
	}

	private static class Holder {
		TextView title;
		TextView due;
		TextView day;
	}

}
