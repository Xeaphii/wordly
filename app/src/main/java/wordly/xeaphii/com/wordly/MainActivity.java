package wordly.xeaphii.com.wordly;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener {

    Button GetCode;
    EditText Message;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetCode = (Button) findViewById(R.id.get_code);
        GetCode.setOnClickListener(this);
        Message = (EditText) findViewById(R.id.message_text);
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if (sharedpreferences.getString("Initialize", "00").equals("00")) {
            BackgroundTask task = new BackgroundTask(MainActivity.this);
            task.execute();
        }
       // Toast.makeText(getApplicationContext(), Boolean.toString(sharedpreferences.getString("Initialize", "00").equals("00")), Toast.LENGTH_LONG).show();

    }

    private class BackgroundTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;

        public BackgroundTask(MainActivity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading awesomeness :) Please wait");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
            editor.putString("Initialize", "1");
            editor.commit();

        }

        @Override
        protected Void doInBackground(Void... params) {
            WordListsDatabase db = new WordListsDatabase(getApplicationContext());
            InputStream XmlFileInputStream = getResources().openRawResource(R.raw.wordlist); // getting XML
            String sxml[] = readTextFile(XmlFileInputStream).split("[\\r\\n]+");
            for (int i = 0 ; i < sxml.length;i++)
            {
                db.addWord(new Words(sxml[i]));
            }

            return null;
        }

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

    @Override
    public void onClick(View v) {
        String MessageCode = Message.getText().toString();
        String lines[] = MessageCode.split("[\\r\\n]+");
        MessageCode = "";
        for (int i = 0; i < lines.length; i++) {
            MessageCode = MessageCode + lines[i].substring(0, 1);
        }
        WordListsDatabase db = new WordListsDatabase(getApplicationContext());
        List<Words> output =  db.GetMatches(MessageCode);
        String out[] = {"","","","",""};
        for (int i = 0 ; i < output.size();i++)
        {
            out[i] = output.get(i).getWord();
        }
//        Toast.makeText(getApplicationContext(),output.get(0).getWord(),Toast.LENGTH_LONG).show();
        // Toast.makeText(getApplicationContext(),MessageCode,Toast.LENGTH_SHORT).show();
        Bundle b=new Bundle();
        b.putStringArray("words",out);
        Intent i=new Intent(getApplicationContext(), Results.class);
        i.putExtras(b);
        startActivity(i);

    }
    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }
}
