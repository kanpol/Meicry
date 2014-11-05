package com.example.CameraDemo;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.*;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.example.bean.MaskEffectBean;
import com.example.memorycache.ImageCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.support.v4.view.ViewPager.OnPageChangeListener;

public class DisplayActivity extends Activity{

    private ViewFlipper flipper;
    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;

    private int imageIds[] = {R.drawable.a, R.drawable.b, R.drawable.c};

    private List<ImageView> indicatorList = new ArrayList<ImageView>();
    private int pageIndex = 0;

    private GestureDetector gestureDetector = new GestureDetector(new ThemeOnGestureListener());

    private ViewPager pager;
    private List<View> pages = new ArrayList<View>();
    View hotTheme;

    private Timer timer;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.display_page);

        inflater = getLayoutInflater();

        hotTheme = inflater.inflate(R.layout.hot_theme, null);

        relativeLayout = (RelativeLayout) hotTheme.findViewById(R.id.layout_theme_recommend);
        linearLayout = (LinearLayout) hotTheme.findViewById(R.id.layout_indicator);
        flipper = (ViewFlipper) hotTheme.findViewById(R.id.vf_theme_recommend);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        for (int i = 0; i < imageIds.length; i++) {
            ImageView image = new ImageView(this);
            image.setImageResource(imageIds[i]);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            flipper.addView(image, layoutParams);
        }


        for (int i = 0; i < imageIds.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            params.leftMargin = 10;
            params.rightMargin = 10;
            ImageView indicator = new ImageView(this);
            indicator.setImageResource(R.drawable.indicator);
            linearLayout.addView(indicator, params);

            indicatorList.add(indicator);
        }

        indicatorList.get(0).setImageResource(R.drawable.indicator_focused);

        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });


        ListView maskEffectList = (ListView) hotTheme.findViewById(R.id.list_mask_effect);
        Log.i("xxxxxxxxx", String.valueOf(maskEffectList));
        maskEffectList.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return  LayoutInflater.from(parent.getContext()).inflate(R.layout.mask_effect_list_item, null);
            }
        });

        
        View  personalHome = getLayoutInflater().inflate(R.layout.personal_home, null);
        ListView listHome = (ListView) personalHome.findViewById(R.id.list_home);

        listHome.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (position == 0)
                    return LayoutInflater.from(parent.getContext()).inflate(R.layout.home_profile, null);

                return  LayoutInflater.from(parent.getContext()).inflate(R.layout.home_photo_item, null);
            }
        });

        pages.add(hotTheme);
        pages.add(personalHome);
        pager = (ViewPager) findViewById(R.id.content_pager);
        pager.setAdapter(new ContentPagerAdapter(pages));
        pager.setOnPageChangeListener(new ChangePagerListener());


        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        flipper.setInAnimation(AnimationUtils.loadAnimation(
                                DisplayActivity.this, R.anim.push_right_in));
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(
                                DisplayActivity.this, R.anim.push_left_out));
                        flipper.showNext();
                        refreshIndicator(++pageIndex % imageIds.length);

                    }
                });
            }
        }, 5000, 10000);

    }


    private void refreshIndicator(int index) {
        for (int i = 0; i < imageIds.length; i++) {
            if (i == index) {
                indicatorList.get(i).setImageResource(R.drawable.indicator_focused);
            } else {
                indicatorList.get(i).setImageResource(R.drawable.indicator);
            }
        }
    }

    class ThemeOnGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            final int distanceX = 50;
            if (e1.getX() - e2.getX() > distanceX) {
                flipper.showNext();
                refreshIndicator(++pageIndex % imageIds.length);
            } else {
                flipper.showPrevious();
                refreshIndicator((--pageIndex + imageIds.length) % imageIds.length);
            }

            return  true;
        }
    }


    class ContentPagerAdapter extends PagerAdapter {
        List<View> viewList;

        ContentPagerAdapter(List<View> views) {
            viewList = views;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = viewList.get(position);
            if (view.getParent() != null) {
                container.removeView(view);
            }
            container.addView(view, position);
            return view;
        }
    }

    class ChangePagerListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i2) {

        }

        @Override
        public void onPageSelected(int i) {
            if (i == 0) {

            } else {

            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }

    }


    class MaskEffectListAdapter extends BaseAdapter {
        private List<MaskEffectBean> maskEffectBeanList;


        public MaskEffectListAdapter(List<MaskEffectBean> maskEffectBeanList) {
            this.maskEffectBeanList = maskEffectBeanList;
        }

        @Override
        public int getCount() {
            return maskEffectBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.mask_effect_list_item, null);
                holder.mask_title = (TextView) convertView.findViewById(R.id.tv_mask_title);
                holder.people_count = (TextView) convertView.findViewById(R.id.tv_mask_people_count);
                holder.picture_count = (TextView) convertView.findViewById(R.id.tv_mask_picture_count);
                holder.effect_original = (ImageView) convertView.findViewById(R.id.iv_effect_original);
                holder.effect_a = (ImageView) convertView.findViewById(R.id.iv_effect_a);
                holder.effect_b = (ImageView) convertView.findViewById(R.id.iv_effect_b);
                holder.effect_c = (ImageView) convertView.findViewById(R.id.iv_effect_c);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            MaskEffectBean bean = maskEffectBeanList.get(position);
            holder.mask_title.setText(bean.getTitle());
            holder.people_count.setText(bean.getPeopleCount());
            holder.picture_count.setText(bean.getPictureCount());
            ImageCache.setBitmapToImageView(bean.getImg_orig_url(), holder.effect_original);
            ImageCache.setBitmapToImageView(bean.getImg_effect_a(), holder.effect_a);
            ImageCache.setBitmapToImageView(bean.getImg_effect_b(), holder.effect_b);
            ImageCache.setBitmapToImageView(bean.getImg_effect_c(), holder.effect_c);

            return convertView;
        }


        public void addItem(MaskEffectBean bean) {
            maskEffectBeanList.add(bean);
        }

        class ViewHolder {
            TextView mask_title;
            TextView people_count;
            TextView picture_count;
            ImageView effect_original;
            ImageView effect_a;
            ImageView effect_b;
            ImageView effect_c;
        }
    }
}
