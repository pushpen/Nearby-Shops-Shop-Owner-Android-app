package org.nearbyshops.shopkeeperappnew.DeliveryPersonInventory.Fragment;

import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import org.nearbyshops.shopkeeperappnew.DeliveryPersonInventory.ViewHolders.ViewHolderOrderButtonDouble;
import org.nearbyshops.shopkeeperappnew.Model.Order;
import org.nearbyshops.shopkeeperappnew.ModelStatusCodes.OrderStatusHomeDelivery;
import org.nearbyshops.shopkeeperappnew.OrderHistory.ViewHolders.ViewHolderOrder;
import org.nearbyshops.shopkeeperappnew.OrdersInventory.ViewHolders.ViewHolderOrderButtonSingle;
import org.nearbyshops.shopkeeperappnew.ViewHolderCommon.LoadingViewHolder;
import org.nearbyshops.shopkeeperappnew.ViewHolderCommon.Models.EmptyScreenData;
import org.nearbyshops.shopkeeperappnew.ViewHolderCommon.ViewHolderEmptyScreenNew;

import java.util.List;

/**
 * Created by sumeet on 13/6/16.
 */
class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Object> dataset = null;

    public static final int VIEW_TYPE_ORDER = 1;
    public static final int VIEW_TYPE_ORDER_WITH_BUTTON = 2;
    public static final int VIEW_TYPE_ORDER_BUTTON_DOUBLE = 3;


    public static final int VIEW_TYPE_SCROLL_PROGRESS_BAR = 5;
    public static final int VIEW_TYPE_EMPTY_SCREEN = 6;


//    private Map<Integer,Order> selectedOrders = new HashMap<>();
    private Fragment fragment;

    private boolean loadMore;



    Adapter(List<Object> dataset, Fragment fragment) {
        this.dataset = dataset;
        this.fragment = fragment;
//        selectedOrders.clear();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        if(viewType==VIEW_TYPE_ORDER)
        {
            return ViewHolderOrder.create(parent,parent.getContext(),fragment);
        }
        else if(viewType == VIEW_TYPE_ORDER_WITH_BUTTON)
        {
            return ViewHolderOrderButtonSingle.create(parent,parent.getContext(),fragment,true);
        }
        else if(viewType == VIEW_TYPE_ORDER_BUTTON_DOUBLE)
        {
            return ViewHolderOrderButtonDouble.create(parent,parent.getContext(),fragment);
        }
        else if (viewType == VIEW_TYPE_SCROLL_PROGRESS_BAR) {

            return LoadingViewHolder.create(parent,parent.getContext());
        }
        else if(viewType==VIEW_TYPE_EMPTY_SCREEN)
        {
            return ViewHolderEmptyScreenNew.create(parent,parent.getContext());
        }



        return null;
    }




    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);

        if(position == dataset.size())
        {
            return VIEW_TYPE_SCROLL_PROGRESS_BAR;
        }
        else if(dataset.get(position) instanceof Order)
        {

            if(!((Order) dataset.get(position)).isPickFromShop())
            {


                if(((Order) dataset.get(position)).getStatusHomeDelivery()==OrderStatusHomeDelivery.HANDOVER_REQUESTED)
                {

                    return VIEW_TYPE_ORDER_WITH_BUTTON;
                }
                else if(((Order) dataset.get(position)).getStatusHomeDelivery()==OrderStatusHomeDelivery.OUT_FOR_DELIVERY)
                {

                    return VIEW_TYPE_ORDER_BUTTON_DOUBLE;
                }
                else if(((Order) dataset.get(position)).getStatusHomeDelivery()==OrderStatusHomeDelivery.RETURN_REQUESTED)
                {

                    return VIEW_TYPE_ORDER;
                }
                else if(((Order) dataset.get(position)).getStatusHomeDelivery()==OrderStatusHomeDelivery.DELIVERED)
                {

                    return VIEW_TYPE_ORDER;
                }
                else
                {
                    return VIEW_TYPE_ORDER_WITH_BUTTON;
                }

            }

        }
        else if(dataset.get(position) instanceof EmptyScreenData)
        {
            return VIEW_TYPE_EMPTY_SCREEN;
        }


        return -1;
    }





    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {




        if(holder instanceof ViewHolderOrderButtonSingle)
        {
            ((ViewHolderOrderButtonSingle) holder).setItem((Order) dataset.get(position));
        }
        else if(holder instanceof ViewHolderOrder)
        {
            ((ViewHolderOrder) holder).setItem((Order) dataset.get(position));
        }
        else if(holder instanceof ViewHolderOrderButtonDouble)
        {
            ((ViewHolderOrder) holder).setItem((Order) dataset.get(position));
        }
        else if (holder instanceof LoadingViewHolder) {

            ((LoadingViewHolder) holder).setLoading(loadMore);
        }
        else if(holder instanceof ViewHolderEmptyScreenNew)
        {
            ((ViewHolderEmptyScreenNew) holder).setItem((EmptyScreenData) dataset.get(position));
        }

    }




    @Override
    public int getItemCount() {
        return (dataset.size()+1);
    }








    public void setLoadMore(boolean loadMore)
    {
        this.loadMore = loadMore;
    }




//    Map<Integer, Order> getSelectedOrders() {
//        return selectedOrders;
//    }


}
