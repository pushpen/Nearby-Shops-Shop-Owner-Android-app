package org.nearbyshops.shopkeeperappnew.OrderDetail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import org.nearbyshops.shopkeeperappnew.Model.DeliveryAddress;
import org.nearbyshops.shopkeeperappnew.Model.Item;
import org.nearbyshops.shopkeeperappnew.Model.Order;
import org.nearbyshops.shopkeeperappnew.Model.OrderItem;
import org.nearbyshops.shopkeeperappnew.ModelStatusCodes.OrderStatusHomeDelivery;
import org.nearbyshops.shopkeeperappnew.ModelStatusCodes.OrderStatusPickFromShop;
import org.nearbyshops.shopkeeperappnew.Prefrences.PrefGeneral;
import org.nearbyshops.shopkeeperappnew.R;

import java.util.List;

/**
 * Created by sumeet on 13/6/16.
 */
class AdapterOrderDetailNew extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Object> dataset = null;
//    private NotifyConfirmOrder confirmOrderPFS;

    public static final int TAG_VIEW_HOLDER_ORDER = 1;
    public static final int TAG_VIEW_HOLDER_ORDER_ITEM = 2;

    private Context context;
    NotifyItemClick notifyItemClick;


    AdapterOrderDetailNew(List<Object> dataset, Context context, NotifyItemClick notifyItemClick) {
        this.dataset = dataset;
        this.context = context;
        this.notifyItemClick = notifyItemClick;
//        this.confirmOrderPFS = confirmOrderPFS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        if(viewType==TAG_VIEW_HOLDER_ORDER)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_order_order_detail_new,parent,false);

            return new ViewHolderOrder(view);
        }
        else
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_order_item_order_detail,parent,false);

            return new ViewHolderOrderItem(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ViewHolderOrder)
        {
            bindOrder((ViewHolderOrder) holder,position);
        }
        else if(holder instanceof ViewHolderOrderItem)
        {
            bindOrderItem((ViewHolderOrderItem)holder,position);
        }
    }




    @Override
    public int getItemViewType(int position) {

        super.getItemViewType(position);

        if(dataset.get(position) instanceof Order)
        {
            return TAG_VIEW_HOLDER_ORDER;
        }
        else if(dataset.get(position) instanceof OrderItem)
        {
            return TAG_VIEW_HOLDER_ORDER_ITEM;
        }

        return -1;
    }



    @Override
    public int getItemCount() {
        return dataset.size();
    }


    class ViewHolderOrder extends RecyclerView.ViewHolder{

        @BindView(R.id.order_id) TextView orderID;
        @BindView(R.id.dateTimePlaced) TextView dateTimePlaced;
        @BindView(R.id.deliveryAddressName) TextView deliveryAddressName;
        @BindView(R.id.deliveryAddress) TextView deliveryAddress;
        @BindView(R.id.deliveryAddressPhone) TextView deliveryAddressPhone;
        @BindView(R.id.numberOfItems) TextView numberOfItems;
        @BindView(R.id.orderTotal) TextView orderTotal;
        @BindView(R.id.currentStatus) TextView currentStatus;
        @BindView(R.id.is_pick_from_shop) TextView isPickFromShop;

        @BindView(R.id.delivery_type) TextView deliveryTypeDescription;

        @BindView(R.id.item_total_value) TextView itemTotal;
        @BindView(R.id.delivery_charge_value) TextView deliveryCharge;
        @BindView(R.id.app_service_charge_value) TextView appServiceCharge;
        @BindView(R.id.net_payable_value) TextView netPayable;




        // order Summary Views

//        @BindView(R.id.item_total) TextView itemTotal;
//        @BindView(R.id.delivery_charges) TextView deliveryCharges;
//        @BindView(R.id.order_total_summary) TextView orderTotalSummary;


//        @Bind(R.id.confirmOrderButton)
//        TextView confirmOrderButton;



        public ViewHolderOrder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }








    private void bindOrder(ViewHolderOrder holder, int position)
    {




            Order order = (Order)dataset.get(position);
            DeliveryAddress deliveryAddress = order.getDeliveryAddress();

            holder.orderID.setText("Order ID : " + order.getOrderID());
            holder.dateTimePlaced.setText("" + order.getDateTimePlaced().toLocaleString());


            holder.deliveryAddressName.setText(deliveryAddress.getName());

            holder.deliveryAddress.setText(deliveryAddress.getDeliveryAddress() + ",\n"
                    + deliveryAddress.getCity() + " - " + deliveryAddress.getPincode());

            holder.deliveryAddressPhone.setText("Phone : " + deliveryAddress.getPhoneNumber());


//            holder.numberOfItems.setText(orderStats.getItemCount() + " Items");
            holder.numberOfItems.setText(order.getItemCount() + " Items");
//            holder.orderTotal.setText("| Total : " + String.valueOf(PrefGeneral.getCurrencySymbol(context)) + " " + String.valueOf(orderStats.getItemTotal() + order.getDeliveryCharges()));
            holder.orderTotal.setText("| Total : " + String.valueOf(PrefGeneral.getCurrencySymbol(context)) + " " + String.format("%.2f",order.getNetPayable()));


            String status = "";



            if(order.isPickFromShop())
            {
                status = OrderStatusPickFromShop.getStatusString(order.getStatusPickFromShop());


                holder.isPickFromShop.setBackgroundColor(ContextCompat.getColor(context, R.color.orangeDark));
                holder.isPickFromShop.setText("Pick from Shop");

                holder.deliveryTypeDescription.setBackgroundColor(ContextCompat.getColor(context, R.color.orangeDark));
                holder.deliveryTypeDescription.setText(context.getString(R.string.delivery_type_description_pick_from_shop));

            }
            else
            {
                status = OrderStatusHomeDelivery.getStatusString(order.getStatusHomeDelivery());


                holder.isPickFromShop.setBackgroundColor(ContextCompat.getColor(context, R.color.phonographyBlue));
                holder.isPickFromShop.setText("Home Delivery");


                holder.deliveryTypeDescription.setBackgroundColor(ContextCompat.getColor(context, R.color.phonographyBlue));
                holder.deliveryTypeDescription.setText(context.getString(R.string.delivery_type_description_home_delivery));

            }




            holder.currentStatus.setText("Current Status : " + status);





            // bind billing details

            holder.itemTotal.setText(PrefGeneral.getCurrencySymbol(context) + " " + String.format("%.2f",order.getItemTotal()));
            holder.deliveryCharge.setText(PrefGeneral.getCurrencySymbol(context) + " " + String.format("%.2f",order.getDeliveryCharges()));
            holder.appServiceCharge.setText(PrefGeneral.getCurrencySymbol(context) + " " + String.format("%.2f", order.getAppServiceCharge()));
            holder.netPayable.setText(PrefGeneral.getCurrencySymbol(context) + " " + String.format("%.2f",order.getNetPayable()));


    }





    class ViewHolderOrderItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.itemImage)
        ImageView itemImage;

        @BindView(R.id.itemName)
        TextView itemName;

        @BindView(R.id.quantity)
        TextView quantity;

        @BindView(R.id.pincode)
        TextView itemPrice;

        @BindView(R.id.item_total)
        TextView itemTotal;

