package com.hardtask.eid.ahmed.registertask.model;


import java.util.Locale;

public class Country {
    private int Id,CurrencyId,Code ;
    private String TitleEN,TitleAR,CurrencyEN,CurrencyAR,CodeEN,CodeAR;
    private String language ;

    public Country(){}

    public Country(int id, int currencyId, int code, String titleEN, String titleAR, String currencyEN, String currencyAR, String codeEN, String codeAR) {
        Id = id;
        CurrencyId = currencyId;
        Code = code;
        TitleEN = titleEN;
        TitleAR = titleAR;
        CurrencyEN = currencyEN;
        CurrencyAR = currencyAR;
        CodeEN = codeEN;
        CodeAR = codeAR;
        language = Locale.getDefault().getISO3Language();
    }

    public Country(String titleEN, String titleAR) {
        TitleEN = titleEN;
        TitleAR = titleAR;
        language = Locale.getDefault().getISO3Language();
    }


    public int getId() {
        return Id;
    }

    public int getCurrencyId() {
        return CurrencyId;
    }

    public int getCode() {
        return Code;
    }

    public String getTitleEN() {
        return TitleEN;
    }

    public String getTitleAR() {
        return TitleAR;
    }

    public String getCurrencyEN() {
        return CurrencyEN;
    }

    public String getCurrencyAR() {
        return CurrencyAR;
    }

    public String getCodeEN() {
        return CodeEN;
    }

    public String getCodeAR() {
        return CodeAR;
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
