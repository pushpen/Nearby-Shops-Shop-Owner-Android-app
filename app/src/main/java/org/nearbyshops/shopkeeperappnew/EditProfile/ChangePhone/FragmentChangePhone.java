package org.nearbyshops.shopkeeperappnew.EditProfile.ChangePhone;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import com.google.android.material.textfield.TextInputEditText;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;
import okhttp3.ResponseBody;
import org.nearbyshops.shopkeeperappnew.API.UserService;
import org.nearbyshops.shopkeeperappnew.DaggerComponentBuilder;
import org.nearbyshops.shopkeeperappnew.ModelRoles.User;
import org.nearbyshops.shopkeeperappnew.Prefrences.PrefServiceConfig;
import org.nearbyshops.shopkeeperappnew.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;

/**
 * Created by sumeet on 27/6/17.
 */

public class FragmentChangePhone extends Fragment {


    @BindView(R.id.progress_bar_button) ProgressBar progressBarButton;
    @BindView(R.id.next) TextView nextButton;

//    @BindView(R.id.text_input_email) TextInputLayout emailLayout;

    @BindView(R.id.ccp) CountryCodePicker ccp;
    @BindView(R.id.phone)
    TextInputEditText phone;
    @BindView(R.id.password)
    TextInputEditText password;

    @BindView(R.id.check_icon) ImageView checkIcon;
    @BindView(R.id.cross_icon) ImageView crossIcon;
    @BindView(R.id.message) TextView textAvailable;

    @BindView(R.id.progress_bar) ProgressBar progressBar;


    boolean emailIsAvailable = false;

    User user;

    @Inject
    UserService userService;


    boolean isDestroyed = false;