//        @Bind(R.id.item_rating)
//        TextView itemRating;

//        @Bind(R.id.rating_count)
//        TextView ratingCount;

        @BindView(R.id.item_id)
        TextView itemID;



        ViewHolderOrderItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(dataset.get(getLayoutPosition()) instanceof OrderItem)
            {
                OrderItem orderItem = (OrderItem) dataset.get(getLayoutPosition());
                Item item = orderItem.getItem();
                notifyItemClick.notifyItemClicked(item);
            }
        }
    }



    private void bindOrderItem(ViewHolderOrderItem holder, int position)
    {
        if(!(dataset.get(position) instanceof OrderItem))
        {
//            return;
        }


        OrderItem orderItem = (OrderItem) dataset.get(position);
        Item item = orderItem.getItem();

        holder.itemID.setText("Item ID : " + String.valueOf(orderItem.getItemID()));

        holder.itemName.setText(item.getItemName());
        holder.quantity.setText("Item Quantity : " + String.valueOf(orderItem.getItemQuantity()) + " "  + item.getQuantityUnit());
        holder.itemPrice.setText("Item Price : " + String.valueOf(PrefGeneral.getCurrencySymbol(context)) + " " + String.valueOf(orderItem.getItemPriceAtOrder())+ " per "  + item.getQuantityUnit());

        holder.itemTotal.setText("Item Total : " + String.valueOf(PrefGeneral.getCurrencySymbol(context)) + " " + String.valueOf(orderItem.getItemPriceAtOrder()*orderItem.getItemQuantity()));



        // bind Item Image

//        String imagePath = UtilityGeneral.getImageEndpointURL(MyApplication.getAppContext()) + item.getItemImageURL();


        String imagePath = PrefGeneral.getServiceURL(context)
                + "/api/v1/Item/Image/five_hundred_" + item.getItemImageURL() + ".jpg";


        Drawable placeholder = VectorDrawableCompat
                .create(context.getResources(),
                        R.drawable.nature_people, context.getTheme());

        Picasso.get()
                .load(imagePath)
                .placeholder(placeholder)
                .into(holder.itemImage);

    }





    interface NotifyItemClick{
        void notifyItemClicked(Item item);
    }

}
