package com.example.jiaguoqiang20181122.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiaguoqiang20181122.Bean;
import com.example.jiaguoqiang20181122.MySqlite.Dao;
import com.example.jiaguoqiang20181122.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class Frag_One extends Fragment {

    String urlString="http://www.xieast.com/api/news/news.php?page=1";

    ArrayList<Bean.DataBean> list = new ArrayList<Bean.DataBean>();

    private PullToRefreshListView pull;

    int page=0;
    private Myadapter myadapter;
    private ImageLoader instance;
    private String murl;
    private Dao dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_one,container,false);

        //创建dao
        dao = new Dao(getActivity());

        //创建imageloader/全局
        instance = ImageLoader.getInstance();

        //获取控件
        pull = view.findViewById(R.id.pull);

        //创建myadapter
        myadapter = new Myadapter();

        //设置adapter
        pull.setAdapter(myadapter);

        //上下拉加载
        pull.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                list.clear();
                initdata(0);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
         page++;
                initdata(page);
            }
        });
        //刷新
        pull.setMode(PullToRefreshListView.Mode.BOTH);
        initdata(page);
        return view;
    }

    private void initdata(int page) {
        murl = urlString + page;
        if (NewUtils.isConn(getActivity())){
            new MAysnc().execute(murl);
        }else {
            Toast.makeText(getActivity(),"请先连网!",Toast.LENGTH_LONG).show();

            String s = dao.queryData(murl);

            if (!s.isEmpty()){

                Gson gson = new Gson();

                Bean bean = gson.fromJson(s, Bean.class);

                List<Bean.DataBean> query = bean.getData();

                list.addAll(query);

                myadapter.notifyDataSetChanged();

                pull.onRefreshComplete();

            }

        }
    }

    private class MAysnc extends AsyncTask<String,Void,String> {
        //子线程
        @Override
        protected String doInBackground(String... strings) {
            return NewUtils.getJson(strings[0]);
        }
        //主线程
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //添加dao
            dao.insertData(murl,s);
            //创建gson
            Gson gson = new Gson();
            Bean bean = gson.fromJson(s, Bean.class);
            List<Bean.DataBean> data = bean.getData();
            list.addAll(data);
            myadapter.notifyDataSetChanged();
            pull.onRefreshComplete();
        }
    }

    private class Myadapter extends BaseAdapter {

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return position%2;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);

            switch (type){
                case 0:
                    ViewHolder1 vh1 = null;
                    if (convertView == null){
                        convertView= View.inflate(getActivity(),R.layout.item_01,null);
                        vh1 = new ViewHolder1();
                        vh1.t1 = convertView.findViewById(R.id.textview1);
                        vh1.image1=convertView.findViewById(R.id.imageview1);
                        convertView.setTag(vh1);
                    }else {
                        vh1 = (ViewHolder1)convertView.getTag();
                    }
                    vh1.t1.setText(list.get(position).getTitle());
                    instance.displayImage(list.get(position).getThumbnail_pic_s(),vh1.image1);
                    break;
                case 1:
                    ViewHolder2 vh2 = null;
                    if (convertView == null){
                        convertView = View.inflate(getActivity(),R.layout.item_02,null);
                        vh2 = new ViewHolder2();

                        vh2.t2 = convertView.findViewById(R.id.textview2);
                        vh2.image2 =convertView.findViewById(R.id.imageview2);
                        vh2.image3 =convertView.findViewById(R.id.imageview3);
                        vh2.image4 =convertView.findViewById(R.id.imageview4);
                        convertView.setTag(vh2);
                    }else {
                         vh2 = (ViewHolder2)convertView.getTag();
                    }
                    vh2.t2.setText(list.get(position).getTitle());
                    instance.displayImage(list.get(position).getThumbnail_pic_s(),vh2.image2);
                    instance.displayImage(list.get(position).getThumbnail_pic_s02(),vh2.image3);
                    instance.displayImage(list.get(position).getThumbnail_pic_s03(),vh2.image4);
                    break;
            }
            return convertView;
        }
    }

    class ViewHolder1{
        public TextView t1;
        public ImageView image1;
    }
    class ViewHolder2{
        public TextView t2;
        public ImageView image2;
        public ImageView image3;
        public ImageView image4;
    }


}
