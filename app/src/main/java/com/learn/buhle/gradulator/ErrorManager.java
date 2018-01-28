package com.learn.buhle.gradulator;

/**
 * Created by bmwanza on 14/01/18.
 */

/**
 * A Class that will have methods to handle and prevent errors
 */
public class ErrorManager
{

    /*
    Validates a String that was recieved from input
    Checks for multiple decimals in the input
    Returns a string that only considers the first occuring decimal point
    eg:
    8.8.34 = 8.834
    */
    public static String validateString(String inputString)
    {
        String validString = "";
        boolean vStringHasDecimal = false;


        for(int i = 0; i < inputString.length(); i++)
        {
            if(inputString.charAt(i) != '.' && inputString.charAt(i) != '%')
            {
                validString += inputString.charAt(i);
            }
            else if(inputString.charAt(i) == '.' && !vStringHasDecimal)
            {
                vStringHasDecimal = true;
                validString += inputString.charAt(i);
            }
        }

        return validString;
    }


    public static String formatValues(double value)
    {
        return String.format("%.1f", value * 100) + "%";
    }

    public static boolean validTargetGrade(double target)
    {
        return target <= 100 && target >= 1;
    }


}
