package ksu.airuino;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    int value ;
    static String[] Pollutant_ID ,Sensor_ID,Pollutant_Value;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = (TextView)findViewById(R.id.textView2);

        new pmValue().execute();
    }


    class pmValue extends AsyncTask<String,String,String>
    {

        public pmValue() {
            super();
        }

        protected String doInBackground(String... params) {
            List<NameValuePair> list= new ArrayList<NameValuePair>();
            JSONObject jsonObject=JSONParser.makeHttpRequest("http://airuino.com/airapp/poll_value.php","POST",list);
            try {
                if(jsonObject!=null){
                    value=jsonObject.getInt("value1");
                    JSONArray jsonArray=jsonObject.getJSONArray("Pollutant_Value"); //To Get the Values
                    Pollutant_ID = new String[jsonArray.length()];
                    Sensor_ID = new String[jsonArray.length()];
                    Pollutant_Value = new String[jsonArray.length()];

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject objcet=jsonArray.getJSONObject(i);
                        Pollutant_ID[i] = objcet.getString("Pollutant_ID");
                        Sensor_ID[i]=objcet.getString("Sensor_ID");
                        Pollutant_Value[i] = objcet.getString("Pollutant_Value");
                    }

                }else{
                    value=0;
                }

            }catch (Exception e){
                Log.d("ERROR", e.getMessage());

            }


            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayList<Float> values1 = new ArrayList<Float>();
            if (value ==1) {
                float sum =0;
                status.setTextColor(Color.RED);
                //int id = Integer.parseInt(Pollutant_ID[0]);

            } else{
                Toast.makeText(getApplicationContext(),"Data retrieval failure...", Toast.LENGTH_LONG).show();
            }
        }

    }
}
