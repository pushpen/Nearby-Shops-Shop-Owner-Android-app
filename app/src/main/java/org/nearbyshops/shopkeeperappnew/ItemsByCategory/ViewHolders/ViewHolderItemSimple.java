package org.nearbyshops.shopkeeperappnew.ItemsByCategory.ViewHolders;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.*;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.squareup.picasso.Picasso;
import org.nearbyshops.shopkeeperappnew.Model.Item;
import org.nearbyshops.shopkeeperappnew.Model.ShopItem;
import org.nearbyshops.shopkeeperappnew.ModelStats.ItemStats;
import org.nearbyshops.shopkeeperappnew.Prefrences.PrefGeneral;
import org.nearbyshops.shopkeeperappnew.R;

import java.util.Map;


public class ViewHolderItemSimple extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener{


    @BindView(R.id.in_shop_color) ImageView inShopColor;

    @BindView(R.id.in_shop_text) TextView inShopText;
    @BindView(R.id.itemName) TextView categoryName;


    @BindView(R.id.items_list_item) CardView itemCategoryListItem;
    @BindView(R.id.itemImage) ImageView categoryImage;
    @BindView(R.id.price_range) TextView priceRange;
    @BindView(R.id.price_average) TextView priceAverage;
    @BindView(R.id.order_status) TextView shopCount;
    @BindView(R.id.item_rating) TextView itemRating;
    @BindView(R.id.rating_count) TextView ratingCount;




    private Context context;
    private Item item;
    private Fragment fragment;

    private RecyclerView.Adapter adapter;


    private Map<Integer, ShopItem> shopItemMap;
    private Map<Integer, Item> selectedItems;






    public static ViewHolderItemSimple create(ViewGroup parent, Context context, Fragment fragment,
                                              RecyclerView.Adapter adapter,
                                              Map<Integer, ShopItem> shopItemMap,
                                              Map<Integer, Item> selectedItems

    )
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_item_guide,parent,false);
        return new ViewHolderItemSimple(view,context,fragment,adapter,shopItemMap,selectedItems);
    }








    public ViewHolderItemSimple(View itemView, Context context, Fragment fragment, RecyclerView.Adapter adapter,
                                Map<Integer, ShopItem> shopItemMap,
                                Map<Integer, Item> selectedItems
    ) {
        super(itemView);
        ButterKnife.bind(this,itemView);

        this.context = context;
        this.fragment = fragment;
        this.adapter = adapter;
        this.shopItemMap = shopItemMap;
        this.selectedItems = selectedItems;
    }




    public void bindItem(Item item)
    {

        this.item = item;

        categoryName.setText(item.getItemName());

        ItemStats itemStats = item.getItemStats();

        priceRange.setText("Price Range :\n" + PrefGeneral.getCurrencySymbol(context) + " " + String.format("%.2f",itemStats.getMin_price()) + " - " + String.format("%.2f",itemStats.getMax_price()) + " per " + item.getQuantityUnit());
        priceAverage.setText("Price Average :\n"  + PrefGeneral.getCurrencySymbol(context) + " " + String.format("%.2f",itemStats.getAvg_price()) + " per " + item.getQuantityUnit());
        shopCount.setText("Available in " + itemStats.getShopCount() + " Shops");


        if(itemStats.getRatingCount()==0)
        {
            itemRating.setText(" New ");
            itemRating.setBackgroundColor(ContextCompat.getColor(context, R.color.phonographyBlue));
            ratingCount.setVisibility(View.GONE);
        }
        else
        {

            itemRating.setText(String.format(" %.2f ",itemStats.getRating_avg()));
            itemRating.setBackgroundColor(ContextCompat.getColor(context, R.color.gplus_color_2));
            ratingCount.setVisibility(View.VISIBLE);
            ratingCount.setText("( " + String.valueOf(itemStats.getRatingCount()) + " Ratings )");
        }





        if(selectedItems.containsKey(item.getItemID()))
        {
            itemCategoryListItem.setBackgroundColor(ContextCompat.getColor(context, R.color.gplus_color_2));
        }
        else
        {
            itemCategoryListItem.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }





        String imagePath = PrefGeneral.getServiceURL(context)
                + "/api/v1/Item/Image/three_hundred_" + item.getItemImageURL() + ".jpg";


        Drawable drawable = VectorDrawableCompat
                .create(context.getResources(),
                        R.drawable.ic_nature_people_white_48px, context.getTheme());

        Picasso.get()
                .load(imagePath)
                .placeholder(drawable)
                .into(categoryImage);



        if(shopItemMap.containsKey(item.getItemID()))
        {

            inShopColor.setBackgroundColor(context.getResources().getColor(R.color.darkGreen));
            inShopText.setText("In Shop");

        }
        else
        {
            inShopColor.setBackgroundColor(context.getResources().getColor(R.color.gplus_color_4));
            inShopText.setText("Not In Shop");
        }


    }





    @OnClick(R.id.items_list_item)
    public void listItemClick()
    {
            if(selectedItems.containsKey(
                    item.getItemID()
            ))
            {
                selectedItems.remove(item.getItemID());

            }else
            {

                selectedItems.put(item.getItemID(),item);
                ((ListItemClick)fragment).notifyItemSelected();
            }

            adapter.notifyItemChanged(getLayoutPosition());

    }// item click Ends



    //        @OnClick(R.id.more_options)
    void optionsOverflowClick(View v)
    {
        PopupMenu popup = new PopupMenu(context, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.item_overflow, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        switch (menuItem.getItemId()) {


            case R.id.action_edit:

                if(fragment instanceof ListItemClick)
                {
                    ((ListItemClick) fragment).editItem(item);
                }


                break;


            default:
                break;

        }

        return false;
    }








    public interface ListItemClick
    {
        void notifyItemSelected();
        void editItem(Item item);
    }



}// ViewHolderShopItem Class declaration ends

