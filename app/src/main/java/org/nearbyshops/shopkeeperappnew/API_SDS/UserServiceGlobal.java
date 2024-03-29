package org.nearbyshops.shopkeeperappnew.API_SDS;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.nearbyshops.shopkeeperappnew.Model.Image;
import org.nearbyshops.shopkeeperappnew.ModelRoles.User;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by sumeet on 3/4/17.
 */

public interface UserServiceGlobal {




    /* Password Reset : Begin */




    @PUT ("/api/v1/User/ResetPassword/GenerateResetCode")
    Call<ResponseBody> generateResetCode(@Body User user);



    @GET ("/api/v1/User/ResetPassword/CheckPasswordResetCode/{emailOrPhone}")
    Call<ResponseBody> checkPasswordResetCode(
            @Path("emailOrPhone") String emailOrPhone,
            @Query("ResetCode") String resetCode
    );


    @PUT ("/api/v1/User/ResetPassword")
    Call<ResponseBody> resetPassword(@Body User user);


    /* Password Reset : Ends */


    @POST ("/api/v1/User/SignUp/EndUserRegistration")
    Call<User> endUserRegistration(@Body User user);


    @GET ("/api/v1/User/SignUp/CheckUsernameExists/{username}")
    Call<ResponseBody> checkUsernameExists(@Path("username") String username);



    /* Sign-Up using e-mail */

    @PUT ("api/v1/User/SignUp/SendEmailVerificationCode/{email}")
    Call<ResponseBody> sendVerificationEmail(@Path("email") String email);



    @GET ("/api/v1/User/SignUp/CheckEmailVerificationCode/{email}")
    Call<ResponseBody> checkEmailVerificationCode(@Path("email") String email,
                                                  @Query("VerificationCode") String verificationCode);


    /* Sign-Up using phone */

    @GET ("/api/v1/User/SignUp/CheckPhoneVerificationCode/{phone}")
    Call<ResponseBody> checkPhoneVerificationCode(@Path("phone") String phone,
                                                  @Query("VerificationCode") String verificationCode);


    @PUT ("api/v1/User/SignUp/SendPhoneVerificationCode/{phone}")
    Call<ResponseBody> sendVerificationPhone(@Path("phone") String phone);



    /* Change Email */

    @PUT ("/api/v1/User/UpdateEmail")
    Call<ResponseBody> updateEmail(@Header("Authorization") String headers,
                                   @Body User user);


    @PUT ("/api/v1/User/UpdatePhone")
    Call<ResponseBody> updatePhone(@Header("Authorization") String headers,
                                   @Body User user);




    /* Change Password */

    @PUT ("/api/v1/User/ChangePassword/{OldPassword}")
    Call<ResponseBody> changePassword(@Header("Authorization") String headers,
                                      @Body User user, @Path("OldPassword") String oldPassword);





    /* Firebase for receiving notifications */


    @PUT ("/api/v1/User/UpdateFirebaseID/{FirebaseID}")
    Call<ResponseBody> updateFirebaseID(@Header("Authorization") String headers,
                                        @Path("FirebaseID") String firebaseID);


    @PUT("/api/v1/User/UpdateOneSignalID/{OneSignalID}")
    Call<ResponseBody> updateOneSignalID(@Header("Authorization") String headers,
                                         @Path("OneSignalID") String oneSignalID);



    @GET("/api/v1/User/GetToken")
    Call<User> getLogin(@Header("Authorization") String headers);






    @GET ("/api/v1/User/GetProfileWithLogin")
    Call<User> getProfile(@Header("Authorization") String headers);




    // Image Calls

    @POST("/api/v1/User/Image")
    Call<Image> uploadImage(@Header("Authorization") String headers,
                            @Body RequestBody image);


    @DELETE("/api/v1/User/Image/{name}")
    Call<ResponseBody> deleteImage(@Header("Authorization") String headers,
                                   @Path("name") String fileName);




//    /* Deprecated Methods : Start */
//
//
//
//
//    @PUT ("api/v1/User/ChangeEmail/{NewEmail}")
//    Call<ResponseBody> changeEmail(@Header("Authorization") String headers,
//                                   @Path("NewEmail") String newEmail);
//
//
//
//    @PUT ("api/v1/User/VerifyEmail/{VerificationCode}")
//    Call<ResponseBody> verifyEmail(@Header("Authorization") String headers,
//                                   @Path("VerificationCode") String verificationCode);
//
//
//
//    /* Deprecated Methods : End */



//
//
//    @POST("/api/v1/User/SignUp/DriverRegistration")
//    Call<User> insertUser(@Body User user);
//
//
//
//
//    @PUT ("/api/v1/User/UpdateProfileDriver")
//    Call<ResponseBody> updateProfileDriver(
//            @Header("Authorization") String headers,
//            @Body User user
//    );






    @PUT ("/api/v1/User/UpdateProfileEndUser")
    Call<ResponseBody> updateProfileEndUser(
            @Header("Authorization") String headers,
            @Body User user
    );






}
