package com.latihan.applicationtodo.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.latihan.applicationtodo.MainActivity;
import com.latihan.applicationtodo.R;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    //untuk mendeklarasikan semua variable yang dibutuhkan

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    int position = 0;
    Button btnMulai, btnSelanjutnya;
    Animation btnAnim;
    TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // membuat activity full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // ketika aktivitas ini akan diluncurkan, memeriksa apakah sudah dibuka sebelumnya atau tidak

        if (restorePrefData()) {

            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActivity);
            finish();


        }
        setContentView(R.layout.activity_intro);
        // menyembunyikan bilah tindakan

        getSupportActionBar().hide();

        // ini views
        btnSelanjutnya = findViewById(R.id.btn_next);
        btnMulai = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);
        tvSkip = findViewById(R.id.tv_skip);

        // mengisi layar daftar

        final List<ItemScreen> mList = new ArrayList<>();
        mList.add(new ItemScreen("Selamat Datang ", "Di aplikais Todo Duty, dengan Aplikasi ini anda dapat mencatat tugas sehari-harimu  ", R.drawable.image1));
        mList.add(new ItemScreen("Apa yang bisa di lakukan aplikasi ini?", "Aplikasi bisa mengedit, menghapus, membuat, dan mencaricatat tugas sehari-harimu ", R.drawable.image2));

        // atur viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        // setup tablayout dengan viewpager

        tabIndicator.setupWithViewPager(screenPager);

        // button selanjutnya

        btnSelanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                if (position < mList.size()) {

                    position++;
                    screenPager.setCurrentItem(position);


                }

                if (position == mList.size() - 1) { // when we rech to the last screen

                    // TODO : show the GETSTARTED Button and hide the indicator and the next button

                    loaddLastScreen();


                }


            }
        });

        // tablayout menambahkan perubahan


        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == mList.size() - 1) {

                    loaddLastScreen();

                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // button mulai

        btnMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //membuka main activity

                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
                // kita juga perlu menyimpan nilai boolean ke penyimpanan sehingga lain kali saat pengguna menjalankan aplikasi
                // kita bisa tahu bahwa dia sudah memeriksa aktivitas layar intro
                savePrefsData();
                finish();


            }
        });

        // Button Lewati

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });


    }

    private boolean restorePrefData() {


        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend", false);
        return isIntroActivityOpnendBefore;


    }

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend", true);
        editor.commit();


    }

    // menuunjukkan Tombol GETSTARTED dan menyembunyikan indikator dan tombol selanjutnya
    private void loaddLastScreen() {

        btnSelanjutnya.setVisibility(View.INVISIBLE);
        btnMulai.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        // TODO : ADD an animation the getstarted button
        // penyiapan animasi
        btnMulai.setAnimation(btnAnim);


    }
}
