package t90.winremote;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;


public class Chat extends Activity{

    EditText iptxt;
    TextView chatbox;
    Button sendbtn;

    InetAddress serverAddress;
    Socket sckt = null;
    OutputStream os;
    InputStream is;

    Runnable r;
    Handler h;

    int PORT = 8000;
    String ServerIP = "192.168.1.3";

    Boolean connectionEstablished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);

        iptxt = (EditText) findViewById(R.id.ipText);
        chatbox = (TextView) findViewById(R.id.chatText);
        sendbtn = (Button) findViewById(R.id.senButton);

        ServerIP = getIntent().getExtras().getString("ip");

        sendbtn.setEnabled(false);
        sendbtn.setText("conecting");
        startConnection();

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String str = String.valueOf(iptxt.getText());
                    iptxt.setText("");
                    new Sender().execute(str);
                    chatbox.append("\n" + str);
                } catch (Exception e){

                }
            }
        });

//        r = new Runnable() {
//            @Override
//            public void run() {
//                new Reciever().execute();
//                h.postDelayed(r, 10);
//            }
//        };
    }

    private void startConnection() {
        new initConnection().execute();
    }

    public class initConnection extends AsyncTask <Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            try {
                serverAddress = InetAddress.getByName(ServerIP);
                sckt = new Socket(serverAddress, PORT);
                os = sckt.getOutputStream();
                is = sckt.getInputStream();
                os.write("123".getBytes());
                byte[] data = new byte[100];
                is.read(data);
                Log.d("chat", String.valueOf(data));
                if(String.valueOf(data).length()>2){
                    connectionEstablished = true;
                    sendbtn.setEnabled(true);
                    sendbtn.setText("Send");
                }
            }catch (Exception e){
                Log.d("chat", e.getLocalizedMessage());
            }
            return null;
        }
    }

    public class Sender extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... strs) {
            try {
                String str = strs[0];
                os.flush();
                os.write(str.getBytes());
                new Reciever().execute();
            }catch (Exception e){
                Log.d("chat", e.getLocalizedMessage());
            }
            return null;
        }
    }

    public class Reciever extends AsyncTask<Void, Void, String > {
        @Override
        protected String doInBackground(Void... params){
            try {
                byte[] data = new byte[200];
                int bytes = is.read(data);
                String str = new String(data);
                Log.d("chat", str);
                return str;
            } catch (IOException e){

            }
            return "0";
        }

        protected void onPostExecute(String str) {
            chatbox.append("\n" + str);
        }
    }

}

