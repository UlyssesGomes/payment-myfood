package com.myfood.payment.repository;

import com.myfood.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p WHERE status = 'CONFIRMED_WITHOUT_INTEGRATION'")
    List<Payment> findAllPendingIntegration();
}
