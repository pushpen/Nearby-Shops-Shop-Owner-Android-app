package org.nearbyshops.shopkeeperappnew.Markets.ViewHolders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import org.nearbyshops.shopkeeperappnew.Markets.AdapterSavedMarkets;
import org.nearbyshops.shopkeeperappnew.Markets.Interfaces.listItemMarketNotifications;
import org.nearbyshops.shopkeeperappnew.Markets.Model.ServiceConfigurationGlobal;
import org.nearbyshops.shopkeeperappnew.R;

import java.util.List;

public class ViewHolderSavedMarketList extends RecyclerView.ViewHolder {


    @BindView(R.id.recycler_view)
    RecyclerView savedMarketList;



//    private List<ServiceConfigurationGlobal> configurationGlobal;
    private Context context;
    private listItemMarketNotifications subscriber;





    public static ViewHolderSavedMarketList create(ViewGroup parent, Context context, listItemMarketNotifications subscriber)
    {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_market_saved_list,parent,false);

        return new ViewHolderSavedMarketList(view,context,subscriber);
    }




    public ViewHolderSavedMarketList(@NonNull View itemView, Context context, listItemMarketNotifications subscriber) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.context = context;
        this.subscriber = subscriber;
    }








    void setItem(List<ServiceConfigurationGlobal> item)
    {

//        this.configurationGlobal = item;

        AdapterSavedMarkets adapter=new AdapterSavedMarkets(item,context, subscriber);
        savedMarketList.setAdapter(adapter);
        savedMarketList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        savedMarketList.setLayoutManager(layoutManager);

    }




}
