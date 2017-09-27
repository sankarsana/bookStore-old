package com.bookStore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bookStore.App.MyDialogFragment
import com.bookStore.R
import com.bookStore.model.Book
import com.bookStore.presenter.SelectBookPresenter
import kotlinx.android.synthetic.main.dialog_input_editor.*

class InputCountDialog : MyDialogFragment(), View.OnClickListener {

	private lateinit var book: Book

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedState: Bundle?): View =
			inflater.inflate(R.layout.dialog_input_editor, container)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		btnOk.setOnClickListener(this)
		btnCancel.setOnClickListener(this)
		inputKeyboard.setEditText(inputCount)
		book = SelectBookPresenter.selectedBook
		bookName.text = book.bookName
		inputCount.text.insert(0, "1")
	}

	override fun onClick(v: View) {
		when (v.id) {
			R.id.btnOk -> onBtnOkClick()
			R.id.btnCancel -> dismiss()
		}
	}

	private fun onBtnOkClick() {
		var bookCount = 0
		if (inputCount.text.isNotEmpty())
			bookCount = inputCount.text.toString().toInt()
		(activity as InputCountDialogListener).onDialogPositiveResult(bookCount)
		dismiss()
	}

	interface InputCountDialogListener {

		fun onDialogPositiveResult(bookCount: Int)
	}
}
