package com.myfood.payment.service;

import com.myfood.payment.dto.PaymentDTO;
import com.myfood.payment.model.Payment;
import com.myfood.payment.model.StatusPaymentEnum;
import com.myfood.payment.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PaymentDTO> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(p -> modelMapper.map(p, PaymentDTO.class));
    }

    public PaymentDTO getById(Long id) {
        Payment p = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        return modelMapper.map(p, PaymentDTO.class);
    }

    public PaymentDTO update(Long id, PaymentDTO paymentDto) {
        Payment p = modelMapper.map(paymentDto, Payment.class);
        p.setId(id);
        p = repository.save(p);
        return modelMapper.map(p, PaymentDTO.class);
    }

    public PaymentDTO create(PaymentDTO paymentDto) {
        Payment p = modelMapper.map(paymentDto, Payment.class);
        p.setStatus(StatusPaymentEnum.CREATED);
        repository.save(p);

        return modelMapper.map(p, PaymentDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
