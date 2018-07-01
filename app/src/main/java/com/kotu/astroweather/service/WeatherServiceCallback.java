package com.kotu.astroweather.service;

import com.kotu.astroweather.data.Channel;


public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);
}
