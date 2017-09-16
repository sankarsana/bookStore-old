package com.bookStore.Store;

import android.content.ContentValues;
import android.database.Cursor;

import com.bookStore.App.DataBase;
import com.bookStore.App.Utilities;

public class Book {
	public long id;
	public String name;
	public String shortName;
	public Writer writer;
	public Category category;
	public int cost;
	public int count;

	public Book() {
		id = -1;
		writer = new Writer();
		category = new Category();
		cost = 0;
		count = 0;
	}

	public Book(long id, String bookName, String shortName, int cost, int count) {
		this.id = id;
		this.name = bookName;
		this.shortName = shortName;
		this.cost = cost;
		this.count = count;
	}

	public Book(String bookName, String shortName, String cost) {
		this();
		this.name = bookName;
		this.shortName = shortName;
		this.cost = Integer.parseInt(cost);
	}

	public Book(long bookId) {
		Cursor c = DataBase.get().rawQuery("SELECT " +
				"Books._id AS _id, " +
				"Books.bookName AS bookName, " +
				"Books.shortName AS shortName, " +
				"Writers.writer AS writer, " +
				"Categories.category AS category, " +
				"Books.cost AS cost, " +
				"Books.count AS count, " +
				"Books.writerId AS writerId, " +
				"Books.categoryId AS categoryId " +

				" FROM Books, Writers, Categories" +
				" WHERE Books.writerId = Writers._id" +
				" AND Books.categoryId = Categories. _id" +
				" AND Books._id = '" + bookId + "';", null);
		c.moveToFirst();
		id = c.getLong(0);
		name = c.getString(1);
		shortName = c.getString(2);
		writer = new Writer(c.getLong(c.getColumnIndex("writerId"))
				, c.getString(c.getColumnIndex("writer")));
		category = new Category(c.getLong(c.getColumnIndex("categoryId")));
		cost = c.getInt(c.getColumnIndex("cost"));
		count = c.getInt(c.getColumnIndex("count"));
		c.close();
	}

	public static String createShortName(String bookName) {
		String shortName = "";
		String[] words = bookName.toUpperCase().split(" ");
		for (String word : words) {
			if (word.length() > 0) {
				if (Utilities.isInteger(word.substring(0, 1)))
					shortName = shortName + " " + word;
				else
					shortName = shortName + word.substring(0, 1);
			}
		}
		return shortName;
	}

	public String getCostSting() {
		return Integer.toString(cost);
	}

	public void setCategory(int categoryId) {
		category = Categories.getInstance().getCategory(categoryId);
	}

	public long insertToBase() {
		return DataBase.get().insert("Books", "bookName", getContentValues());
	}

	public void updateInBase() {
		DataBase.get().update("Books", getContentValues(), "_id = " + id, null);
	}

	private ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		values.put("bookName", name);
		values.put("shortName", shortName);
		values.put("writerId", writer.getId());
		values.put("categoryId", category.getId());
		values.put("cost", cost);
		values.put("search", " " + name.toLowerCase() + " " + shortName.toLowerCase());
		return values;
	}
}
