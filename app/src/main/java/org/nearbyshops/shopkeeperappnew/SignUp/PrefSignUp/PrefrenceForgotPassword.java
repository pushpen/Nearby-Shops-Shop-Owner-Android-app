package org.nearbyshops.shopkeeperappnew.SignUp.PrefSignUp;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import org.nearbyshops.shopkeeperappnew.ModelRoles.User;
import org.nearbyshops.shopkeeperappnew.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sumeet on 6/8/17.
 */

public class PrefrenceForgotPassword {


    public static final String TAG_SIGN_UP = "tag_forgot_password";


    public static void saveUser(User user, Context context)
    {

        //Creating a shared preference

        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = sharedPref.edit();

        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString(TAG_SIGN_UP, json);

        prefsEditor.apply();
    }


    public static User getUser(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPref.getString(TAG_SIGN_UP, null);

        return gson.fromJson(json, User.class);
    }

}
