package org.nearbyshops.shopkeeperappnew.DeliveryPersonInventory;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import org.nearbyshops.shopkeeperappnew.DeliveryPersonInventory.Fragment.DeliveryInventoryFragment;
import org.nearbyshops.shopkeeperappnew.ModelRoles.User;
import org.nearbyshops.shopkeeperappnew.ModelStatusCodes.OrderStatusHomeDelivery;

/**
 * Created by sumeet on 15/6/16.
 */

public class PagerAdapter extends FragmentPagerAdapter {

//    DeliveryGuySelf deliveryVehicle;
//
//    int vehicleID;




    public PagerAdapter(FragmentManager fm, User deliveryGuySelf) {
        super(fm);

//        deliveryVehicle = deliveryGuySelf;
//
//        if(deliveryVehicle!=null)
//        {
//            vehicleID = deliveryVehicle.getDeliveryGuyID();
//        }
    }

    @Override
    public Fragment getItem(int position) {


        if(position==0)
        {
            return DeliveryInventoryFragment.newInstance(OrderStatusHomeDelivery.HANDOVER_REQUESTED,false);

        }else if(position == 1)
        {
            return DeliveryInventoryFragment.newInstance(OrderStatusHomeDelivery.OUT_FOR_DELIVERY,false);
        }
        else if(position==2)
        {
            return DeliveryInventoryFragment.newInstance(OrderStatusHomeDelivery.RETURN_REQUESTED,false);
        }
        else if(position==3)
        {
            return DeliveryInventoryFragment.newInstance(OrderStatusHomeDelivery.DELIVERED,false);
        }

        return null;
    }




    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return titlePendingHandover;
            case 1:
                return titleConfirmed;
            case 2:
                return titleReturnPending;
            case 3:
                return titlePaymentPending;

        }
        return null;
    }



    private String titlePendingHandover = "Handover Requested (0/0)";
    private String titleConfirmed = "Out For Delivery (0/0)";
    private String titleReturnPending = "Return Pending (0/0)";
    private String titlePaymentPending = "Payment Pending (0/0)";



    public void setTitle(String title, int tabPosition)
    {


        if(tabPosition == 0){

            titlePendingHandover = title;
        }
        else if (tabPosition == 1)
        {
            titleConfirmed = title;

        }else if(tabPosition == 2)
        {
            titleReturnPending = title;
        }
        else if(tabPosition==3)
        {
            titlePaymentPending=title;
        }

        notifyDataSetChanged();
    }



}