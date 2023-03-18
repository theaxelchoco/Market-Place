package com.example.group17project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import android.location.Location;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLOutput;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserLocationUnitTest {

    static UserLocation userLocation;
    static Location currLocation;

    @BeforeClass
    public static void setup() throws Exception{
        userLocation = mock(UserLocation.class);
        userLocation.turnOnLocation();
        userLocation.getCurrentLocation();

        Location currLocation = userLocation.getUserLocation();
        when (userLocation.getLocationName(currLocation.getLatitude(),currLocation.getLongitude())).thenReturn("Mountain View");
    }

    @AfterClass
    public static void tearDown(){
        System.gc();
    }

    @Test
    public void getLocation(){
        assertEquals("Mountain View",userLocation.getLocationName(currLocation.getLatitude(),currLocation.getLongitude()));
        Mockito.verify(userLocation, Mockito.atLeastOnce()).getLocationName(currLocation.getLatitude(),currLocation.getLongitude());
    }

}
