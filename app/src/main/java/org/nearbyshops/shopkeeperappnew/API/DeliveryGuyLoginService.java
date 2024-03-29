package org.nearbyshops.shopkeeperappnew.API;


import okhttp3.ResponseBody;
import org.nearbyshops.shopkeeperappnew.ModelRoles.DeliveryGuyData;
import org.nearbyshops.shopkeeperappnew.ModelRoles.User;
import org.nearbyshops.shopkeeperappnew.ModelRoles.UserEndpoint;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by sumeet on 30/8/17.
 */


public interface DeliveryGuyLoginService {



    @PUT ("/api/v1/User/DeliveryGuy/UpdateProfileBySelf")
    Call<ResponseBody> updateProfileBySelf(
            @Header("Authorization") String headers,
            @Body User user
    );



    @PUT ("/api/v1/User/DeliveryGuy/{UserID}")
    Call<ResponseBody> updateDeliveryGuyByAdmin(
            @Header("Authorization") String headers,
            @Body User user,
            @Path("UserID") int userID
    );




    @PUT ("/api/v1/User/DeliveryGuy/UpdateStaffLocation")
    Call<ResponseBody> updateLocation(
            @Header("Authorization") String headers,
            @Body DeliveryGuyData deliveryGuyData
    );




    @GET ("/api/v1/User/DeliveryGuy/GetDeliveryGuyForShopAdmin")
    Call<UserEndpoint> getDeliveryGuyForShopAdmin(
            @Header("Authorization") String headers,
            @Query("latCurrent") Double latPickUp, @Query("lonCurrent") Double lonPickUp,
            @Query("Gender") Boolean gender,
            @Query("SortBy") String sortBy,
            @Query("Limit") Integer limit, @Query("Offset") Integer offset,
            @Query("GetRowCount") boolean getRowCount,
            @Query("MetadataOnly") boolean getOnlyMetaData
    );


}
