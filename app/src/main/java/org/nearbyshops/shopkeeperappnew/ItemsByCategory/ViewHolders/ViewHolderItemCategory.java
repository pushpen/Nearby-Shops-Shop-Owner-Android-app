package org.nearbyshops.shopkeeperappnew.ItemsByCategory.ViewHolders;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.squareup.picasso.Picasso;
import org.nearbyshops.shopkeeperappnew.Model.Item;
import org.nearbyshops.shopkeeperappnew.Model.ItemCategory;
import org.nearbyshops.shopkeeperappnew.Model.ShopItem;
import org.nearbyshops.shopkeeperappnew.Prefrences.PrefGeneral;
import org.nearbyshops.shopkeeperappnew.R;

import java.util.Map;

public class ViewHolderItemCategory extends RecyclerView.ViewHolder{


    @BindView(R.id.name) TextView categoryName;
    @BindView(R.id.itemCategoryListItem)
    ConstraintLayout itemCategoryListItem;
    @BindView(R.id.categoryImage) ImageView categoryImage;
    @BindView(R.id.cardview)
    CardView cardView;



    private Context context;
    private ItemCategory itemCategory;
    private Fragment fragment;

    private RecyclerView.Adapter adapter;

//
//    private Map<Integer, ShopItem> shopItemMap;
//    private Map<Integer, Item> selectedItems;





    public static ViewHolderItemCategory create(ViewGroup parent, Context context, Fragment fragment,
                                                RecyclerView.Adapter adapter,
                                                Map<Integer, ShopItem> shopItemMap,
                                                Map<Integer, Item> selectedItems)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_item_category_simple,parent,false);
        return new ViewHolderItemCategory(view,context,fragment,adapter,shopItemMap,selectedItems);
    }








    public ViewHolderItemCategory(View itemView, Context context, Fragment fragment, RecyclerView.Adapter adapter,
                                  Map<Integer, ShopItem> shopItemMap,
                                  Map<Integer, Item> selectedItems
    )
    {
        super(itemView);
        ButterKnife.bind(this,itemView);


        this.context = context;
        this.fragment = fragment;
        this.adapter = adapter;
//        this.shopItemMap = shopItemMap;
//        this.selectedItems = selectedItems;
    }





    @OnClick(R.id.itemCategoryListItem)
    public void itemCategoryListItemClick()
    {

        if(fragment instanceof ListItemClick)
        {
            ((ListItemClick) fragment).notifyRequestSubCategory(itemCategory);
        }


//        selectedItems.clear();
    }







    public void bindItemCategory(ItemCategory itemCategory)
    {
        this.itemCategory = itemCategory;

        categoryName.setText(String.valueOf(itemCategory.getCategoryName()));


        String imagePath = PrefGeneral.getServiceURL(context) + "/api/v1/ItemCategory/Image/five_hundred_"
                + itemCategory.getImagePath() + ".jpg";

        Drawable placeholder = VectorDrawableCompat
                .create(context.getResources(),
                        R.drawable.ic_nature_people_white_48px, context.getTheme());


        Picasso.get()
                .load(imagePath)
                .placeholder(placeholder)
                .into(categoryImage);
    }







    public interface ListItemClick
    {
        void notifyRequestSubCategory(ItemCategory itemCategory);
    }



}// ViewHolderShopItem Class declaration ends





