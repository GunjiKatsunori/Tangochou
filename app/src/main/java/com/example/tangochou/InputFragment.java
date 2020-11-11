package com.example.tangochou;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public abstract class InputFragment extends Fragment {
    View view;
    String title;
    String actionName;
    Integer selectedId;
    final Fragment fragment = this;

    /**
     *
     * @param title
     * @param actionName
     * @param selectedId
     */
    InputFragment(String title, String actionName, Integer selectedId) {
        this.title = title;
        this.actionName = actionName;
        this.selectedId = selectedId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.input_fragment, null);

        // 表示の編集
        TextView textView =  view.findViewById(R.id.input_text_view);
        textView.setText(title);
        Button button = view.findViewById(R.id.input_button);
        button.setText(actionName);

        // ボタンのクリックリスナー
        setOnButtonClickListener(view);

        return view;
    }

    /**
     * ボタンを押された時のアクション
     */
    public abstract void setOnButtonClickListener(View view);
}
