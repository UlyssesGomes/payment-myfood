package com.myfood.payment.controller;

import com.myfood.payment.dto.PaymentDTO;
import com.myfood.payment.dto.PaymentWithDetailsDTO;
import com.myfood.payment.service.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("payments")
public class PaymentController {
    @Autowired
    private PaymentService service;

    @GetMapping
    public Page<PaymentDTO> getPayments(@PageableDefault(size = 10) Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable @NotNull Long id) {
        PaymentDTO dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}/details")
    @CircuitBreaker(name = "orderService", fallbackMethod = "")
    public ResponseEntity<PaymentWithDetailsDTO> getPaymentByIdWithDetails(@PathVariable @NotNull Long id) {
        PaymentWithDetailsDTO dto = service.getByIdWithDetails(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody @Valid PaymentDTO dto, UriComponentsBuilder uriBuilder) {
        PaymentDTO paymentDTO = service.create(dto);
        URI address = uriBuilder.path("payments/{id}").buildAndExpand(paymentDTO.getId()).toUri();
        return ResponseEntity.created(address).body(paymentDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable @NotNull Long id, @RequestBody @Valid PaymentDTO dto) {
        PaymentDTO paymentDTO = service.update(id, dto);
        return ResponseEntity.ok(paymentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePaymentById(@PathVariable @NotNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirm")
    @CircuitBreaker(name = "orderService", fallbackMethod = "paymentAuthorizedWithIntegrationPending")
    public void confirmPayment(@PathVariable @NotNull Long id) {
        service.confirmPayment(id);
    }

    public void paymentAuthorizedWithIntegrationPending(Long id, Exception e) {
        service.changeStatus(id);
    }
}
