package com.juaracoding.iot.model;

import androidx.fragment.app.Fragment;

public interface FragmentBackStack {
    void loadFragment(Fragment fragment, String fragmentName);
}
