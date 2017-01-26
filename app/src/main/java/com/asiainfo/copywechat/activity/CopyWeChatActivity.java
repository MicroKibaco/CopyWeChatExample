package com.asiainfo.copywechat.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asiainfo.copywechat.R;
import com.asiainfo.copywechat.fragment.ChatMainTabFragment;
import com.asiainfo.copywechat.fragment.ContactsMainTabFragment;
import com.asiainfo.copywechat.fragment.DiscoverMainTabFragment;
import com.asiainfo.copywechat.view.BadgeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置数据适配器
 * Tab类型App的主界面
 * Viewpager+多布局文件
 * FragmentManager + Fragment
 * Viewpager + Fragment + PagerAdapter
 * TabHost
 *
 *
 * BaddgeView
 * Viewpager
 * onPagerListener
 * TabLine leftMargin
 * Viewpager + FragmentMannager
 */

public class CopyWeChatActivity extends FragmentActivity {

    private ViewPager mViewPager;

    private FragmentPagerAdapter mPagerAdapter;

    private List<Fragment> mDatas;

    private TextView mChatTextView;
    private TextView mContactsTextView;
    private TextView mDiscoverTextView;

    private LinearLayout mChatLl;
    private LinearLayout mDiscoverLl;
    private LinearLayout mContactsLl;

    private ImageView mTabLine;

    private int mScreen1_3;

    private int mCurrentIndex;

    private BadgeView mBadgeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_copy_we_chat);


        initView();
    }

    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mChatTextView = (TextView) findViewById(R.id.id_chat);
        mContactsTextView = (TextView) findViewById(R.id.id_constants);
        mDiscoverTextView = (TextView) findViewById(R.id.id_discover);
        mChatLl = (LinearLayout) findViewById(R.id.id_ll_chat_wapper);
        mContactsLl = (LinearLayout) findViewById(R.id.id_ll_contacts_wapper);
        mDiscoverLl = (LinearLayout) findViewById(R.id.id_ll_discover_wapper);
        mTabLine = (ImageView) findViewById(R.id.id_tabline);

        initTabLine();

        mDatas = new ArrayList<>();

        final ChatMainTabFragment mChatfr = new ChatMainTabFragment();

        ContactsMainTabFragment mContactsfr = new ContactsMainTabFragment();

        final DiscoverMainTabFragment mDiscoverfr = new DiscoverMainTabFragment();

        mDatas.add(mChatfr);
        mDatas.add(mContactsfr);
        mDatas.add(mDiscoverfr);

        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mDatas.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mDatas.get(position);
            }
        };

        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                Log.e("TAG", position + ", " + positionOffset + ", " + positionOffsetPixels + ", ");

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLine.getLayoutParams();

                if (mCurrentIndex == 0 && position == 0) {//0-->1

                    lp.leftMargin = (int) (positionOffset * mScreen1_3 + mCurrentIndex * mScreen1_3);

                }else if (mCurrentIndex == 1 &&position == 0){//1-->0

                    lp.leftMargin = (int) (mCurrentIndex*mScreen1_3+(positionOffset-1)*mScreen1_3);

                }else if (mCurrentIndex == 1 &&position == 1) {//1-->2

                    lp.leftMargin = (int) (mCurrentIndex*mScreen1_3+positionOffset*mScreen1_3);

                }else if (mCurrentIndex == 2 &&position == 1){//2-->1

                    lp.leftMargin = (int) (mCurrentIndex*mScreen1_3+(positionOffset-1)*(mScreen1_3));


                }

                mTabLine.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {

                resetTextView();

                switch (position) {
                    case 0:

                        if (mBadgeView != null) {

                            mChatLl.removeView(mBadgeView);
                        }

                        mBadgeView = new BadgeView(CopyWeChatActivity.this);

                        mBadgeView.setBadgeCount(7);

                        mChatTextView.setTextColor(Color.parseColor("#008000"));

                        mChatLl.addView(mBadgeView);


                        break;

                    case 1:
                        mDiscoverTextView.setTextColor(Color.parseColor("#008000"));


                        break;

                    case 2:
                        mContactsTextView.setTextColor(Color.parseColor("#008000"));


                        break;

                    default:
                        break;

                }

                mCurrentIndex = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initTabLine() {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        mScreen1_3 = metrics.widthPixels / 3;
        ViewGroup.LayoutParams lp = mTabLine.getLayoutParams();
        lp.width = mScreen1_3;
        mTabLine.setLayoutParams(lp);
    }

    private void resetTextView() {

        mChatTextView.setTextColor(Color.BLACK);
        mDiscoverTextView.setTextColor(Color.BLACK);

        mContactsTextView.setTextColor(Color.BLACK);
    }
}
