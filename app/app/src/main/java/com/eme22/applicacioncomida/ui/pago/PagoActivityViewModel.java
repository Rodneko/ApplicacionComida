package com.eme22.applicacioncomida.ui.pago;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eme22.applicacioncomida.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PagoActivityViewModel extends ViewModel {

    private final MutableLiveData<PagoActivityFormState> payFormState = new MutableLiveData<>();

    LiveData<PagoActivityFormState> getPayFormState() {
        return payFormState;
    }

    private final MutableLiveData<Boolean> payDeliveryAccepted = new MutableLiveData<>(false);

    LiveData<Boolean> getPayDeliveryAccepted() {
        return payDeliveryAccepted;
    }


    private boolean isNumberValid(String number ) {

        try {
            Double.parseDouble(number);
        } catch (NumberFormatException ignore) {
            return false;
        }

        final String regExp = "[0-9]+([,.][0-9]{1,2})?";
        final Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();

    }


    public void setPayDelivery(boolean accepted) {
        payDeliveryAccepted.setValue(accepted);
    }

    public void onPayDeliveryChanged(String text) {
        if (!isNumberValid(text)) {
            payFormState.setValue(new PagoActivityFormState(R.string.invalid_number));
        }
        else {
            payFormState.setValue(new PagoActivityFormState(true));
        }
    }
}
