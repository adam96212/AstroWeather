package com.example.astroweather;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import com.example.astroweather.Frag.MainLayout;
import com.example.astroweather.Frag.MoonLayout;
import com.example.astroweather.Frag.SunLayout;
import com.rd.PageIndicatorView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainLayout.OnMessageReadListener {


    public ViewPager pointer;
    public PageIndicatorView pagePointer;
    public TextView sunrise, sunset, moonrise, moonset, moonFull, moonNew, civilTwilight, civilDawn;
    public TextView laMsg, loMsg;
    public TextView sunLong, sunLat, moonLon, moonLat, moonPhase, phase;
    public PagerAdapter pagerAdapter;
    String message;
    Boolean flaga;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pointer = findViewById(R.id.pager);
        List<Fragment> fList = new ArrayList<>();
        fList.add(new SunLayout());
        fList.add(new MainLayout());
        fList.add(new MoonLayout());

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);

        pagerAdapter = new SlidePageAdapter(getSupportFragmentManager(), fList);
        pointer.setAdapter(pagerAdapter);
        pagePointer = findViewById(R.id.pagePointerView);

        pointer.setCurrentItem(1);
        pointer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                pagePointer.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });



        if (findViewById(R.id.longiM) != null && findViewById(R.id.latiM) != null && findViewById(R.id.longiS) != null && findViewById(R.id.latiS) != null) {
            if(savedInstanceState!=null){
                return;
            }
            MainLayout PrimaryLayout = new MainLayout();
            FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction().add(R.id.longiM, PrimaryLayout);
            FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction().add(R.id.latiM, PrimaryLayout);
            FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction().add(R.id.longiS, PrimaryLayout);
            FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction().add(R.id.latiS, PrimaryLayout);
            fragmentTransaction1.commit();
            fragmentTransaction2.commit();
            fragmentTransaction3.commit();
            fragmentTransaction4.commit();
        }
    }
    @Override
    public void onSaveInstanceState (Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        civilDawn = findViewById(R.id.civilDawn);
        civilTwilight = findViewById(R.id.civilTwilight);
        moonPhase = findViewById(R.id.phase);
        phase = findViewById(R.id.sinodicDay);
        moonrise = findViewById(R.id.moonrise);
        moonset = findViewById(R.id.moonset);
        moonNew = findViewById(R.id.newMoon);
        moonFull = findViewById(R.id.fullMoon);
        if(flaga!=null) {
            savedInstanceState.putString("sunrise", sunrise.getText().toString());
            savedInstanceState.putString("sunset", sunset.getText().toString());
            savedInstanceState.putString("civilDawn", civilDawn.getText().toString());
            savedInstanceState.putString("civilTwilight", civilTwilight.getText().toString());
            savedInstanceState.putString("moonPhase", moonPhase.getText().toString());
            savedInstanceState.putString("sunrise", sunrise.getText().toString());
            savedInstanceState.putString("phase", phase.getText().toString());
            savedInstanceState.putString("moonrise", moonrise.getText().toString());
            savedInstanceState.putString("moonset", moonset.getText().toString());
            savedInstanceState.putString("moonNew", moonNew.getText().toString());
            savedInstanceState.putString("moonFull", moonFull.getText().toString());
            savedInstanceState.putString("message", message);
            savedInstanceState.putBoolean("flaga", flaga);

        }

    }

    @Override
    public void onRestoreInstanceState (Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        civilDawn = findViewById(R.id.civilDawn);
        civilTwilight = findViewById(R.id.civilTwilight);
        moonPhase = findViewById(R.id.phase);
        phase = findViewById(R.id.sinodicDay);
        moonrise = findViewById(R.id.moonrise);
        moonset = findViewById(R.id.moonset);
        moonNew = findViewById(R.id.newMoon);
        moonFull = findViewById(R.id.fullMoon);
        sunrise.setText(savedInstanceState.getString("sunrise"));
        sunset.setText(savedInstanceState.getString("sunset"));
        civilDawn.setText(savedInstanceState.getString("civilDawn"));
        civilTwilight.setText(savedInstanceState.getString("civilTwilight"));
        moonPhase.setText(savedInstanceState.getString("moonPhase"));
        phase.setText(savedInstanceState.getString("phase"));
        moonrise.setText(savedInstanceState.getString("moonrise"));
        moonset.setText(savedInstanceState.getString("moonset"));
        moonNew.setText(savedInstanceState.getString("moonNew"));
        moonFull.setText(savedInstanceState.getString("moonFull"));
        message=savedInstanceState.getString("message");
        flaga=savedInstanceState.getBoolean("flaga");

    }

    public static double round(double val, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        val = val * factor;
        long tmp = Math.round(val);
        return (double) tmp / factor;
    }
    public AstroDateTime getAstroDateTime() {
        long systemDate = System.currentTimeMillis();
        int year = Integer.parseInt(new SimpleDateFormat("yyyy", Locale.ENGLISH).format(systemDate));
        int month = Integer.parseInt(new SimpleDateFormat("MM", Locale.ENGLISH).format(systemDate));
        int day = Integer.parseInt(new SimpleDateFormat("dd", Locale.ENGLISH).format(systemDate));
        int hour = Integer.parseInt(new SimpleDateFormat("hh", Locale.ENGLISH).format(systemDate));
        int minute = Integer.parseInt(new SimpleDateFormat("mm", Locale.ENGLISH).format(systemDate));
        int second = Integer.parseInt(new SimpleDateFormat("ss", Locale.ENGLISH).format(systemDate));
        int zoneOffSetTime = 1;
        boolean dayLightSaving = true;
        return new AstroDateTime(year, month, day, hour, minute, second, zoneOffSetTime, dayLightSaving);
    }


    @Override
    public void onMessageRead(String message) {
        String[] msgLonLat = message.split("/");
        flaga=true;
        laMsg = findViewById(R.id.latiS);
        loMsg = findViewById(R.id.longiS);
        sunLong = findViewById(R.id.latiS);
        sunLong.setText(msgLonLat[0]);
        sunLat = findViewById(R.id.longiS);
        sunLat.setText(msgLonLat[1]);
        moonLon = findViewById(R.id.longiM);
        moonLon.setText(msgLonLat[0]);
        moonLat = findViewById(R.id.latiM);
        moonLat.setText(msgLonLat[1]);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        civilDawn = findViewById(R.id.civilDawn);
        civilTwilight = findViewById(R.id.civilTwilight);
        moonPhase = findViewById(R.id.phase);
        phase = findViewById(R.id.sinodicDay);
        moonrise = findViewById(R.id.moonrise);
        moonset = findViewById(R.id.moonset);
        moonNew = findViewById(R.id.newMoon);
        moonFull = findViewById(R.id.fullMoon);

        AstroCalculator.Location location;
        AstroCalculator astroCalculator;

        location = new AstroCalculator.Location(Double.valueOf(laMsg.getText().toString()), Double.valueOf(loMsg.getText().toString()));
        astroCalculator = new AstroCalculator(getAstroDateTime(),location);

        Double illumination = round(astroCalculator.getMoonInfo().getIllumination(),2);

        String getSunrisegetHour = String.valueOf(astroCalculator.getSunInfo().getSunrise().getHour()-1);
        if (Integer.valueOf(getSunrisegetHour) < 10)
        { getSunrisegetHour = "0" + getSunrisegetHour; }
        String getSunrisegetMinute = String.valueOf(astroCalculator.getSunInfo().getSunrise().getMinute());
        if (Integer.valueOf(getSunrisegetMinute) < 10)
        { getSunrisegetMinute = "0" + getSunrisegetMinute; }
        String getSunrisegetSecond = String.valueOf(astroCalculator.getSunInfo().getSunrise().getSecond());
        if (Integer.valueOf(getSunrisegetSecond) < 10)
        { getSunrisegetSecond = "0" + getSunrisegetSecond; }
        sunrise.setText("Sunrise at: " + "\n" + String.valueOf(getSunrisegetHour + ":" + getSunrisegetMinute + ":" + getSunrisegetSecond + " | Azimuth: " + Math.round(astroCalculator.getSunInfo().getAzimuthRise()) + "°"));

        String getSunsetgetHour = String.valueOf(astroCalculator.getSunInfo().getSunset().getHour()-1);
        if (Integer.valueOf(getSunsetgetHour) < 10)
        { getSunsetgetHour = "0" + getSunsetgetHour; }
        String getSunsetgetMinute = String.valueOf(astroCalculator.getSunInfo().getSunset().getMinute());
        if (Integer.valueOf(getSunsetgetMinute) < 10)
        { getSunsetgetMinute = "0" + getSunsetgetMinute; }
        String getSunsetgetSecond = String.valueOf(astroCalculator.getSunInfo().getSunset().getSecond());
        if (Integer.valueOf(getSunsetgetSecond) < 10)
        { getSunsetgetSecond = "0" + getSunsetgetSecond; }
        sunset.setText("Sunset at: " + "\n"  + String.valueOf(getSunsetgetHour + ":" + getSunsetgetMinute + ":" + getSunsetgetSecond + " | Azimuth: " + Math.round(astroCalculator.getSunInfo().getAzimuthSet()) + "°"));

        String getTwilightEveninggetHour = String.valueOf(astroCalculator.getSunInfo().getTwilightEvening().getHour()-1);
        if (Integer.valueOf(getTwilightEveninggetHour) < 10)
        { getTwilightEveninggetHour = "0" + getTwilightEveninggetHour; }
        String getTwilightEveninggetMinute = String.valueOf(astroCalculator.getSunInfo().getTwilightEvening().getMinute());
        if (Integer.valueOf(getTwilightEveninggetMinute) < 10)
        { getTwilightEveninggetMinute = "0" + getTwilightEveninggetMinute; }
        String getTwilightEveninggetSecond = String.valueOf(astroCalculator.getSunInfo().getTwilightEvening().getSecond());
        if (Integer.valueOf(getTwilightEveninggetSecond) < 10)
        { getTwilightEveninggetSecond = "0" + getTwilightEveninggetSecond; }
        civilTwilight.setText("Civil Twilight at: " + "\n"  + String.valueOf(getTwilightEveninggetHour + ":" + getTwilightEveninggetMinute + ":" + getTwilightEveninggetSecond));

        String getTwilightMorninggetHour = String.valueOf(astroCalculator.getSunInfo().getTwilightMorning().getHour()-1);
        if (Integer.valueOf(getTwilightMorninggetHour) < 10)
        { getTwilightMorninggetHour = "0" + getTwilightMorninggetHour; }
        String getTwilightMorningetMinute = String.valueOf(astroCalculator.getSunInfo().getTwilightMorning().getMinute());
        if (Integer.valueOf(getTwilightMorningetMinute) < 10)
        { getTwilightMorningetMinute = "0" + getTwilightMorningetMinute; }
        String getTwilightMorninggetSecond = String.valueOf(astroCalculator.getSunInfo().getTwilightMorning().getSecond());
        if (Integer.valueOf(getTwilightMorninggetSecond) < 10)
        { getTwilightMorninggetSecond = "0" + getTwilightMorninggetSecond; }
        civilDawn.setText("Civil Dawn at: " + "\n"  + String.valueOf(getTwilightMorninggetHour + ":" + getTwilightMorningetMinute + ":" + getTwilightMorninggetSecond));

        String getMoonrisegetHour = String.valueOf(astroCalculator.getMoonInfo().getMoonrise().getHour()-1);
        if (Integer.valueOf(getMoonrisegetHour) < 10)
        { getMoonrisegetHour = "0" + getMoonrisegetHour; }
        String getMoonrisegetMinute = String.valueOf(astroCalculator.getMoonInfo().getMoonrise().getMinute());
        if (Integer.valueOf(getMoonrisegetMinute) < 10)
        { getMoonrisegetMinute = "0" + getMoonrisegetMinute; }
        String getMoonrisegetSecond = String.valueOf(astroCalculator.getMoonInfo().getMoonrise().getSecond());
        if (Integer.valueOf(getMoonrisegetSecond) < 10)
        { getMoonrisegetSecond = "0" + getMoonrisegetSecond; }
        moonrise.setText("Civil Dawn at: " + "\n"  + String.valueOf(getMoonrisegetHour + ":" + getMoonrisegetMinute + ":" + getMoonrisegetSecond));

        String getMoonsetgetHour = String.valueOf(astroCalculator.getMoonInfo().getMoonset().getHour()-1);
        if (Integer.valueOf(getMoonsetgetHour) < 10)
        { getMoonsetgetHour = "0" + getMoonsetgetHour; }
        String getMoonsetgetMinute = String.valueOf(astroCalculator.getMoonInfo().getMoonset().getMinute());
        if (Integer.valueOf(getMoonsetgetMinute) < 10)
        { getMoonsetgetMinute = "0" + getMoonsetgetMinute; }
        String getMoonsetgetSecond = String.valueOf(astroCalculator.getMoonInfo().getMoonset().getSecond());
        if (Integer.valueOf(getMoonsetgetSecond) < 10)
        { getMoonsetgetSecond = "0" + getMoonsetgetSecond; }
        moonset.setText("Moonset at: " + "\n" + String.valueOf(getMoonsetgetHour + ":" + getMoonsetgetMinute + ":" + getMoonsetgetSecond));

        String getNextFullMoongetYear = String.valueOf(astroCalculator.getMoonInfo().getNextFullMoon().getYear());
        if (Integer.valueOf(getMoonsetgetHour) < 10)
        { getMoonsetgetHour = "0" + getMoonsetgetHour; }
        String getNextFullMoongetMonth = String.valueOf(astroCalculator.getMoonInfo().getNextFullMoon().getMonth());
        if (Integer.valueOf(getMoonsetgetMinute) < 10)
        { getMoonsetgetMinute = "0" + getMoonsetgetMinute; }
        String getNextFullMoongetDay = String.valueOf(astroCalculator.getMoonInfo().getNextFullMoon().getDay());
        if (Integer.valueOf(getMoonsetgetSecond) < 10)
        { getMoonsetgetSecond = "0" + getMoonsetgetSecond; }
        moonFull.setText("Full Moon on: " + "\n" + String.valueOf(getNextFullMoongetYear + "." + getNextFullMoongetMonth + "." + getNextFullMoongetDay));

        String getNextNewMoongetYear = String.valueOf(astroCalculator.getMoonInfo().getNextNewMoon().getYear());
        if (Integer.valueOf(getNextNewMoongetYear) < 10)
        { getNextNewMoongetYear = "0" + getNextNewMoongetYear; }
        String getNextNewMoongetMonth = String.valueOf(astroCalculator.getMoonInfo().getNextNewMoon().getMonth());
        if (Integer.valueOf(getNextNewMoongetMonth) < 10)
        { getNextNewMoongetMonth = "0" + getNextNewMoongetMonth; }
        String getNextNewMoongetDay = String.valueOf(astroCalculator.getMoonInfo().getNextNewMoon().getDay());
        if (Integer.valueOf(getNextNewMoongetDay) < 10)
        { getNextNewMoongetDay = "0" + getNextNewMoongetDay; }
        moonNew.setText("New Moon on: " + "\n" + String.valueOf(getNextNewMoongetYear + "." + getNextNewMoongetMonth + "." + getNextNewMoongetDay));
        moonPhase.setText("Phase: " + "\n" + String.valueOf(Math.round(illumination * 100.0) + "%"));
        phase.setText((int) astroCalculator.getMoonInfo().getAge() + "th synodic day");

    }

}