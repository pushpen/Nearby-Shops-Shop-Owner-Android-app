package org.nearbyshops.shopkeeperappnew.QuickStockEditor.ViewHolders;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.squareup.picasso.Picasso;
import okhttp3.ResponseBody;
import org.nearbyshops.shopkeeperappnew.API.ShopItemService;
import org.nearbyshops.shopkeeperappnew.DaggerComponentBuilder;
import org.nearbyshops.shopkeeperappnew.Model.Item;
import org.nearbyshops.shopkeeperappnew.Model.ShopItem;
import org.nearbyshops.shopkeeperappnew.Prefrences.PrefGeneral;
import org.nearbyshops.shopkeeperappnew.Prefrences.PrefLogin;
import org.nearbyshops.shopkeeperappnew.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import java.util.List;


public class ViewHolderShopItem extends RecyclerView.ViewHolder{


    @Inject
    ShopItemService shopItemService;


    @BindView(R.id.itemName)
    TextView itemName;

//        @BindView(R.id.itemDescriptionShort)
//        TextView itemDescriptionShort;

    @BindView(R.id.itemImage)
    ImageView itemImage;

    @BindView(R.id.availableText)
    TextView availableText;

    @BindView(R.id.reduceQuantity)
    ImageView reduceQuantity;

    @BindView(R.id.itemQuantity)
    EditText itemQuantity;

    @BindView(R.id.increaseQuantity)
    ImageView increaseQuantity;

    @BindView(R.id.priceText)
    TextView priceText;

    @BindView(R.id.reducePrice)
    ImageView reducePrice;

    @BindView(R.id.itemPrice)
    EditText itemPrice;

    @BindView(R.id.increasePrice)
    ImageView increasePrice;

    @BindView(R.id.removeButton) TextView removeButton;
    @BindView(R.id.progress_bar_remove)
    ProgressBar progressBarRemove;

    @BindView(R.id.updateButton) TextView updateButton;
    @BindView(R.id.progress_bar) ProgressBar progressBarUpdate;





    private Context context;
    private Item item;
    private ShopItem shopItem;
    private Fragment fragment;


    private RecyclerView.Adapter adapter;
    private List<Object> dataset = null;






