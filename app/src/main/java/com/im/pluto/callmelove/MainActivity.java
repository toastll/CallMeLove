package com.im.pluto.callmelove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.im.pluto.callmelove.widget.LoveLayout;

public class MainActivity extends AppCompatActivity{


    private Button btnLove;
    private LoveLayout loveLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loveLayout = (LoveLayout) findViewById(R.id.loveLayout);
    }


    /**
     * 此处需要添加View
     * @param view
     */
    public void start(View view){
        /*低耦合，高内聚
        *业务逻辑和效果逻辑分开？*/
        loveLayout.addLove();
        Toast.makeText(MainActivity.this, "我被点击了", Toast.LENGTH_SHORT).show();
    }

}