    public FragmentChangePhone() {

        DaggerComponentBuilder.getInstance()
                .getNetComponent().Inject(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.fragment_change_phone, container, false);
        ButterKnife.bind(this, rootView);


        user = PrefChangePhone.getUser(getActivity());

        if(user==null)
        {
            user = new User();
        }


        ccp.setCcpClickable(true);


        if(PrefServiceConfig.getServiceConfigLocal(getActivity())!=null)
        {
            ccp.setCountryForNameCode(PrefServiceConfig.getServiceConfigLocal(getActivity()).getISOCountryCode());
        }






        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                textInputChanged();
            }
        });





        phone.requestFocus();
        bindViews();
        textInputChanged();

        return rootView;
    }






    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isDestroyed = true;
    }


    @Override
    public void onResume() {
        super.onResume();

        isDestroyed = false;
    }




    void bindViews()
    {
        if(user.getPhone()!=null)
        {
            phone.setText(user.getPhoneWithCountryCode());
        }
    }





    void saveDataFromViews()
    {
        user.setPhone(phone.getText().toString());
        user.setPassword(password.getText().toString());

        user.setRt_phone_country_code(ccp.getSelectedCountryCode());
    }





    @OnTextChanged({R.id.phone})
    void textInputChanged()
    {
        // reset flags
        emailIsAvailable = false;


        textAvailable.setVisibility(View.INVISIBLE);
        checkIcon.setVisibility(View.INVISIBLE);
        crossIcon.setVisibility(View.INVISIBLE);

        if(!isDataValid(false))
        {
            return;
        }

//        saveDataFromViews();


//        checkUsernameExist();


        progressBar.setVisibility(View.VISIBLE);
        countDownTimer.cancel();  // restart the timer
        countDownTimer.start();
    }






    boolean isDataValid(boolean showError)
    {
        boolean isValid = true;

       // validate phone

        // validate phone

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();


        Phonenumber.PhoneNumber phoneNumber = null;

        try {
            phoneNumber = phoneUtil.parse(phone.getText().toString(),ccp.getSelectedCountryNameCode());
        } catch (NumberParseException e) {
            e.printStackTrace();
        }





        if(phone.getText().toString().equals(""))
        {
            if(showError)
            {
                phone.setError("Phone cannot be empty !");
            }

            isValid= false;

        }
        else if(phoneNumber!=null && !phoneUtil.isValidNumberForRegion(phoneNumber,ccp.getSelectedCountryNameCode()))
        {
//            phone.getText().toString().length()!=10

            if(showError)
            {
                phone.requestFocus();
                phone.setError("Invalid Phone Number !");
            }

            isValid=false;
        }


        return isValid;
    }





    boolean validatePassword(boolean showError)
    {
        boolean isValid = true;

        if(password.getText().toString().length()==0)
        {
            if(showError)
            {
                password.requestFocus();
                password.setError("Password is empty. Please provide your password !");
            }

            isValid = false;

        }

        return isValid;
    }









    CountDownTimer countDownTimer = new CountDownTimer(2000, 1000) {

        public void onTick(long millisUntilFinished) {

        }

        public void onFinish() {

            if(isDestroyed)
            {
                return;
            }

           checkUsernameExist();
        }
    };






    void checkUsernameExist()
    {
        String inputName = "";

//        inputName = phone.getText().toString();

        inputName = ccp.getSelectedCountryCode() + phone.getText().toString();


        Call<ResponseBody> call = userService.checkUsernameExists(inputName);


        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(isDestroyed)
                {
                    return;
                }

                progressBar.setVisibility(View.INVISIBLE);

                if(response.code()==200)
                {
                    // username is not unique and already exist


                    phone.setError("An account already with that Phone. Please use another phone !");

                    checkIcon.setVisibility(View.INVISIBLE);
                    crossIcon.setVisibility(View.VISIBLE);

                    textAvailable.setVisibility(View.VISIBLE);
                    textAvailable.setText("Not Available for Registration !");


                    emailIsAvailable = false;

                }
                else if(response.code()==204)
                {
                    // username is unique and available for registration
                    checkIcon.setVisibility(View.VISIBLE);
                    crossIcon.setVisibility(View.INVISIBLE);

                    textAvailable.setVisibility(View.VISIBLE);
                    textAvailable.setText("Available for Registration !");


                    emailIsAvailable = true;
                }
                else
                {
                    // username is unique and available for registration
                    checkIcon.setVisibility(View.INVISIBLE);
                    crossIcon.setVisibility(View.VISIBLE);

                    textAvailable.setVisibility(View.VISIBLE);
                    textAvailable.setText("Failed Code : " + String.valueOf(response.code()));

                    emailIsAvailable = false;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                if(isDestroyed)
                {
                    return;
                }

                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }








    @OnClick(R.id.next)
    void nextClick()
    {
        if(!isDataValid(true))
        {
            return;
        }

        if(!validatePassword(true))
        {
            return;
        }




        saveDataFromViews();

        PrefChangePhone.saveUser(user,getActivity());



        // registering using phone

        if(!emailIsAvailable)
        {
            // e-mail is not available for registration so return
            return;
        }





        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());


        dialog.setTitle("You will receive a OTP code via SMS !")
                .setMessage("We will send you a OTP code on your phone. You will be asked to " +
                        "enter the one time password (OTP) code !")
                .setPositiveButton("Ok",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sendEmailVerificationCode();
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        showToastMessage("Cancelled !");
                    }
                })
                .show();




    }




    void sendEmailVerificationCode()
    {


        progressBarButton.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.INVISIBLE);

        Call<ResponseBody> call = userService.sendVerificationPhone(
                user.getRt_phone_country_code() + user.getPhone()
        );


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(isDestroyed)
                {
                    return;
                }

                progressBarButton.setVisibility(View.INVISIBLE);
                nextButton.setVisibility(View.VISIBLE);

                if(response.code()==200)
                {


                    if(getActivity() instanceof ShowFragmentChangePhone)
                    {
                        ((ShowFragmentChangePhone) getActivity()).showVerifyPhone();
                    }

                }
                else if(response.code()==304)
                {
                    showToastMessage("Failed to send verification code. Please try again !");
                }
                else
                {
                    showToastMessage("Failed to send verification code. Please try again !");
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                if(isDestroyed)
                {
                    return;
                }

                progressBarButton.setVisibility(View.INVISIBLE);
                nextButton.setVisibility(View.VISIBLE);

                showToastMessage("Failed to send verification code. Please try again !");

            }
        });


    }






    void showToastMessage(String message)
    {
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }

}