    public static ViewHolderShopItem create(ViewGroup parent, Context context, Fragment fragment,
                                            RecyclerView.Adapter adapter, List<Object> dataset

    )
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_shop_item_experimental,parent,false);
        return new ViewHolderShopItem(view,context,fragment,adapter,dataset);
    }







    public ViewHolderShopItem(View itemView, Context context, Fragment fragment, RecyclerView.Adapter adapter, List<Object> dataset) {
        super(itemView);


        ButterKnife.bind(this,itemView);

        itemQuantity.addTextChangedListener(new QuantityTextWatcher());
        itemPrice.addTextChangedListener(new PriceTextWatcher());


        DaggerComponentBuilder.getInstance()
                .getNetComponent()
                .Inject(this);


        this.context = context;
        this.fragment = fragment;
        this.adapter = adapter;
        this.dataset = dataset;
    }









    class QuantityTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


            if(!itemQuantity.getText().toString().equals(""))
            {

                String availableLocal = String.valueOf(Integer.parseInt(itemQuantity.getText().toString()));


                if(item!=null)
                {
                    availableText.setText("Available : " + availableLocal + " " + item.getQuantityUnit());

                }else
                {
                    availableText.setText("Available : " + availableLocal + " Items");
                }

            }else
            {
                availableText.setText("Available : " + "0" + " Items");
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }






    @OnClick(R.id.reduceQuantity)
    void reduceQuantityClick(View view)
    {

        if(!itemQuantity.getText().toString().equals(""))
        {

            if(Integer.parseInt(itemQuantity.getText().toString())<=0)
            {
                return;
            }


            String availableLocal = String.valueOf(Integer.parseInt(itemQuantity.getText().toString())-1);

            itemQuantity.setText(availableLocal);


            if(item!=null)
            {
                availableText.setText("Available : " + availableLocal + " " + item.getQuantityUnit());

            }else
            {
                availableText.setText("Available : " + availableLocal + " Items");
            }



        }else
        {
            itemQuantity.setText(String.valueOf(0));
        }

    }







    @OnClick(R.id.increaseQuantity)
    void increaseQuantityClick(View view)
    {

        if(!itemQuantity.getText().toString().equals(""))
        {

            String availableLocal = String.valueOf(Integer.parseInt(itemQuantity.getText().toString())+1);

            itemQuantity.setText(availableLocal);


            if(item!=null)
            {
                availableText.setText("Available : " + availableLocal + " " + item.getQuantityUnit());

            }else
            {
                availableText.setText("Available : " + availableLocal + " Items");
            }


        }else
        {
            itemQuantity.setText(String.valueOf(0));
        }

    }



    // methods for setting item Price



    class PriceTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(!itemPrice.getText().toString().equals(""))
            {

                String priceLocal = String.valueOf(Integer.parseInt(itemPrice.getText().toString()));

                if(item!=null)
                {
                    priceText.setText("Price : " + priceLocal + " per " + item.getQuantityUnit());

                }else
                {
                    priceText.setText("Price : " + priceLocal + " per Item ");
                }


            }else
            {

                if(item!=null)
                {
                    priceText.setText("Price : " + "0" + " per " + item.getQuantityUnit());

                }else
                {
                    priceText.setText("Price : " + "0" + " per Item ");
                }

            }



        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }



    @OnClick(R.id.reducePrice)
    void reducePriceClick(View view)
    {

        if(!itemPrice.getText().toString().equals(""))
        {

            if(Integer.parseInt(itemPrice.getText().toString())<=0)
            {
                return;
            }


            String priceLocal = String.valueOf(Integer.parseInt(itemPrice.getText().toString())-1);

            itemPrice.setText(priceLocal);



            if(item!=null)
            {
                priceText.setText("Price : " + priceLocal + " per " + item.getQuantityUnit());

            }else
            {
                priceText.setText("Price : " + priceLocal + " per Item ");
            }




        }else
        {
            itemPrice.setText(String.valueOf(0));
        }

    }



    @OnClick(R.id.increasePrice)
    void increasePriceClick(View view)
    {

        if(!itemPrice.getText().toString().equals(""))
        {

            String priceLocal = String.valueOf(Integer.parseInt(itemPrice.getText().toString())+1);

            itemPrice.setText(priceLocal);



            if(item!=null)
            {
                priceText.setText("Price : " + priceLocal + " per " + item.getQuantityUnit());

            }else
            {
                priceText.setText("Price : " + priceLocal + " per Item ");
            }


        }else
        {
            itemPrice.setText(String.valueOf(0));
        }

    }






    // Update and remove methods

    @OnClick(R.id.updateButton)
    void updateClick(View view)
    {

        if(!itemQuantity.getText().toString().equals("") && !itemPrice.getText().toString().equals(""))
        {

            int quantityLocal = Integer.parseInt(itemQuantity.getText().toString());
            int priceLocal = Integer.parseInt(itemPrice.getText().toString());

                /*
                if(quantityLocal <= 0 || priceLocal<=0)
                {
                    showToastMessage("Quantity or Price not set !");

                    return;
                }
                */

            shopItem.setAvailableItemQuantity(quantityLocal);
            shopItem.setItemPrice(priceLocal);



            Call<ResponseBody> call = shopItemService.putShopItem(
                    PrefLogin.getAuthorizationHeaders(context),
                    shopItem
            );



            updateButton.setVisibility(View.INVISIBLE);
            progressBarUpdate.setVisibility(View.VISIBLE);


            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if(response.code()==200)
                    {


                        if(fragment instanceof ShopItemUpdates)
                        {
                            ((ShopItemUpdates) fragment).notifyShopItemUpdated(shopItem);
                        }




                        if(shopItem.getItem()!=null)
                        {
                            showToastMessage(shopItem.getItem().getItemName() + " Updated !");

                        }else
                        {
                            showToastMessage("Update Successful !");
                        }
                    }
                    else if(response.code() == 403)
                    {
                        showToastMessage("Not permitted !");
                    }
                    else if(response.code() == 401)
                    {
                        showToastMessage("We are not able to identify you !");
                    }



                    updateButton.setVisibility(View.VISIBLE);
                    progressBarUpdate.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    showToastMessage("Network Request Failed. Try again !");

                    updateButton.setVisibility(View.VISIBLE);
                    progressBarUpdate.setVisibility(View.INVISIBLE);
                }
            });

        }
        else
        {
            showToastMessage("Quantity or Price not set !");
        }

    }



    @OnClick(R.id.removeButton)
    void removeClick(View view)
    {



        removeButton.setVisibility(View.INVISIBLE);
        progressBarRemove.setVisibility(View.VISIBLE);

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setTitle("Confirm Remove Item !")
                .setMessage("Do you want to remove this item from your shop !")
                .setPositiveButton("Yes",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        removeButton.setVisibility(View.VISIBLE);
                        progressBarRemove.setVisibility(View.INVISIBLE);


                        removeShopItem(shopItem);

                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        removeButton.setVisibility(View.VISIBLE);
                        progressBarRemove.setVisibility(View.INVISIBLE);

                        showToastMessage("Cancelled !");
                    }
                })
                .show();
    }








    private void removeShopItem(final ShopItem shopItem)
    {

        Call<ResponseBody> responseBodyCall = shopItemService.deleteShopItem(
                PrefLogin.getAuthorizationHeaders(context),
                shopItem.getShopID(),
                shopItem.getItemID()
        );


        removeButton.setVisibility(View.INVISIBLE);
        progressBarRemove.setVisibility(View.VISIBLE);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.code()==200)
                {
                    if(shopItem.getItem()!=null)
                    {
                        showToastMessage(shopItem.getItem().getItemName() + " Removed !");

                    }else
                    {
                        showToastMessage("Successful !");
                    }


//                    int removedPosition = dataset.indexOf(shopItem);


                    if(fragment instanceof ShopItemUpdates)
                    {
                        ((ShopItemUpdates) fragment).notifyShopItemRemoved(shopItem);
                        adapter.notifyItemRemoved(getAdapterPosition());
                        dataset.remove(shopItem);
                    }




//                    dataset.remove(shopItem);
//                    notifyItemRemoved(removedPosition);



//                        offset = offset - 1;
//                        item_count = item_count -1;
//                        notifyTitleChanged();
                }
                else if(response.code() == 304) {

                    showToastMessage("Not removed !");

                }
                else if(response.code() == 403)
                {
                    showToastMessage("Not permitted !");
                }
                else if(response.code() == 401)
                {
                    showToastMessage("We are not able to identify you !");
                }
                else
                {
                    showToastMessage("Server Error !");
                }



                removeButton.setVisibility(View.VISIBLE);
                progressBarRemove.setVisibility(View.INVISIBLE);

