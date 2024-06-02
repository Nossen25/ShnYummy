package com.example.nossenshniyami.Updel;

import static com.example.nossenshniyami.SignUp.SignUpMoudle.isValidEmail;

import android.content.Context;
import android.widget.Toast;

import com.example.nossenshniyami.FirebaseHelper.FirebaseHelper;
import com.example.nossenshniyami.Reposetory.Reposetory;

public class UpdelMoudle
{
  private   Reposetory  reposetory;

  private FirebaseHelper firebaseHelper ;
    public UpdelMoudle(Context context)
    {

        this.firebaseHelper= new FirebaseHelper();

        this.reposetory=new Reposetory(firebaseHelper,context);

    }
    public void Update(String email, String name, String password, String address, String phoneNumber, int toApp, FirebaseHelper.whenDone callback) {
        reposetory.Update(email, name, password, address, phoneNumber, toApp, callback);
    }
    public void delete(String email, FirebaseHelper.whenDone callback) {
        reposetory.delete(email, callback);
    }

    public Boolean CheckBeforeUpdate(String fullname, String email, String password, String address, String phone, Context context)
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
        if (!fullname.matches("^[a-zA-Z\u0590-\u05FF]+$"))
        {
            Toast.makeText(context, "השם יכול להכיל רק אותיות באנגלית או בעברית", Toast.LENGTH_SHORT).show();
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
        //מדלג על הסיסמא לבדוק עם צאט
        //בכללי length לא עובד בכל הפרוייקט
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

            // בודק אם הכניס לפי הפורמט
            if (firstCommaIndex == -1 || secondCommaIndex == -1) {
                Toast.makeText(context, "פורמט כתובת לא חוקי. השתמש ב-'רחוב, עיר, מספר בית'", Toast.LENGTH_LONG).show();
                return false;
            }

            // Extract the substrings
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

            //בדיקה שרק ספרות
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

    public static boolean areBothStringsSameLanguage(String str1, String str2) {
        boolean isStr1English = isEnglish(str1);
        boolean isStr1Hebrew = isHebrew(str1);
        boolean isStr2English = isEnglish(str2);
        boolean isStr2Hebrew = isHebrew(str2);

        return (isStr1English && isStr2English) || (isStr1Hebrew && isStr2Hebrew);
    }

    public static boolean isEnglish(String str) {
        return str.matches("^[a-zA-Z\\s]+$");
    }

    public static boolean isHebrew(String str) {
        return str.matches("^[\u0590-\u05FF\\s]+$");
    }


}
