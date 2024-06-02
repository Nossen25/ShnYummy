package com.example.nossenshniyami.Admin;

import static com.example.nossenshniyami.Updel.UpdelMoudle.areBothStringsSameLanguage;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

public class AdminMoudle
{






    public AdminMoudle(){}


    public Boolean CheckIfBusOk(String name, String address, String phone, String info, ImageView imageView, Context context)
    {
        if (name.equals(""))
        {
            Toast.makeText(context, "צריך להכניס שם מלא", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (name.length()>18||name.length()<4)
        {
            Toast.makeText(context,"שם מלא חייב להיות בין 4 - 18 תווים", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!name.matches("^[a-zA-Z\u0590-\u05FF ]+$")) {
            Toast.makeText(context, "השם יכול להכיל רק אותיות באנגלית או בעברית ורווחים", Toast.LENGTH_SHORT).show();
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

         //בודק אם הפורמט חוקי
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

          //בודק שרק ספרות
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
        if (info.length()>80||info.length()<4)
        {
            Toast.makeText(context, "מידע בסיסי צריך להיות בין 4-80 תווים", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!name.matches("^[a-zA-Z\u0590-\u05FF ]+$")) {
            Toast.makeText(context, "השם יכול להכיל רק אותיות באנגלית או בעברית ורווחים", Toast.LENGTH_SHORT).show();
            return false;
        }
      if (imageView.getDrawable() == null) {
        Toast.makeText(context, "חייב לקחת תמונה בשביל להוסיף עסק", Toast.LENGTH_SHORT).show();
        return false;
    }
        return true;
    }



}

