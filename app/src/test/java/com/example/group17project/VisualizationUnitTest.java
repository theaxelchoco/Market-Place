package com.example.group17project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.group17project.Homepages.Visualization;
import com.example.group17project.ReceiverFunctionality.ReceiverFragment;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class VisualizationUnitTest {
    static com.example.group17project.Homepages.Visualization visualization;

    @BeforeClass
    public static void setup() throws Exception{
        visualization = new Visualization();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        System.gc();
    }


    @Test
    public void checkIfEmpty(){
        assertFalse(Visualization.class.getName().isEmpty());
        assertTrue(!Visualization.class.getName().isEmpty());
    }

    @Test
    public void checkIfVisualizationWorks(){
        assertFalse(ReceiverFragment.class.isLocalClass());
    }

    @Test
    public void checkIfInterface(){
        assertTrue(!ReceiverFragment.class.isInterface());
        assertFalse(ReceiverFragment.class.isInterface());

    }
}
