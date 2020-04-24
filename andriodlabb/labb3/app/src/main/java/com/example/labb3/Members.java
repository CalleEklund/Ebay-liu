package com.example.labb3;

import java.util.ArrayList;

public class Members {
    private ArrayList<Member> medlemmar;


    public ArrayList<String> getMedlemmar() {
                ArrayList<String> list = new ArrayList<>();
                for (Member medlem: medlemmar
                ) {
                    String currmed;
                    currmed = "Namn: " + medlem.getNamn() + "\n" + "Epost: " + medlem.getEpost() + "\n" +
                            "Svarade: " + medlem.getSvarade();
                    list.add(currmed);
        }
        return list;
    }
}

