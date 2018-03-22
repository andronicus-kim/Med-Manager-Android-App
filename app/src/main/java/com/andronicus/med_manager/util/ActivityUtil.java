package com.andronicus.med_manager.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by andronicus on 3/23/2018.
 */

public class ActivityUtil {

    public static void addFragmentToActivity(@NonNull FragmentManager manager,
                                             @NonNull int fragmentId,
                                             @NonNull Fragment fragment){
        manager.beginTransaction()
                .replace(fragmentId,fragment)
                .commit();
    }
}
