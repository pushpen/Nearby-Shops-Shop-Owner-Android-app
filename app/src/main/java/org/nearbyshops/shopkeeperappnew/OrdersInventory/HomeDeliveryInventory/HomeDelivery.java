package org.nearbyshops.shopkeeperappnew.OrdersInventory.HomeDeliveryInventory;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.material.tabs.TabLayout;
import com.wunderlist.slidinglayer.SlidingLayer;
import org.nearbyshops.shopkeeperappnew.Interfaces.NotifySearch;
import org.nearbyshops.shopkeeperappnew.Interfaces.NotifySort;
import org.nearbyshops.shopkeeperappnew.Interfaces.NotifyTitleChanged;
import org.nearbyshops.shopkeeperappnew.OrderHistory.SlidingLayerSort.SlidingLayerSortOrders;
import org.nearbyshops.shopkeeperappnew.R;

public class HomeDelivery extends AppCompatActivity implements NotifyTitleChanged, ViewPager.OnPageChangeListener, NotifySort {


    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @BindView(R.id.tablayout)
    TabLayout tabLayout;


    @BindView(R.id.slidingLayer)
    SlidingLayer slidingLayer;

    public static final String TAG_SLIDING_LAYER = "sliding_layer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_delivery);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        setupSlidingLayer();

    }


    void setupSlidingLayer()
    {

        ////slidingLayer.setShadowDrawable(R.drawable.sidebar_shadow);
        //slidingLayer.setShadowSizeRes(R.dimen.shadow_size);

        if(slidingLayer!=null) {
            slidingLayer.setChangeStateOnTap(true);
            slidingLayer.setSlidingEnabled(true);
            slidingLayer.setPreviewOffsetDistance(15);
            slidingLayer.setOffsetDistance(10);
            slidingLayer.setStickTo(SlidingLayer.STICK_TO_RIGHT);

//            DisplayMetrics metrics = new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(250, ViewGroup.LayoutParams.MATCH_PARENT);

            //slidingContents.setLayoutParams(layoutParams);

            //slidingContents.setMinimumWidth(metrics.widthPixels-50);




            if (getSupportFragmentManager().findFragmentByTag(TAG_SLIDING_LAYER)==null)
            {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.slidinglayerfragment,new SlidingLayerSortOrders(),TAG_SLIDING_LAYER)
                        .commit();
            }
        }
    }



    @OnClick({R.id.icon_sort, R.id.text_sort})
    void sortClick()
    {
        slidingLayer.openLayer(true);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the ShopList/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    // methods for getting fragment reference

    /*public PlaceholderFragment getActiveFragment(ViewPager container, int position) {
        String name = makeFragmentName(container.getId(), position);
        return  getSupportFragmentManager().findFragmentByTag(name);
    }*/

    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }



    @Override
    public void NotifyTitleChanged(String title, int tabPosition) {

        mPagerAdapter.setTitle(title,tabPosition);
    }





    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }




    @Override
    public void onPageSelected(int position) {

        if(position==2||position==3||position==4 || position==5 || position==6)
        {
            handoverButton.setVisibility(View.VISIBLE);


            if(position==3 || position==4  || position==5)
            {
                handoverButton.setText("Filter\nby Delivery");
            }
            else
            {
                handoverButton.setText("Handover\nto Delivery");
            }

        }
        else
        {
            handoverButton.setVisibility(View.GONE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_order_history_hd, menu);


        // Associate searchable configuration with the SearchView
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        MenuItem item = menu.findItem(R.id.action_search);

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {


                Fragment fragment = getSupportFragmentManager()
                        .findFragmentByTag(
                                makeFragmentName(mViewPager.getId(),mViewPager.getCurrentItem())
                        );


                if(fragment instanceof NotifySearch)
                {
                    ((NotifySearch) fragment).endSearchMode();
                }

                return true;
            }
        });




        return true;
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }


    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow

//            Toast.makeText(this,query,Toast.LENGTH_SHORT).show();

//            Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);

            Fragment fragment = getSupportFragmentManager()
                    .findFragmentByTag(
                            makeFragmentName(mViewPager.getId(),mViewPager.getCurrentItem())
                    );


            if(fragment instanceof NotifySearch)
            {
                ((NotifySearch) fragment).search(query);
            }
        }
    }



    @Override
    public void notifySortChanged() {

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(
                        makeFragmentName(mViewPager.getId(),mViewPager.getCurrentItem())
                );

        if(fragment instanceof NotifySort)
        {
            ((NotifySort)fragment).notifySortChanged();
        }
    }




    public interface HandoverClicked
    {
        void handoverClicked();
    }






    @BindView(R.id.handover_button)
    TextView handoverButton;

    @OnClick(R.id.handover_button)
    void handoverClick()
    {

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(makeFragmentName(mViewPager.getId(),mViewPager.getCurrentItem()));

        if(fragment instanceof HandoverClicked)
        {
            ((HandoverClicked)fragment).handoverClicked();
        }



//        FragmentManager fm = getSupportFragmentManager();
//        DeliveryGuyListDialog dialog = new DeliveryGuyListDialog();

//        dialog.show(fm, "rate");

    }


}
