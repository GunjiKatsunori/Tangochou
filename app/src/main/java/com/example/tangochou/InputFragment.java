package com.example.tangochou;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.model.FolderModel;

public class InputFragment extends Fragment {
    View view;
    String title;
    String actionName;
    FolderModel selectedFolder;

    InputFragment(String title, String actionName, FolderModel selectedFolder) {
        this.title = title;
        this.actionName = actionName;
        this.selectedFolder = selectedFolder;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.input_fragment, null);

        // 表示の編集
        TextView textView =  view.findViewById(R.id.input_text_view);
        textView.setText(title);
        final EditText textBox = view.findViewById(R.id.input_box);
        Button button = view.findViewById(R.id.input_button);
        button.setText(actionName);

        final Fragment fragment = this;
        // ボタンを押すとフォルダ名を更新
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String directory = selectedFolder.getDirectory();
                String originalName = selectedFolder.getName();
                String newName = textBox.getText().toString();
                Integer id = selectedFolder.getParentId();
                // DBを更新
                FolderListPresenter presenter = new FolderListPresenter(getContext());
                presenter.updateFolderName(directory, originalName, newName);
                // フラグメント表示終了
                getFragmentManager()
                        .beginTransaction()
                        .remove(fragment)
                        .commit();
                // 画面に変更を反映
                ((FolderListActivity)getContext()).createView(id);

                // デバッグ
                DBOpenHelper helper = new DBOpenHelper(getContext());
                SQLiteDatabase db = helper.getReadableDatabase();
                Cursor cursor = db.query("folder", new String[]{"id", "path", "name", "directory", "parent_id"}, null, null, null, null, null);
                cursor.moveToFirst();

                for (int i=0; i<cursor.getCount(); i++) {
                    Log.d("test", cursor.getString(1));
                    Log.d("test", cursor.getString(2));
                    cursor.moveToNext();
                }
                // デバッグ終わり
            }
        });

        return view;
    }
}
