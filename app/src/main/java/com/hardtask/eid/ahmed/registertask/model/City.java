package com.hardtask.eid.ahmed.registertask.model;

import java.util.Locale;

public class City {
    private int Id, CountryId;
    private String TitleEN, TitleAR;
    private String language ;

    public City(int id, int countryId, String titleEN, String titleAR) {
        Id = id;
        CountryId = countryId;
        TitleEN = titleEN;
        TitleAR = titleAR;
        language = Locale.getDefault().getISO3Language();
    }

    public City(String titleEN, String titleAR) {
        TitleEN = titleEN;
        TitleAR = titleAR;
        language = Locale.getDefault().getISO3Language();
    }

    public int getId() {
        return Id;
    }

    public int getCountryId() {
        return CountryId;
    }

    public String getTitleEN() {
        return TitleEN;
    }

    public String getTitleAR() {
        return TitleAR;
    }

    @Override
    public String toString() {
        language = Locale.getDefault().getISO3Language();
        if (language.equals("eng")){
            return TitleEN;
        }else {
            return TitleAR;
        }
    }
}
