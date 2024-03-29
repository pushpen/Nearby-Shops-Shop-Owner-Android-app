package org.nearbyshops.shopkeeperappnew.ShopAdminHome;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import org.nearbyshops.shopkeeperappnew.API.ShopService;
import org.nearbyshops.shopkeeperappnew.DaggerComponentBuilder;
import org.nearbyshops.shopkeeperappnew.EditProfile.EditProfile;
import org.nearbyshops.shopkeeperappnew.EditProfile.FragmentEditProfile;
import org.nearbyshops.shopkeeperappnew.EditShop.EditShop;
import org.nearbyshops.shopkeeperappnew.EditShop.EditShopFragment;
import org.nearbyshops.shopkeeperappnew.Interfaces.NotifyAboutLogin;
import org.nearbyshops.shopkeeperappnew.Model.Shop;
import org.nearbyshops.shopkeeperappnew.Prefrences.PrefGeneral;
import org.nearbyshops.shopkeeperappnew.Prefrences.PrefLogin;
import org.nearbyshops.shopkeeperappnew.Prefrences.PrefShopHome;
import org.nearbyshops.shopkeeperappnew.R;
import org.nearbyshops.shopkeeperappnew.ShopHome.ShopHome;
import org.nearbyshops.shopkeeperappnew.Transactions.Transactions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;

public class ShopAdminHomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {




    @Inject
    ShopService shopService;

    @BindView(R.id.notice) TextView notice;

    @BindView(R.id.shop_open_status) ImageView shopOpenStatus;
    @BindView(R.id.header) TextView headerText;
    @BindView(R.id.shop_open_switch) Switch shopOpenSwitch;
    @BindView(R.id.progress_switch) ProgressBar progressSwitch;

    @BindView(R.id.current_dues) TextView currentDues;
    @BindView(R.id.credit_limit) TextView creditLimit;
    @BindView(R.id.low_balance_message) TextView lowBalanceMessage;


    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;





    public ShopAdminHomeFragment() {
        DaggerComponentBuilder.getInstance()
                .getNetComponent()
                .Inject(this);
    }





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.activity_shop_admin_home, container, false);
        ButterKnife.bind(this, rootView);

        setupSwipeContainer();


        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(ContextCompat.getColor(getActivity(), R.color.white));
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//


        bindAllFields();

