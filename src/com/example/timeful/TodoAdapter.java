package com.example.timeful;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TodoAdapter extends BaseAdapter implements
		StickyListHeadersAdapter {
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

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ViewHolder holder = null;
		if (view == null) {
			view = inflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.title = (TextView) view.findViewById(R.id.title);
			holder.due = (TextView) view.findViewById(R.id.due);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
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

		return view;
	}

	private CharSequence getDueText(Date dueDate) {
		String result = "Due: ";

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dueDate);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);

		return result + hours + ":00";
	}
	
	private static class HeaderHolder {
		TextView text;
	}
	
	

	private static class ViewHolder {
		TextView title;
		TextView due;
	}

	@Override
	public View getHeaderView(int position, View view, ViewGroup parent) {
		HeaderHolder holder = null;
		if (view == null) {
			view = inflater.inflate(R.layout.list_header, null);
			holder = new HeaderHolder();
			holder.text = (TextView) view.findViewById(R.id.text);
			view.setTag(holder);
		} else {
			holder = (HeaderHolder) view.getTag();
		}

		TodoItem item = getItem(position);
		if(item.getDueDate() == null){
			holder.text.setText(context.getResources().getString(R.string.unscheduled_header));
		}else {
			holder.text.setText(new SimpleDateFormat("EE").format(item.getDueDate()));
		}

		return view;
	}

	@Override
	public long getHeaderId(int position) {
		TodoItem item = getItem(position);
		if (item.getDueDate() == null) {
			return -1;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(item.getDueDate());
			return calendar.get(Calendar.DAY_OF_YEAR);
		}
	}

}
