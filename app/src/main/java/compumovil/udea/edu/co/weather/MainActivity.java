package compumovil.udea.edu.co.weather;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity {

    Button button;
    TextView temp, city, description;
    EditText location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button) findViewById(R.id.button);
        temp=(TextView) findViewById(R.id.temp);
        city=(TextView) findViewById(R.id.city);
        location=(EditText) findViewById(R.id.editText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class WeatherTask extends AsyncTask<String,Void,Void> {

        private static final String TAG="WeatherTask";
        private String Content;
        private String Error=null;
        private ProgressDialog Dialog=new ProgressDialog(MainActivity.this);
        String data="";

        @Override
        protected void onPreExecute(){

        //super.onPreExecute();
        //StartProgressDialog(Message)

            Dialog.setMessage("Pleasewait..");
            Dialog.show();

        }
        @Override
        //CallafteronPreExecutemethod
        protected Void doInBackground(String...params){

        /************MakePostCallToWebServer***********/
        //BufferedReaderreader=null;
        //Senddata

            try   {

                data=((new WeatherHttpClient()).getWeatherData(params[0]));
                Log.v(TAG, "Info:" + data);

            }

            catch(Exception ex) {

                Error=ex.getMessage();

            }

/*****************************************************/

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid){
        //NOTE:YoucancallUIElementhere.
        //Closeprogressdialog
            Dialog.dismiss();

            if(Error!=null){

            }else{

                //ShowResponseJsonOnScreen(activity)
                /******************StartParseResponseJSONData*************/

                String OutputData="";
                JSONObject jsonResponse;

                try{

                /******CreatesanewJSONObjectwithname/valuemappingsfromtheJSONstring.
                ********/

                    jsonResponse=new JSONObject(data);

                /*****ReturnsthevaluemappedbynameifitexistsandisaJSONArray.***/
                /*******Returnsnullotherwise.*******/
                //JSONArrayjsonMainNode=jsonResponse.optJSONArray("Android");

                    OutputData=jsonResponse.optString("name").toString();

                //Log.v("City",OutputData);

                    city.setText(OutputData);
                    OutputData=jsonResponse.getJSONObject("main").optString("temp").toString();
                    temp.setText(OutputData+"°C");
                    OutputData= jsonResponse.getJSONArray("weather").getJSONObject(0).optString("main").toString();
                   // description.setText(OutputData);

                }catch(JSONException e){

                    e.printStackTrace();

                }

            }

        }

    }

    public void cualquierNombre(View view){

        new WeatherTask().execute(location.getText().toString());

    }
}
