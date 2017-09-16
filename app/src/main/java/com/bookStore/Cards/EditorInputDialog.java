package com.bookStore.Cards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bookStore.App.MyDialogFragment;
import com.bookStore.R;
import com.development.InputView;
import com.development.NumberKeyboard;

public class EditorInputDialog extends MyDialogFragment implements View.OnClickListener {

	private Button btnAll;
	private TextView bookName;
	private InputView inputCount;
	private int allInStock;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.editor_input_dialog, container);

		btnAll = (Button) view.findViewById(R.id.editor_input_btn_all);
		bookName = (TextView) view.findViewById(R.id.bookName);
		inputCount = (InputView) view.findViewById(R.id.editor_input_count);

		view.findViewById(R.id.editor_input_btn_ok).setOnClickListener(this);
		view.findViewById(R.id.editor_input_btn_cancel).setOnClickListener(this);

		((NumberKeyboard) view.findViewById(R.id.editorInputKeyboard)).setEditText(inputCount);
		btnAll.setOnClickListener(this);

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		bookName.setText(getActivity().getIntent().getStringExtra("bookName"));

		int currentBookCount = getActivity().getIntent().getIntExtra("currentCount", 0);
		if (currentBookCount != 0) {
			inputCount.setText(Integer.toString(currentBookCount));
			inputCount.selectAll();
		} else
			inputCount.setText("");


		allInStock = getActivity().getIntent().getIntExtra("availableCount", 0);
		if (allInStock != 0)
			btnAll.setText(getResources().getString(R.string.allAvailable) + " " + allInStock);
		else
			btnAll.setText(getResources().getString(R.string.allAvailable));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

			case R.id.editor_input_btn_ok:
				int bookCount = 0;
				if (inputCount.getText().length() != 0)
					bookCount = Integer.parseInt(inputCount.getText().toString())
							- getActivity().getIntent().getIntExtra("currentCount", 0);
				if (((IEditorInputDialog) getActivity()).executeOk(bookCount))
					dismiss();
				break;

			case R.id.editor_input_btn_cancel:
				dismiss();
				break;

			case R.id.editor_input_btn_all:
				if (allInStock != 0)
					inputCount.setText(Integer.toString(allInStock));
				break;
		}
	}

	public interface IEditorInputDialog {

		/**
		 * @param additionalCount new count of book
		 * @return true - dialog dismiss, false dialog not dismiss
		 */
		public boolean executeOk(int additionalCount);
	}
}
