package com.jcgomis.kata_foo_bar_quix.services.impl;

import org.springframework.stereotype.Service;
import com.jcgomis.kata_foo_bar_quix.services.ConvertService;

@Service
public class ConvertServiceImpl implements ConvertService {
    @Override
    public String convertNumber(int number) {
        if(number < 0 || number > 100) {
            throw new IllegalArgumentException("Le nombre doit être compris entre 0 et 100");
        }

        StringBuilder result = new StringBuilder();
 // Régle de 3 et 5
        if(number % 3 == 0){
            result.append("FOO");
        }
        if(number % 5 == 0){
            result.append("BAR");
        }
        // Régle de contenance des  nombre 3, 5 et 7
         String numberContains = String.valueOf(number);
        for(char digit: numberContains.toCharArray()){
            if(digit == '3'){
                result.append("FOO");
            }else if(digit == '5'){
                result.append("BAR");
            } else if (digit == '7') {
                result.append("QUIX");

            }
        }
        if(result.isEmpty()){

            return numberContains;
        }
        return result.toString();
    }
}
