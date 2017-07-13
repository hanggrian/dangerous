package com.hendraanggrian.rx.activity;

import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.JVM)
public class RequestCodeGeneratorTest {
/*
    private static final int ITERATION = 1000;

    @Test
    public void initialization() throws Exception {
        assertNull(RxActivity.INSTANCE.getRANDOM_REQUEST_CODE());
        RxActivity.INSTANCE.generateRequestCode();
        assertNotNull(RxActivity.INSTANCE.getRANDOM_REQUEST_CODE());
        assertNotNull(RxActivity.INSTANCE.getRANDOM_REQUEST_CODE().get());
    }

    @Test
    public void noDuplicate() throws Exception {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < ITERATION; i++) {
            int requestCode = RxActivity.INSTANCE.generateRequestCode();
            assertEquals(set.add(requestCode), true);
            RxActivity.INSTANCE.getQUEUES().append(requestCode, null);
        }
        assertEquals(set.size(), ITERATION);
    }
    */
}