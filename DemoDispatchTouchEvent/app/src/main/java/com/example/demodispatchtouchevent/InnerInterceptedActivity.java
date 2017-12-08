package com.example.demodispatchtouchevent;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demodispatchtouchevent.utils.MyUtils;
import com.example.demodispatchtouchevent.wedgit.HoriScrollViewGroup;

import java.util.ArrayList;

public class InnerInterceptedActivity extends AppCompatActivity {
    private static final String TAG = "InnerInterceptedATY";
    private HoriScrollViewGroup mHoriScrollViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_intercepted);
        initView();
    }

    private void initView() {
        mHoriScrollViewGroup = (HoriScrollViewGroup) findViewById(R.id.layout_container);
        final int width = MyUtils.getScreenMetrics(this).widthPixels;
        final int height = MyUtils.getScreenMetrics(this).heightPixels;
        LayoutInflater inflater = getLayoutInflater();

        // add 3 customized view in mHoriScrollViewGroup
        for (int i = 0; i < 3; i++) {
            // 创建cusmized_layout1这种布局的视图
            ViewGroup view = (ViewGroup) inflater.inflate(R.layout.cusmized_layout1,
                    mHoriScrollViewGroup, false);
//            view.getLayoutParams().width = width;
            TextView tv = (TextView) view.findViewById(R.id.tv_tittle);
            tv.setText("page" + i);
            view.setBackgroundColor(Color.rgb(255/(i+1), 255/(i+1), 0));
            // 往mHoriScrollViewGroup里添加这个视图
            mHoriScrollViewGroup.addView(view);
            // 添加listview到view
            createList(view);
        }
    }

    private void createList(ViewGroup group) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("name" + i);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.cusmized_item1, R.id.tv_item, list);
        ListView listView = (ListView) group.findViewById(R.id.list_view);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(InnerInterceptedActivity.this, "clicked item" + position,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
