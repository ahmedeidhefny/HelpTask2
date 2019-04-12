package com.hardtask.eid.ahmed.registertask.model;

import java.util.Locale;

public class Code {
    private int Code;
    private String CodeEN,CodeAR;
    private String language ;


    public Code(int code, String codeEN, String codeAR) {
        Code = code;
        CodeEN = codeEN;
        CodeAR = codeAR;
        language = Locale.getDefault().getISO3Language();
    }

    public Code(String codeEN, String codeAR) {
        CodeEN = codeEN;
        CodeAR = codeAR;
        language = Locale.getDefault().getISO3Language();
    }

    public int getCode() {
        return Code;
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
            return CodeEN;
        }else {
            return CodeAR;
        }

    }
}
