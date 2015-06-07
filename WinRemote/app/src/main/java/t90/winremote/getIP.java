package t90.winremote;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import eu.livotov.zxscan.ScannerView;


public class getIP extends ActionBarActivity {

    ScannerView scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ip);

        scanner = (ScannerView) findViewById(R.id.scanner);

        int width = getResources().getDisplayMetrics().widthPixels-20;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, width);
        scanner.setLayoutParams(params);

        scanner.setScannerViewEventListener(new ScannerView.ScannerViewEventListener(){
            @Override
            public void onScannerReady() {}
            @Override
            public void onScannerFailure(int i) {}

            public boolean onCodeScanned(final String data){
                scanner.stopScanner();
                Toast.makeText(getApplicationContext(), "Success!!!", Toast.LENGTH_SHORT).show();
                Intent b = new Intent(getApplicationContext(), Chat.class);
                if(data.startsWith("192.168")) {
                    Log.d("ip", data);
                    b.putExtra("ip", data);
                    startActivity(b);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Oops, some error occurred, Please restart app",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scanner.startScanner();
            }
        }, 1000);


    }


}