//                makeRefreshNetworkCall();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {


                showToastMessage("Network request failed !");


                removeButton.setVisibility(View.VISIBLE);
                progressBarRemove.setVisibility(View.INVISIBLE);

            }
        });
    }




    public void bindShopItem(ShopItem shopItem)
    {

            this.shopItem = shopItem;
            this.item  = shopItem.getItem();


            itemName.setText(item.getItemName());

//            String imagePath = UtilityGeneral.getImageEndpointURL(MyApplication.getAppContext()) + item.getItemImageURL();

            String imagePath = PrefGeneral.getServiceURL(context)
                    + "/api/v1/Item/Image/five_hundred_" + item.getItemImageURL() + ".jpg";


            Drawable placeholder = VectorDrawableCompat
                    .create(context.getResources(),
                            R.drawable.nature_people, context.getTheme());

            Picasso.get()
                    .load(imagePath)
                    .placeholder(placeholder)
                    .into(itemImage);


            //holder.availableText.setText("Available : " + shopItem.getAvailableItemQuantity() + " Items");
            //holder.priceText.setText("Price : " + shopItem.getItemPrice());

            itemPrice.setText(String.valueOf((int)shopItem.getItemPrice()));
            itemQuantity.setText(String.valueOf((int)shopItem.getAvailableItemQuantity()));

            //holder.itemPrice.setText(String.format( "%.0f", shopItem.getItemPrice()));
            //holder.itemQuantity.setText(String.format( "%.0f", shopItem.getAvailableItemQuantity()));

            if(item!=null)
            {
                availableText.setText("Available : " + shopItem.getAvailableItemQuantity() + " " + item.getQuantityUnit());
                priceText.setText("Price : " + shopItem.getItemPrice() + " per " + item.getQuantityUnit());

            }else
            {
                availableText.setText("Available : " + shopItem.getAvailableItemQuantity() + " Items");
                priceText.setText("Price : " + shopItem.getItemPrice() + " per Item");
            }

    }




    private void showToastMessage(String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }




    public interface ShopItemUpdates{

        void notifyShopItemUpdated(ShopItem shopItem);
        void notifyShopItemRemoved(ShopItem shopItem);

    }


}


