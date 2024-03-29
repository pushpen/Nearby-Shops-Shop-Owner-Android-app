package org.nearbyshops.shopkeeperappnew.ViewHolderCommon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import org.nearbyshops.shopkeeperappnew.R;
import org.nearbyshops.shopkeeperappnew.ViewHolderCommon.Models.EmptyScreenData;


public class ViewHolderEmptyScreenNew extends RecyclerView.ViewHolder{




    @BindView(R.id.graphic_image) ImageView graphicImage;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.message) TextView message;
    @BindView(R.id.copyrights) TextView designedByFreepik;


    private Context context;


    private EmptyScreenData item;




    public static ViewHolderEmptyScreenNew create(ViewGroup parent, Context context)
    {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_empty_screen,parent,false);

        return new ViewHolderEmptyScreenNew(view,context);
    }





    public ViewHolderEmptyScreenNew(View itemView, Context context) {
        super(itemView);

        ButterKnife.bind(this,itemView);
        this.context = context;
    }





    public void setItem(EmptyScreenData data)
    {
        title.setText(data.getTitle());
        message.setText(data.getMessage());
        graphicImage.setImageResource(data.getDrawableResource());

        if(data.isShowDesignedByFreepik())
        {
            designedByFreepik.setVisibility(View.VISIBLE);
        }
        else
        {
            designedByFreepik.setVisibility(View.GONE);
        }
    }


}

