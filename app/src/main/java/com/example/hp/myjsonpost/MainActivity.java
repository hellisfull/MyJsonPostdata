package com.example.hp.myjsonpost;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ImageView imv;
    EditText ed1,ed2;
    private HashMap<String, String> loginParms;
    String email,pass;
    ServiceHandler serviceHandler222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serviceHandler222 =new ServiceHandler();
        imv=(ImageView)findViewById(R.id.imvv);
        ed1=(EditText)findViewById(R.id.ed1);
        ed2=(EditText)findViewById(R.id.ed2);
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = ed1.getText().toString();
                pass = ed2.getText().toString();
                if ((!email.equals(""))&&(!pass.equals("")))
                {
                    loginParms = new HashMap<>();
                    loginParms.put("email", email);
                    loginParms.put("password", pass);

                    new PostTask().execute("http://www.iroidtech.com/prestashop/veonlogin1.php");

                }

            }
        });
    }

   private class PostTask extends AsyncTask<String,Void,String> {

       @Override
       protected String doInBackground(String... params) {

           String response ="";
           try {
               response = serviceHandler222.performPostCall("http://iroidtech.com/prestashop/veonlogin1.php", loginParms);

           } catch (IOException e) {
               e.printStackTrace();
           }
           Log.d("LOG RES ###############", response);

           return response;
       }

       @Override
       protected void onPostExecute(String s) {
           super.onPostExecute(s);
           try {
               JSONObject jsonObject = new JSONObject(s);
               String status = jsonObject.getString("status");
               if (status.equals("success")) {
                   JSONObject dataobj = jsonObject.getJSONObject("data");
                   String firstname = dataobj.getString("firstname");
                   String lastname = dataobj.getString("lastname");
                   String id=dataobj.getString("id_customer");
                   Log.d("LOGIN###", firstname + "..." + lastname+"...."+id);
                   Intent ii=new Intent(getApplicationContext(),Main2Activity.class);
                   ii.putExtra("id",id);
                   ii.putExtra("namefirst",firstname);
                   ii.putExtra("namesecond",lastname);
                   startActivity(ii);

               } else {
                   String message = jsonObject.getString("message");
                   Log.d("FAILED###", message);
               }
           } catch (JSONException e) {
               e.printStackTrace();
           }

       }
   }


}
