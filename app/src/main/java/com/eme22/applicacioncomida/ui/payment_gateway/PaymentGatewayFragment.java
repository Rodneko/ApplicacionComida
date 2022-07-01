package com.eme22.applicacioncomida.ui.payment_gateway;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eme22.applicacioncomida.R;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentGatewayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentGatewayFragment extends Fragment {


    private PaymentGatewayFragment() {

    }

    public static PaymentGatewayFragment newInstance() {
        PaymentGatewayFragment fragment = new PaymentGatewayFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_gateway, container, false);
    }
}