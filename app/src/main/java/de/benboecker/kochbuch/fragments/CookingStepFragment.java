package de.benboecker.kochbuch.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.benboecker.kochbuch.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CookingStepFragment extends Fragment {


	public CookingStepFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_cooking_step, container, false);
	}

}
