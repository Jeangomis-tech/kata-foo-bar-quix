package com.jcgomis.kata_foo_bar_quix.batch.processors;

import com.jcgomis.kata_foo_bar_quix.services.ConvertService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class FooBarQuixProcessor implements ItemProcessor<String, String> {

   @Autowired
   private ConvertService convertService ;


    @Override
    public String process(String item) throws Exception {
        try{
            int number = Integer.parseInt(item.trim());
            String result = convertService.convertNumber(number);
            return number + "\"" + result + "\"";
        }catch(NumberFormatException e){

            return item + " \" Error: Invalid number format!\"" ;

        }catch(IllegalArgumentException e){
            return item + " \" Error: " + e.getMessage() + "\"" ;

        }

    }
    }

