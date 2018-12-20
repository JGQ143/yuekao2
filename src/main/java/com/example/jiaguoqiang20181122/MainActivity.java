package com.example.jiaguoqiang20181122;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.example.jiaguoqiang20181122.Fragment.Frag_One;
import com.example.jiaguoqiang20181122.Fragment.Frag_Three;
import com.example.jiaguoqiang20181122.Fragment.Frag_Two;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private RadioGroup radio;
    private Frag_One frag_one;
    private Frag_Two frag_two;
    private Frag_Three frag_three;
    private DrawerLayout dr;
    private ListView listview;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取侧拉控件
        dr = findViewById(R.id.dr);
        listview = findViewById(R.id.listview);

        ll = findViewById(R.id.ll);

        //创建侧拉列表

        ArrayList<String> list = new ArrayList<String>();

        for (int i=0;i<1;i++){
            list.add("日志");
            list.add("帮助");
            list.add("无障碍服务");

        }
        //创建adapter
        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,list);

        listview.setAdapter(myadapter);

        //条目监听

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.hide(frag_one);
                transaction2.hide(frag_two);
                transaction2.hide(frag_three);
                switch (position){
                    case 0:
                        transaction2.show(frag_one);
                        break;
                    case 1:
                        transaction2.show(frag_two);
                        break;
                    case 2:
                        transaction2.show(frag_three);
                        break;
                }
                transaction2.commit();
                dr.closeDrawer(ll);
            }
        });

        //开启

        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();

        //添加fragment

        frag_one = new Frag_One();
        frag_two = new Frag_Two();
        frag_three = new Frag_Three();

        transaction.add(R.id.frag, frag_one);
        transaction.add(R.id.frag, frag_two);
        transaction.add(R.id.frag, frag_three);

        transaction.hide(frag_two);
        transaction.hide(frag_three);

        //提交
        transaction.commit();

        //获取按钮

        radio = findViewById(R.id.radiogroup);

        //默认选中
        radio.check(1);

        //监听

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.hide(frag_one);
                transaction1.hide(frag_two);
                transaction1.hide(frag_three);
                switch (checkedId){
                    case 1:
                        transaction1.show(frag_one);
                        break;
                    case 2:
                        transaction1.show(frag_two);
                        break;
                    case 3:
                        transaction1.show(frag_three);
                        break;
                }
                transaction1.commit();
            }
        });
    }
}
