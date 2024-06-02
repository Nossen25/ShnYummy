package com.example.nossenshniyami.Creditcard;

import static com.example.nossenshniyami.SignUp.SignUpMoudle.isValidEmail;

import android.content.Context;
import android.widget.Toast;

import com.example.nossenshniyami.FirebaseHelper.FirebaseHelper;
import com.example.nossenshniyami.Reposetory.Reposetory;
import com.google.firebase.auth.FirebaseAuth;

public class CreditCardModule
{

    FirebaseHelper firebaseHelper;
    Reposetory reposetory ;
    public CreditCardModule()
    {

    }
    public CreditCardModule(Context context)
    {
        this.firebaseHelper  = new FirebaseHelper();
        this.reposetory= new Reposetory(firebaseHelper,context);
    }

public Boolean CheckCreditcard(String fullname, String email, String phone, String cardnum, String date, String cvc, Context context)
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
    if (phone.length()!=10)
    {
        Toast.makeText(context, "טלפון חייב להכיל 10 מספרים", Toast.LENGTH_SHORT).show();
        return false;
    }
    if (!phone.matches("\\d+")) {
        Toast.makeText(context, "מספר טלפון יכול להכיל רק ספרות", Toast.LENGTH_SHORT).show();
        return false;
    }
    if (cardnum.length()!=16)
    {
        Toast.makeText(context, "מספר כרטיס חייב להכיל 16 מספרים", Toast.LENGTH_SHORT).show();
        return false;
    }
    if (!cardnum.matches("\\d+")) {
        Toast.makeText(context, "מספר כרטיס יכול להכיל רק ספרות", Toast.LENGTH_SHORT).show();
        return false;
    }
    if (date.isEmpty())
    {
        Toast.makeText(context, "אסור להשאיר תוקף ריקה", Toast.LENGTH_SHORT).show();
        return false;
    }
    else {
        int firstCommaIndex = date.indexOf('/');


       //בודק אם הוא הכניס לפי הפורמט
        if (firstCommaIndex == -1 ) {
            Toast.makeText(context, "פורמט תוקף לא חוקי. השתמש ב-'חודש/שנה'", Toast.LENGTH_LONG).show();
            return false;
        }


        String month = date.substring(0, firstCommaIndex).trim();
        String year = date.substring(firstCommaIndex + 1).trim();

        int monthNumber = Integer.parseInt(month);
        int yearNumber = Integer.parseInt(year);

        if (month.isEmpty()) {
            Toast.makeText(context, "חודש לא יכול להיות ריק", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (year.isEmpty()) {
            Toast.makeText(context, "שנה לא יכולה להיות ריקה", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (!month.matches("\\d+")) {
            Toast.makeText(context, " חודש חייב להיות ערך מספרי", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!year.matches("\\d+")) {
            Toast.makeText(context, " שנה חייבת להיות ערך מספרי", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(monthNumber>12||monthNumber==0)
        {
            Toast.makeText(context, " חודש חייב להיות בין1-12", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(yearNumber<24||yearNumber>33)
        {
            Toast.makeText(context, " שנה חייבת להיות בין24-33", Toast.LENGTH_SHORT).show();
            return false;
        }


    }
    if (cvc.length()!=3)
    {
        Toast.makeText(context, "cvc חייב להכיל 3 מספרים", Toast.LENGTH_SHORT).show();
        return false;
    }
    if (!cvc.matches("\\d+")) {
        Toast.makeText(context, "cvc יכול להכיל רק ספרות", Toast.LENGTH_SHORT).show();
        return false;
    }

    return true;

}
public void GetInfo(FirebaseAuth ma, FirebaseHelper.userFound userFound)
{
   reposetory.GetInfo(ma,userFound);
}

}
