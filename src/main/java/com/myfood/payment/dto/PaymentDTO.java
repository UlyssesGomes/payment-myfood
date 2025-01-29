package com.myfood.payment.dto;

import com.myfood.payment.model.StatusPaymentEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentDTO {
    private Long id;
    private BigDecimal paymentValue;
    private String name;
    private String number;
    private String expirationDate;
    private String code;
    private StatusPaymentEnum status;
    private Long orderId;
    private Long paymentMethodId;
}
