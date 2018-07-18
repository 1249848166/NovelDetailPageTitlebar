package su.com.noveldetailtitlebarpage;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.jaeger.library.StatusBarUtil;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NovelDetailTitleBarPage extends CoordinatorLayout implements View.OnClickListener{

    Context context;
    Toolbar toolbar;

    ScrollView scroll;
    View inner;
    ImageView bookImage;
    TextView bookName2;
    TextView authorName2;
    TextView class1;
    TextView class2;
    TextView bookName;
    TextView authorName;
    TextView class0;

    int startColor=Color.parseColor("#555555");
    int toColor=Color.parseColor("#111111");
    String BookName;
    String bookImageUrl;
    String authorname;
    String mainClass;
    String subClass;

    TopRightMenu topRightMenu;

    ImageLoader imageLoader;
    RequestQueue requestQueue;
    ImageLoader.ImageCache imageCache;
    HashMap<String,Bitmap> map;

    public NovelDetailTitleBarPage(Context context) {
        this(context,null);
    }

    public NovelDetailTitleBarPage(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NovelDetailTitleBarPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initTool();
        init();
    }

    private void initTool() {
        requestQueue= Volley.newRequestQueue(context);
        imageCache=new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                if(map==null)
                    return null;
                else
                    return map.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                if(map==null)
                    map=new HashMap<>();
                map.put(url,bitmap);
            }
        };
        imageLoader=new ImageLoader(requestQueue,imageCache);
    }

    private void init() {

        LayoutInflater.from(context).inflate(R.layout.layout_main,this,true);

        bookImage=findViewById(R.id.bookimg);
        bookName2=findViewById(R.id.bookname2);
        authorName2=findViewById(R.id.authorname2);
        class1=findViewById(R.id.class1);
        class2=findViewById(R.id.class2);
        bookName=findViewById(R.id.bookname);
        authorName=findViewById(R.id.authorname);
        class0=findViewById(R.id.class0);

        toolbar=findViewById(R.id.toolbar);
        ((AppCompatActivity)context).setSupportActionBar(toolbar);
        StatusBarUtil.setTranslucentForCoordinatorLayout((Activity) context,0);

        scroll=findViewById(R.id.scroll);
        inner=findViewById(R.id.inner);
        final View bg=findViewById(R.id.bg);
        final View barbg=findViewById(R.id.barbg);
        final ArgbEvaluator evaluator=new ArgbEvaluator();
        final View rl6=findViewById(R.id.rl6);
        scroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                float ratio=(float)Math.abs(scroll.getScrollY())/dp2px(context,250);
                int color= (int) evaluator.evaluate(ratio,startColor,toColor);
                int color2= (int) evaluator.evaluate(ratio+0.3f,startColor,toColor);
                bg.setBackgroundColor(color);
                if(Math.abs(scroll.getScrollY())<dp2px(context,170))
                    barbg.setBackgroundColor(color2);
                bookImage.setScaleX(1f-ratio);
                bookImage.setScaleY(1f-ratio);
                if(bookImage.getScaleX()>1)
                    bookImage.setScaleX(1);
                if(bookImage.getScaleY()>1)
                    bookImage.setScaleY(1);
                rl6.setScaleX(1.5f-ratio);
                rl6.setScaleY(1.5f-ratio);
                if(rl6.getScaleX()>1)
                    rl6.setScaleX(1);
                if(rl6.getScaleY()>1)
                    rl6.setScaleY(1);
                if(Math.abs(scroll.getScrollY())>dp2px(context,130)){
                    if(Math.abs(scroll.getScrollY())>dp2px(context,170)){
                        barbg.setAlpha(ratio+0.5f);
                    }else{
                        barbg.setAlpha(0);
                    }
                }else
                {
                    barbg.setAlpha(0);
                }
            }
        });

        findViewById(R.id.returnbutton).setOnClickListener(this);
        findViewById(R.id.menubutton).setOnClickListener(this);
    }

    public void setTopRightMenu(List<MenuItem> menuItems,int height,int width,boolean showIcon,boolean dimBackground,boolean needAnimationStyle,
                                TopRightMenu.OnMenuItemClickListener listener){
        topRightMenu = new TopRightMenu((Activity) context);
        topRightMenu
                .setHeight(height)     //默认高度480
                .setWidth(width)      //默认宽度wrap_content
                .showIcon(showIcon)     //显示菜单图标，默认为true
                .dimBackground(dimBackground)        //背景变暗，默认为true
                .needAnimationStyle(needAnimationStyle)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)
                .addMenuList(menuItems)
                .setOnMenuItemClickListener(listener);
    }

    private void showTopRightMenu(View v){
        if(topRightMenu!=null)
            topRightMenu.showAsDropDown(v,-225,0);
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
        findViewById(R.id.bg).setBackgroundColor(startColor);
    }

    public void setToColor(int toColor) {
        this.toColor = toColor;
        findViewById(R.id.barbg).setBackgroundColor(toColor);
    }

    public void setBookName(String bookName) {
        BookName = bookName;
        this.bookName.setText(bookName);
        this.bookName2.setText(bookName);
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
        imageLoader.get(bookImageUrl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                bookImage.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("加载图书封面失败",error.toString());
                bookImage.setImageResource(R.drawable.unload120_150);
            }
        });
    }

    public void setAuthorName(String authorName) {
        this.authorname = authorName;
        this.authorName.setText(authorName);
        this.authorName2.setText(authorName);
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
        this.class0.setText(mainClass);
        this.class1.setText(mainClass);
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
        this.class2.setText(subClass);
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.returnbutton) {
            if(!((Activity)context).isTaskRoot())
                ((Activity) context).finish();
        } else if (i == R.id.menubutton) {
            showTopRightMenu(view);
        }
    }
}
