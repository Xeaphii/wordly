package wordly.xeaphii.com.wordly;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Sunny on 1/22/2015.
 */
public class Results extends Activity{
   TextView Results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        Bundle b=this.getIntent().getExtras();
        String[] array=b.getStringArray("words");
        Results = (TextView) findViewById(R.id.results);
        String output = "";
        for (int i = 0 ; i < array.length;i++)
        {
            output = output+array[i]+" \n";
        }
        Results.setText(output);

    }
}
