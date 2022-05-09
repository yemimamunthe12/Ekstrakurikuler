package com.example.ekskulstoda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private  static final String URL = "jdbc:oracle:thin:@ec2-54-188-53-95.us-west-2.compute.amazonaws.com:1521:XE";
    private static final String USERNAME = "hr";
    private static final String PASSWORD = "mima";

    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
    }

    public void buttonConnectToOracleDB(View view){
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Toast.makeText(this, "CONNECTED", Toast.LENGTH_LONG).show();

            Statement statement = connection.createStatement();
            StringBuffer stringBuffer = new StringBuffer();
            ResultSet resultSet = statement.executeQuery("select username, pass from account");
            while (resultSet.next()){
                stringBuffer.append(resultSet.getString(1) + "\n");
            }
            textView.setText(stringBuffer.toString());
            connection.close();
        }
        catch (Exception e){
            textView.setText(e.toString());
        }
    }
}