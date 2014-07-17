package com.example.timeful;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {
	private StickyListHeadersListView mList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mList = (StickyListHeadersListView) findViewById(R.id.list);
		TodoAdapter adapter = new TodoAdapter(getTestData(), this);
		mList.setAdapter(adapter);
	}


	private List<TodoItem> getTestData() {
		List<TodoItem> data = new ArrayList<TodoItem>();

		data.add(new TodoItem("Buy Milk", new Date()));

		// get a calendar instance, which defaults to "now"
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		for (int i = 1; i <= 20; i++) {
			// add one day to the date/calendar
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_YEAR, i % 5);
			data.add(new TodoItem("Item: " + i, calendar.getTime()));
		}
		
		data.add(new TodoItem("Item 9", null));
		data.add(new TodoItem("Item 9", null));

		Collections.shuffle(data);
		return data;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
