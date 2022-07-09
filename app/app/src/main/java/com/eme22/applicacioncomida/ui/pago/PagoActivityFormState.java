package com.eme22.applicacioncomida.ui.pago;

import androidx.annotation.Nullable;

class PagoActivityFormState {

    @Nullable
    private final Integer deliveryPaymentError;

    private final boolean isDataValid;

    public PagoActivityFormState(@Nullable Integer deliveryPaymentError) {
        this.deliveryPaymentError = deliveryPaymentError;
        this.isDataValid = false;
    }

    public PagoActivityFormState(boolean isDataValid) {
        this.deliveryPaymentError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getDeliveryPaymentError() {
        return deliveryPaymentError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
