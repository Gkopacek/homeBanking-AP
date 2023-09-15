package com.ap.mindhub.homebanking.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class CardUtils {

   private CardUtils(){}

    public static String getCardNumber(){
        List<String> numbers = new ArrayList<>();

        Random random = new Random();

        for(int i=0; i<4; i++ ){
            if(i!=0){
                numbers.add("-"+String.format("%04d",random.nextInt(10000)));
            }else{
                numbers.add(String.format("%04d",random.nextInt(10000)));
            }

        }
        String concatNumber = "";
        for(String number: numbers){
            concatNumber += number;

        }
        return concatNumber;
    }

    public static int getCvv() {
        return (int) ((Math.random() * (999 - 001)) + 001);
    }
}
