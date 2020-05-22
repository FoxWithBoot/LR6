package com.example.lr6_1;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {
    RecyclerFragment recyclerFragment;
    ViewPagerFragment viewPagerFragment;
    final static String TAG_1 = "RECYCLER_FRAGMENT";
    final static String TAG_2 = "VIEW_PAGER_FRAGMENT";
    Click click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        click = new Click() {
            @Override
            public void click(int position) {
                viewPagerFragment = new ViewPagerFragment(position);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame, viewPagerFragment, TAG_2);
                transaction.commit();
            }
        };



        recyclerFragment = new RecyclerFragment(click);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_frame, recyclerFragment, TAG_1);
        transaction.commit();
        Button button = findViewById(R.id.cart_btn_main);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        Button adminButton = findViewById(R.id.admin_btn_main);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().findFragmentByTag(TAG_2) != null){
            recyclerFragment = new RecyclerFragment(click);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, recyclerFragment, TAG_1)
                    .commit();
        } else {
            finish();
        }
    }
}
