# NovelDetailPageTitlebar
## 1.效果图
![效果1](https://github.com/1249848166/NovelDetailPageTitlebar/blob/master/app/GIF.gif) 
![效果2](https://github.com/1249848166/NovelDetailPageTitlebar/blob/master/app/GIF2.gif) 
![效果3](https://github.com/1249848166/NovelDetailPageTitlebar/blob/master/app/GIF3.gif) 
![效果4](https://github.com/1249848166/NovelDetailPageTitlebar/blob/master/app/GIF4.gif) 
![效果5](https://github.com/1249848166/NovelDetailPageTitlebar/blob/master/app/GIF5.gif) 
## 2.说明
我实现了详情页顶部滑动渐变，包括一个选择框。你需要引入后，写下方自己的布局。
## 3.添加引用
在project的build.gradle里面添加内容
```Java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
在app的build.gradle里面添加依赖
```Java
dependencies {
	        implementation 'com.github.1249848166:NovelDetailPageTitlebar:1.0'
	}

```
## 4.代码示例
```Java
package su.com.noveldetailpagetitlebar;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import su.com.noveldetailtitlebarpage.NovelDetailTitleBarPage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NovelDetailTitleBarPage novel=findViewById(R.id.novel);
        novel.setAuthorName("旺旺小小苏2");//设置作者名
        novel.setBookImageUrl("http://bmob-cdn-20220.b0.upaiyun.com/2018/06/28/30df2824402af6f480403224c12f6adc.png");//设置作品封面图片地址
        novel.setBookName("锻体巅峰");//设置书名
        novel.setMainClass("仙侠");//设置小说主类目
        novel.setSubClass("古典仙侠");//设置小说副类目
        novel.setToColor(Color.parseColor("#598721"));//设置标题栏最终颜色
        novel.setStartColor(Color.parseColor("#555555"));//设置标题栏起始颜色
        List<MenuItem> menuItems=new ArrayList<>();//右上角菜单弹出的列表
        menuItems.add(new MenuItem(R.drawable.collect,"收藏"));//添加收藏按钮
        menuItems.add(new MenuItem(R.drawable.share,"分享"));//添加分享按钮
        menuItems.add(new MenuItem(R.drawable.download,"下载"));//添加下载按钮
        //设置右上角菜单，并实现item点击事件
        novel.setTopRightMenu(menuItems, 380, 270, true, true, true, new TopRightMenu.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position) {
                String str="";
                switch (position){
                    case 0:
                        str="是否确定收藏？";
                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        str="是否前往分享？";
                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        str="是否下载全书？";
                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        //下面这些是自己定义的布局，和上面不同。上面是框架内实现的内容，而下面需要自己实现详情页具体布局。
        Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                initYourCustomView();
            }
        });
    }

    void initYourCustomView(){
        @SuppressLint("InflateParams")
        View content= LayoutInflater.from(this).inflate(R.layout.layout_content,null,false);//加载了自己定义的布局
        final RelativeLayout parent=findViewById(su.com.noveldetailtitlebarpage.R.id.parent);//然后将这个布局加入到框架里的预留位置
        parent.addView(content);//加入进去后，后面实现你自己布局的逻辑

        RecyclerView comment_parent=content.findViewById(R.id.comment_parent);

        //因为框架的跟布局是scrollview，scrollview内部嵌套recyclerview会影响滑动的顺畅，需要这些设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        comment_parent.setLayoutManager(layoutManager);
        comment_parent.setHasFixedSize(true);
        comment_parent.setNestedScrollingEnabled(false);

        List<CommentData> commentDataList=new ArrayList<>();
        for(int i=0;i<5;i++){
            CommentData commentData=new CommentData();
            if(i%2==0)
                commentData.setComment("评论内容"+i+":巴拉巴拉说了一大堆，呱呱呱呱呱呱呱呱呱呱呱呱呱呱呱古古怪怪嘎嘎你你你你你你你你你你你你你你你你你你你嘎嘎嘎嘎嘎哈哈哈哈哈哈啦啦啦啦啦啦啦啦啦啊啊啊啊啊啊啊");
            else
                commentData.setComment("评论内容"+i);
            commentData.setHead(BitmapFactory.decodeResource(getResources(),R.drawable.head));
            commentData.setName("用户"+i);
            commentData.setResponseNum("0");
            commentData.setTime(new Date(System.currentTimeMillis()).toString());
            commentDataList.add(commentData);
        }
        MyRecyclerAdapter adapter=new MyRecyclerAdapter(this,commentDataList);
        comment_parent.setAdapter(adapter);
        MyRecyclerDecoration decoration=new MyRecyclerDecoration(1);
        comment_parent.addItemDecoration(decoration);
        comment_parent.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

}

```
其中你需要实现的上面内容中的以下部分
```Java
final NovelDetailTitleBarPage novel=findViewById(R.id.novel);
        novel.setAuthorName("旺旺小小苏2");//设置作者名
        novel.setBookImageUrl("http://bmob-cdn-20220.b0.upaiyun.com/2018/06/28/30df2824402af6f480403224c12f6adc.png");//设置作品封面图片地址
        novel.setBookName("锻体巅峰");//设置书名
        novel.setMainClass("仙侠");//设置小说主类目
        novel.setSubClass("古典仙侠");//设置小说副类目
        novel.setToColor(Color.parseColor("#598721"));//设置标题栏最终颜色
        novel.setStartColor(Color.parseColor("#555555"));//设置标题栏起始颜色
        List<MenuItem> menuItems=new ArrayList<>();//右上角菜单弹出的列表
        menuItems.add(new MenuItem(R.drawable.collect,"收藏"));//添加收藏按钮
        menuItems.add(new MenuItem(R.drawable.share,"分享"));//添加分享按钮
        menuItems.add(new MenuItem(R.drawable.download,"下载"));//添加下载按钮
        //设置右上角菜单，并实现item点击事件
        novel.setTopRightMenu(menuItems, 380, 270, true, true, true, new TopRightMenu.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position) {
                String str="";
                switch (position){
                    case 0:
                        str="是否确定收藏？";
                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        str="是否前往分享？";
                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        str="是否下载全书？";
                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
```
由于使用titlebar，需要去除当前activity默认的actionbar。简单方法如下
复制style文件里的默认AppTheme，添加一个notitle属性
```Java
<resources>

    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>

    <style name="AppNoTitleTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="windowNoTitle">true</item>
    </style>

</resources>

```
然后在mainifest文件中，当前activity处引用
```Java
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="su.com.noveldetailpagetitlebar">

    <uses-permission android:name="android.permission.INTERNET"/>

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity"
            android:theme="@style/AppNoTitleTheme"
            >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```
因为加载小说封面用到网络，需要在manifest文件中添加网络权限。
