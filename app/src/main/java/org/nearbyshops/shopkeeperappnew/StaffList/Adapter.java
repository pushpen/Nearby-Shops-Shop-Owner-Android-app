package org.nearbyshops.shopkeeperappnew.StaffList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import org.nearbyshops.shopkeeperappnew.ModelRoles.User;
import org.nearbyshops.shopkeeperappnew.ViewHolderCommon.LoadingViewHolder;
import org.nearbyshops.shopkeeperappnew.ViewHolderCommon.Models.EmptyScreenData;
import org.nearbyshops.shopkeeperappnew.ViewHolderCommon.Models.HeaderTitle;
import org.nearbyshops.shopkeeperappnew.ViewHolderCommon.ViewHolderEmptyScreenNew;
import org.nearbyshops.shopkeeperappnew.ViewHolderCommon.ViewHolderHeaderSimple;

import java.util.List;

/**
 * Created by sumeet on 19/12/15.
 */





public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Object> dataset;
    private Context context;
    private Fragment fragment;

    public static final int VIEW_TYPE_STAFF_PROFILE = 1;

    public static final int VIEW_TYPE_HEADER = 2;
    public static final int VIEW_TYPE_SCROLL_PROGRESS_BAR = 3;
    public static final int VIEW_TYPE_EMPTY_SCREEN = 4;



    private boolean loadMore;



    public Adapter(List<Object> dataset, Context context, Fragment fragment) {

        this.dataset = dataset;
        this.context = context;
        this.fragment = fragment;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;


        if (viewType == VIEW_TYPE_STAFF_PROFILE) {

            return ViewHolderShopStaff.create(parent,context,fragment,this);
        }
        else if (viewType == VIEW_TYPE_HEADER) {

            return ViewHolderHeaderSimple.create(parent,context);
        }
        else if(viewType == VIEW_TYPE_SCROLL_PROGRESS_BAR)
        {
            return LoadingViewHolder.create(parent,context);
        }
        else if(viewType==VIEW_TYPE_EMPTY_SCREEN)
        {
            return ViewHolderEmptyScreenNew.create(parent,context);
        }


        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolderShopStaff) {

            ((ViewHolderShopStaff) holder).setItem((User) dataset.get(position));
        }
        else if (holder instanceof LoadingViewHolder) {

            ((LoadingViewHolder) holder).setLoading(loadMore);
        }
        else if(holder instanceof ViewHolderEmptyScreenNew)
        {
            ((ViewHolderEmptyScreenNew) holder).setItem((EmptyScreenData) dataset.get(position));
        }
        else if (holder instanceof ViewHolderHeaderSimple) {

            if (dataset.get(position) instanceof HeaderTitle) {

                ((ViewHolderHeaderSimple) holder).setItem((HeaderTitle) dataset.get(position));
            }

        }

    }


    @Override
    public int getItemViewType(int position) {

        super.getItemViewType(position);

        if (position == dataset.size()) {

            return VIEW_TYPE_SCROLL_PROGRESS_BAR;
        }
        else if (dataset.get(position) instanceof HeaderTitle) {

            return VIEW_TYPE_HEADER;
        }
        else if (dataset.get(position) instanceof User) {

            return VIEW_TYPE_STAFF_PROFILE;
        }
        else if(dataset.get(position) instanceof EmptyScreenData)
        {
            return VIEW_TYPE_EMPTY_SCREEN;
        }

        return -1;
    }


    @Override
    public int getItemCount() {

        return (dataset.size() + 1);
    }



    public void setLoadMore(boolean loadMore)
    {
        this.loadMore = loadMore;
    }

}