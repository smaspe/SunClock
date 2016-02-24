package com.smaspe.sunclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.smaspe.sunclock.network.SunriseSunsetClient;
import com.smaspe.sunclock.network.SunriseSunsetResult;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.sunrise_time)
    TextView sunriseTime;
    @Bind(R.id.sunset_time)
    TextView sunsetTime;

    SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Subscription subscription = SunriseSunsetClient.api().getTodaySunriseSunsetTimes(0, 0)
                .filter(response -> TextUtils.equals("OK", response.status))
                .map(response -> response.results)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::updateUI);
    }

    public void updateUI(SunriseSunsetResult result) {
        sunriseTime.setText(format.format(result.sunrise));
        sunsetTime.setText(format.format(result.sunset));
    }
}