//        checkAccountActivation();

        if (savedInstanceState == null)
        {

            swipeContainer.post(new Runnable() {
                @Override
                public void run() {

                    onRefresh();
                }
            });
        }



        return rootView;
    }



    void setupSwipeContainer()
    {

        if(swipeContainer!=null) {

            swipeContainer.setOnRefreshListener(this);
            swipeContainer.setColorSchemeResources(
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
        }

    }



    @Override
    public void onRefresh() {


        refreshShop();
    }






    @OnClick(R.id.billing_info)
    void billingInfoClick()
    {
        Intent intent = new Intent(getActivity(), Transactions.class);
        startActivity(intent);
    }






    @OnClick(R.id.image_edit_profile)
    void editProfileClick()
    {
//        intent.putExtra(EditShopFragment.SHOP_ADMIN_INTENT_KEY,shopAdmin);
//        ShopAdmin shopAdmin = UtilityLogin.getShopAdmin(this);

//        Intent intent = new Intent(getActivity(), EditShopAdmin.class);
//        intent.putExtra(EditShopAdminFragment.EDIT_MODE_INTENT_KEY,EditShopAdminFragment.MODE_UPDATE);
//        startActivity(intent);




        Intent intent = new Intent(getActivity(), EditProfile.class);
        intent.putExtra(FragmentEditProfile.EDIT_MODE_INTENT_KEY, FragmentEditProfile.MODE_UPDATE);
//        intent.putExtra("user_role", User.ROLE_SHOP_ADMIN_CODE);
        startActivity(intent);

    }





    @OnClick(R.id.image_edit_shop)
    void editSHopClick()
    {
        if(PrefShopHome.getShop(getActivity())==null)
        {
            // check online for shop exist or not
            // if shop exist save it in shop home and open it in edit mode
            // if shop does not exist then open edit shop fragment in ADD mode

            /*if(UtilityLogin.getShopAdmin(this)==null)
            {
                return;
            }


            int id = UtilityLogin.getShopAdmin(this).getShopAdminID();*/


            swipeContainer.post(new Runnable() {
                @Override
                public void run() {

                    swipeContainer.setRefreshing(true);
                }
            });




            Call<Shop> call = shopService.getShopForShopAdmin(
                    PrefLogin.getAuthorizationHeaders(getActivity())
            );


            call.enqueue(new Callback<Shop>() {
                @Override
                public void onResponse(Call<Shop> call, Response<Shop> response) {

                    if(response.code()==200)
                    {
                        PrefShopHome.saveShop(response.body(),getActivity());


                        // Open Edit fragment in edit mode
                        Intent intent = new Intent(getActivity(), EditShop.class);
                        intent.putExtra(EditShopFragment.EDIT_MODE_INTENT_KEY, EditShopFragment.MODE_UPDATE);
                        startActivity(intent);


                    }
                    else if(response.code()==204)
                    {

                            Intent intent = new Intent(getActivity(), EditShop.class);
                            intent.putExtra(EditShopFragment.EDIT_MODE_INTENT_KEY, EditShopFragment.MODE_ADD);
                            startActivity(intent);
                    }
                    else if(response.code()==401||response.code()==403)
                    {

                        Toast.makeText(getActivity(),"Not Permitted !",Toast.LENGTH_SHORT)
                                .show();
                    }


                    swipeContainer.setRefreshing(false);

                }

                @Override
                public void onFailure(Call<Shop> call, Throwable t) {



                    swipeContainer.setRefreshing(false);


                }
            });


        }
        else
        {

            //     open edit shop in edit mode
            Intent intent = new Intent(getActivity(), EditShop.class);
            intent.putExtra(EditShopFragment.EDIT_MODE_INTENT_KEY, EditShopFragment.MODE_UPDATE);
            startActivity(intent);

            swipeContainer.setRefreshing(false);
        }




    }




    @OnClick(R.id.image_shop_dashboard)
    void shopDashboardClick()
    {
        if(PrefShopHome.getShop(getActivity())==null)
        {
            Call<Shop> call = shopService.getShopForShopAdmin(
                    PrefLogin.getAuthorizationHeaders(getActivity())
            );


            call.enqueue(new Callback<Shop>() {
                @Override
                public void onResponse(Call<Shop> call, Response<Shop> response) {

                    if(response.code()==200)
                    {
                        PrefShopHome.saveShop(response.body(),getActivity());

//                        Toast.makeText(ShopAdminHome.this,"Shop ID : "  + String.valueOf(response.body().getShopID()),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), ShopHome.class);
                        startActivity(intent);

                    }
                    else if(response.code()==204)
                    {
                        Toast.makeText(getActivity(),"You have not created Shop yet",Toast.LENGTH_SHORT)
                                .show();
                    }
                    else if(response.code()==401||response.code()==403)
                    {
                        Toast.makeText(getActivity(),"Not Permitted. Your account is not activated !",Toast.LENGTH_SHORT)
                                .show();
                    }
                    else
                    {
                        showToastMessage("Failed Code : " + String.valueOf(response.code()));
                    }

                }

                @Override
                public void onFailure(Call<Shop> call, Throwable t) {

                    showToastMessage("Network Failed !");
                }
            });
        }
        else
        {
            Intent intent = new Intent(getActivity(), ShopHome.class);
            startActivity(intent);

        }

    }



    void showToastMessage(String message)
    {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }



    @OnClick(R.id.logout)
    void logoutClick()
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setTitle("Confirm Logout !")
                .setMessage("Do you want to log out !")
                .setPositiveButton("Yes",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        logout();

                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        showToastMessage("Cancelled !");
                    }
                })
                .show();
    }






    void logout()
    {
        // log out
        PrefLogin.saveUserProfile(null,getActivity());
        PrefLogin.saveCredentials(getActivity(),null,null);
        PrefShopHome.saveShop(null,getActivity());

        // stop location update service

        if(getActivity() instanceof NotifyAboutLogin)
        {
            ((NotifyAboutLogin) getActivity()).loginSuccess();
        }
    }







    void bindAllFields()
    {
        bindBalance();
        bindShopOpenStatus();
        bindNotice();
    }




    void bindNotice()
    {

        Shop shop = PrefShopHome.getShop(getActivity());

        if(shop==null)
        {
            return;
        }


        if(shop.getShopEnabled())
        {

            if(shop.getAccountBalance()<shop.getRt_min_balance())
            {
                notice.setText(getString(R.string.notice_low_balance));
                notice.setVisibility(View.VISIBLE);

            }
            else
            {
                notice.setVisibility(View.GONE);
            }

        }
        else
        {
            notice.setText(getString(R.string.notice_account_deactivated));
            notice.setVisibility(View.VISIBLE);
        }
    }



    void bindShopOpenStatus()
    {

        Shop shop = PrefShopHome.getShop(getActivity());


        if(shop==null)
        {
            return;
        }


        if(shop.getShopEnabled())
        {
            shopOpenSwitch.setEnabled(true);

            if(shop.isOpen())
            {

                shopOpenSwitch.setChecked(true);


                if(shop.getAccountBalance()<shop.getRt_min_balance())
                {
                    shopOpenStatus.setVisibility(View.GONE);
                }
                else
                {
                    shopOpenStatus.setVisibility(View.VISIBLE);
                    shopOpenStatus.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.open));
                }

                headerText.setText("Shop Open");
            }
            else
            {
//                shopOpenStatus.setVisibility(View.GONE);
                shopOpenStatus.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.shop_closed_small));

                shopOpenSwitch.setChecked(false);

                headerText.setText("Shop Closed");
            }
        }
        else
        {
            shopOpenStatus.setVisibility(View.GONE);
            shopOpenSwitch.setEnabled(false);

            headerText.setText("Shop Disabled");
        }
    }




    void bindBalance()
    {
        Shop shop = PrefShopHome.getShop(getActivity());


        if(shop==null)
        {
            return;
        }


        currentDues.setText("Balance : " + PrefGeneral.getCurrencySymbol(getActivity()) + String.format(" %.2f ",shop.getAccountBalance()));
        creditLimit.setText("Minimum balance required : " + PrefGeneral.getCurrencySymbol(getActivity()) + String.format(" %.2f ", shop.getRt_min_balance()));


        if(shop.getAccountBalance()<shop.getRt_min_balance())
        {
            lowBalanceMessage.setText(getString(R.string.notice_low_balance));
            lowBalanceMessage.setVisibility(View.VISIBLE);

        }
        else
        {
            lowBalanceMessage.setVisibility(View.GONE);
        }

    }






    void refreshShop()
    {
        Call<Shop> call = shopService.getShopForShopAdmin(
                PrefLogin.getAuthorizationHeaders(getActivity())
        );


        call.enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {

                if(response.code()==200)
                {
                    PrefShopHome.saveShop(response.body(),getActivity());

                    bindAllFields();

                }
                else if(response.code()==204)
                {

                    showToastMessage("Unable to Refresh !");

                }
                else if(response.code()==401||response.code()==403)
                {

                    Toast.makeText(getActivity(),"Not Permitted !",Toast.LENGTH_SHORT)
                            .show();
                }


                swipeContainer.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<Shop> call, Throwable t) {


                showToastMessage("Failed to refresh. Check your network connection !");

                swipeContainer.setRefreshing(false);


            }
        });
    }









    @OnClick(R.id.shop_open_switch)
    void shopSwitchClick()
    {
        if(shopOpenSwitch.isChecked())
        {
//            showToastMessage("Checked !");
            updateShopOpen();
        }
        else
        {
//            showToastMessage("Not checked !");
            updateShopClosed();
        }

    }






    void updateShopOpen()
    {


        progressSwitch.setVisibility(View.VISIBLE);

        Call<ResponseBody> call = shopService.updateShopOpen(
                PrefLogin.getAuthorizationHeaders(getActivity())
        );


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                if(!isVisible())
                {
                    return;
                }


                if(response.code()==200)
                {
//                    showToastMessage("Shop Open !");

                    Shop shop = PrefShopHome.getShop(getActivity());
                    shop.setOpen(true);
                    PrefShopHome.saveShop(shop,getActivity());

                    bindShopOpenStatus();
                }
                else
                {
                    showToastMessage("Failed Code : "  + String.valueOf(response.code()));
                    shopOpenSwitch.setChecked(!shopOpenSwitch.isChecked());
                }


                progressSwitch.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                if(!isVisible())
                {
                    return;
                }

                showToastMessage("Failed : Check you Network Connection !");
                shopOpenSwitch.setChecked(!shopOpenSwitch.isChecked());
                progressSwitch.setVisibility(View.GONE);
            }
        });

    }



    void updateShopClosed()
    {


        progressSwitch.setVisibility(View.VISIBLE);

        Call<ResponseBody> call = shopService.updateShopClosed(
                PrefLogin.getAuthorizationHeaders(getActivity())
        );


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                if(!isVisible())
                {
                    return;
                }


                if(response.code()==200)
                {
//                    showToastMessage("Shop Closed !");


                    Shop shop = PrefShopHome.getShop(getActivity());
                    shop.setOpen(false);
                    PrefShopHome.saveShop(shop,getActivity());

                    bindShopOpenStatus();
                }
                else
                {
                    showToastMessage("Failed Code : "  + String.valueOf(response.code()));
                    shopOpenSwitch.setChecked(!shopOpenSwitch.isChecked());
                }


                progressSwitch.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                if(!isVisible())
                {
                    return;
                }

                showToastMessage("Failed : Check you Network Connection !");
                shopOpenSwitch.setChecked(!shopOpenSwitch.isChecked());
                progressSwitch.setVisibility(View.GONE);
            }
        });

    }


}
