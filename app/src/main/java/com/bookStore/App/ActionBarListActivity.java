package com.bookStore.App;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.bookStore.R;

public abstract class ActionBarListActivity extends MyActionBarActivity {

	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		ListAdapter adapter = getListAdapter();
		if (adapter instanceof CursorSearchAdapter)
			((CursorSearchAdapter) adapter).refresh();
	}

	protected ListView getListView() {
		if (mListView == null) {
			mListView = (ListView) findViewById(android.R.id.list);
			mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
					onListItemClick(getListView(), view, i, l);
				}
			});
		}
		return mListView;
	}

	protected ListAdapter getListAdapter() {
		ListAdapter adapter = getListView().getAdapter();
		if (adapter instanceof HeaderViewListAdapter) {
			return ((HeaderViewListAdapter) adapter).getWrappedAdapter();
		} else {
			return adapter;
		}
	}

	protected void setListAdapter(ListAdapter adapter) {
		getListView().setAdapter(adapter);
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return initializeMySearchView(menu);
	}

	protected boolean initializeMySearchView(Menu menu) {
		MenuItem searchItem = menu.findItem(R.id.my_action_search);
		if (searchItem == null)
			return false;
		MySearchView searchView = (MySearchView) MenuItemCompat.getActionView(searchItem);
		searchView.setHint(searchItem.getTitleCondensed());
		searchView.setDataOfSearch((IDataOfSearch) getListView().getAdapter());
		return true;
	}
}
