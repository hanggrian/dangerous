package com.hendraanggrian.rx.activity;

import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.JVM)
public class RequestCodeGeneratorTest {

    private static final int ITERATION = 1000;

    @Test
    public void initialization() throws Exception {
        assertNull(RxActivity.RANDOM_REQUEST_CODE);
        RxActivity.generateRequestCode();
        assertNotNull(RxActivity.RANDOM_REQUEST_CODE);
        assertNotNull(RxActivity.RANDOM_REQUEST_CODE.get());
    }

    @Test
    public void noDuplicate() throws Exception {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < ITERATION; i++) {
            int requestCode = RxActivity.generateRequestCode();
            assertEquals(set.add(requestCode), true);
            RxActivity.QUEUES.append(requestCode, null);
        }
        assertEquals(set.size(), ITERATION);
    }
}