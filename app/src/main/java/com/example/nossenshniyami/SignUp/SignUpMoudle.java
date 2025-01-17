package com.example.nossenshniyami.SignUp;

import static com.example.nossenshniyami.Updel.UpdelMoudle.areBothStringsSameLanguage;

import android.content.Context;
import android.widget.Toast;

import com.example.nossenshniyami.Account.Account;
import com.example.nossenshniyami.FirebaseHelper.FirebaseHelper;
import com.example.nossenshniyami.Reposetory.Reposetory;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpMoudle
{
   private  Reposetory reposetory;

    public SignUpMoudle(FirebaseHelper firebaseHelper,Context context) {
       reposetory = new Reposetory(firebaseHelper,context);
    }
    public boolean Check (String fullname, String email, String password, String address, String phone, Context context)
    {

        if (fullname.equals(""))
        {
            Toast.makeText(context, "צריך להכניס שם מלא", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (fullname.length()>18||fullname.length()<4)
        {
            Toast.makeText(context,"שם מלא חייב להיות בין 4 - 18 תווים", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!fullname.matches("^[a-zA-Z\u0590-\u05FF ]+$")) {
            Toast.makeText(context, "השם יכול להכיל רק אותיות באנגלית או בעברית ורווחים", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.equals(""))
        {
            Toast.makeText(context, "צריך להכניס אימייל", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isValidEmail(email))
        {
            Toast.makeText(context, "אימייל לא תקין", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.length()>25||email.length()<6)
        {
            Toast.makeText(context, "אמייל צריך להיות בין 6-25 תווים", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length()>20||email.length()<8)
        {
            Toast.makeText(context, "סיסמא צריכה להיות בין 8-20 תווים", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (address.isEmpty())
        {
            Toast.makeText(context, "אסור להשאיר כתובת ריקה", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            int firstCommaIndex = address.indexOf(',');
            int secondCommaIndex = address.indexOf(',', firstCommaIndex + 1);

           //בודק אם הכניס כמו שצריך
            if (firstCommaIndex == -1 || secondCommaIndex == -1) {
                Toast.makeText(context, "פורמט כתובת לא חוקי. השתמש ב-'רחוב, עיר, מספר בית'", Toast.LENGTH_LONG).show();
                return false;
            }


            String street = address.substring(0, firstCommaIndex).trim();
            String city = address.substring(firstCommaIndex + 1, secondCommaIndex).trim();
            String houseNumber = address.substring(secondCommaIndex + 1).trim();






            if (street.isEmpty()) {
                Toast.makeText(context, "רחוב לא יכול להיות ריק", Toast.LENGTH_SHORT).show();
                return false;
            }


            if (city.isEmpty()) {
                Toast.makeText(context, "עיר לא יכולה להיות ריקה", Toast.LENGTH_SHORT).show();
                return false;
            }

           //בדיקה שספרות
            if (!houseNumber.matches("\\d+")) {
                Toast.makeText(context, "מספר בית חייב להיות ערך מספרי", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (!areBothStringsSameLanguage(street,city))
            {
                Toast.makeText(context, "רחוב ועיר צריך להיות אותו שפה(אנגלית/עברית)", Toast.LENGTH_LONG).show();
                return false;
            }
        }
                if (phone.length()!=10)
                {
                  Toast.makeText(context, "טלפון חייב להכיל 10 מספרים", Toast.LENGTH_SHORT).show();
                  return false;
                }
        if (!phone.matches("\\d+")) {
            Toast.makeText(context, "מספר טלפון יכול להכיל רק ספרות", Toast.LENGTH_SHORT).show();
            return false;
        }

                return  true;
    }
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);


        return matcher.matches();
    }
    public void SignUp(Account account, Map<String, Object> map)
    {
     reposetory.SigNUp(account,map);
    }

}
