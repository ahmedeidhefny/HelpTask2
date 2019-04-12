package com.hardtask.eid.ahmed.registertask;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hardtask.eid.ahmed.registertask.model.API;
import com.hardtask.eid.ahmed.registertask.model.City;
import com.hardtask.eid.ahmed.registertask.model.Code;
import com.hardtask.eid.ahmed.registertask.model.Country;
import com.hardtask.eid.ahmed.registertask.model.UserRegister;
import com.hardtask.eid.ahmed.registertask.view.MainActivity;
import com.hardtask.eid.ahmed.registertask.viewModel.CityViewModel;
import com.hardtask.eid.ahmed.registertask.viewModel.CountryViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RegisterFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

    private static final String TAG = "RegisterFragment";
    @BindView(R.id.country_spinner)
    Spinner countrySpinner;
    @BindView(R.id.city_spinner)
    Spinner citySpinner;
    @BindView(R.id.code_spinner)
    Spinner codeSpinner;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.fullName_et)
    EditText fullNameEt;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.terms_conditions)
    TextView termsConditions;
    @BindView(R.id.change_language)
    Button changeLanguage;

    private static final String termsAndConditionsUrl = "https://termsfeed.com/blog/sample-terms-and-conditions-template/";


    ArrayAdapter<Country> countryAdapter;
    ArrayAdapter<City> cityAdapter;
    ArrayAdapter<Code> codeAdapter;

    List<Country> countries;
    List<City> cities;
    List<Code> codes;

    String countrySelected;
    String citySelected;

    City cityDefault;

    Boolean isTouchSpinner = false;
    Boolean isSelectCountrySpinner = false;
    Boolean isSelectCitySpinner = false;
    Boolean isSelectCodeSpinner = false;

    CountryViewModel viewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, myView);
        initializeUI();
        setSpinnerBackground();
        initializeSpinnerAdapter();
        getCountries();

        return myView;
    }


    private void initializeUI() {
        countries = new ArrayList<>();
        cities = new ArrayList<>();
        codes = new ArrayList<>();
        viewModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        countrySpinner.setOnItemSelectedListener(this);
        countrySpinner.setOnTouchListener(this);
        citySpinner.setOnItemSelectedListener(this);
        citySpinner.setOnTouchListener(this);
        codeSpinner.setOnItemSelectedListener(this);
        codeSpinner.setOnTouchListener(this);
    }


    private void setSpinnerBackground() {
        if (Build.VERSION.SDK_INT >= 21) {
            citySpinner.setBackground(getResources().getDrawable(R.drawable.spinner_background_21));
            countrySpinner.setBackground(getResources().getDrawable(R.drawable.spinner_background_21));
            codeSpinner.setBackground(getResources().getDrawable(R.drawable.spinner_background_21));
        } else {
            citySpinner.setBackground(getResources().getDrawable(R.drawable.spinner_background));
            countrySpinner.setBackground(getResources().getDrawable(R.drawable.spinner_background));
            codeSpinner.setBackground(getResources().getDrawable(R.drawable.spinner_background));
        }
    }

    private void initializeSpinnerAdapter() {
        Country country = new Country(getString(R.string.country), getString(R.string.country));
        countries.add(country);
        countryAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, countries);
        countryAdapter.setDropDownViewResource(R.layout.item_spinner);
        countrySpinner.setAdapter(countryAdapter);

        cityDefault = new City(getString(R.string.city), getString(R.string.city));
        cities.add(cityDefault);
        cityAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, cities);
        cityAdapter.setDropDownViewResource(R.layout.item_spinner);
        citySpinner.setAdapter(cityAdapter);

        Code code = new Code(getString(R.string.code), getString(R.string.code));
        codes.add(code);
        codeAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, codes);
        codeAdapter.setDropDownViewResource(R.layout.item_spinner);
        codeSpinner.setAdapter(codeAdapter);
    }


    private void getCountries() {
        //fetch country from API and ViewModel
        viewModel.getCountryList().observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(@Nullable List<Country> countryList) {
                countries.addAll(countryList);
                countryAdapter.notifyDataSetChanged();
                getCodesFromCountry(countryList);
            }
        });
    }

    private void getCodesFromCountry(List<Country> countryList) {
        for (Country country : countryList) {
            Code code = new Code(country.getCode(), country.getCodeEN(), country.getCodeAR());
            codes.add(code);
        }
        codeAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //onItemSelected on spinner
        if (isTouchSpinner) {
            if (adapterView.getItemAtPosition(i) instanceof Country) {
                countrySelected = adapterView.getItemAtPosition(i).toString();
                Country country = (Country) adapterView.getItemAtPosition(i);
                String countryId = String.valueOf(country.getId());
                //clear cityList
                if (cities.size() > 0) {
                    cities.clear();
                    cities.add(cityDefault);
                    cityAdapter.notifyDataSetChanged();
                }
                //fetch cities from API based to countryId
                getCities(countryId);
                if (adapterView.getItemIdAtPosition(i) > 0) {
                    isSelectCountrySpinner = true;
                } else {
                    isSelectCountrySpinner = false;
                }
            } else if (adapterView.getItemAtPosition(i) instanceof City) {
                if (adapterView.getItemIdAtPosition(i) > 0) {
                    isSelectCitySpinner = true;
                    citySelected = adapterView.getItemAtPosition(i).toString();
                } else {
                    isSelectCitySpinner = false;
                }
            } else if (adapterView.getItemAtPosition(i) instanceof Code) {
                if (adapterView.getItemIdAtPosition(i) > 0) {
                    isSelectCodeSpinner = true;
                    Code code = (Code) adapterView.getItemAtPosition(i);
                    codeEt.setText(String.valueOf(code.getCode()));
                } else {
                    isSelectCodeSpinner = false;
                }
            }
        }

    }

    private void getCities(String countryId) {
        //fetch cities from API based to countryId
        CityViewModel cityViewModel = new CityViewModel();
        Retrofit retrofit = cityViewModel.getCities();
        API api = retrofit.create(API.class);
        Call<List<City>> call = api.getCites(countryId);
        call.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(@NonNull Call<List<City>> call, @NonNull Response<List<City>> response) {
                List<City> cityList = response.body();
                //check response is empty or not empty
                if (cityList.size() > 0) {
                    cities.addAll(cityList);
                    cityAdapter.notifyDataSetChanged();
                    citySpinner.setAdapter(cityAdapter);
                } else {
                    Toast.makeText(getActivity(), "Not Fount cities", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<City>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure" + "Error");
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //onNothingSelected no action
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        isTouchSpinner = true;
        return false;
    }


    @OnClick({R.id.register, R.id.terms_conditions, R.id.change_language})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register:
                registerData();
                break;
            case R.id.terms_conditions:
                goToChromeCustomTab();
                break;
            case R.id.change_language:
                changeLocalLanguage();
                break;
        }
    }


    private void registerData() {
        String fullName = fullNameEt.getText().toString();
        String password = passwordEt.getText().toString();
        String code = codeEt.getText().toString();
        String email = emailEt.getText().toString();
        //check Validation is true
        if (Validation(fullName, password, email, code)) {
            UserRegister userRegister = new UserRegister(fullName, password, email, code, countrySelected, citySelected);
            Toast.makeText(getActivity(), "successful Register", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean Validation(String fullName, String password, String email, String code) {
        if (TextUtils.isEmpty(fullName)) {
            fullNameEt.setError(getString(R.string.massage_error_fullName));
            return false;
        } else if (TextUtils.isEmpty(password)) {
            passwordEt.setError(getString(R.string.massage_error_password));
            return false;
        } else if (!isSelectCodeSpinner) {
            Toast.makeText(getActivity(), getString(R.string.massage_error_code_spinner), Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(email)) {
            emailEt.setError(getString(R.string.massage_error_email));
            return false;
        } else if (TextUtils.isEmpty(code)) {
            codeEt.setError(getString(R.string.massage_error_code));
            return false;
        } else if (!isSelectCountrySpinner) {
            Toast.makeText(getActivity(), getString(R.string.massage_error_country_spinner), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isSelectCitySpinner) {
            Toast.makeText(getActivity(), getString(R.string.massage_error_city_spinner), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    //using chrome custom Tab for launching URLs in our Android applications
    /* The other two ways were using a WebView and opening it in a browser.
    /* Chrome Custom Tabs allows us to customize the tabs easily
    and load them much faster about webView.
     */
    private void goToChromeCustomTab() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent mCustomTabsIntent = builder.build();
        //set Toolbar Color
        builder.setToolbarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
       //launchUrl in our Android applications
        mCustomTabsIntent.launchUrl(getActivity(), Uri.parse(termsAndConditionsUrl));
    }

    private void changeLocalLanguage() {
        String language = Locale.getDefault().getISO3Language();
        if (language.equals("eng")) {
            language = "ar";
        } else {
            language = "en";
        }

        //set localization
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        //reload the the activity
        getActivity().recreate();


    }

}
