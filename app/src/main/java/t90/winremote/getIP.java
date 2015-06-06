package t90.winremote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class getIP extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ip);
        Button setBtn = (Button) findViewById(R.id.setIp);
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIP();
            }
        });
    }

    public void setIP(){
        Intent b = new Intent(getApplicationContext(), Chat.class);
        String ip = String.valueOf(((EditText) findViewById(R.id.getIp)).getText());
        b.putExtra("ip", ip);
        startActivity(b);
    }
}
