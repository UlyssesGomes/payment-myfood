package com.myfood.payment.service;

import com.myfood.payment.dto.PaymentDTO;
import com.myfood.payment.model.Payment;
import com.myfood.payment.model.StatusPaymentEnum;
import com.myfood.payment.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;

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
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        try {
            BeanUtils.copyProperties(paymentDto, payment, getNullPropertyNames(paymentDto));
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }

        return modelMapper.map(repository.save(payment), PaymentDTO.class);
    }

    private String[] getNullPropertyNames(Object source) throws IntrospectionException {
        return Arrays.stream(Introspector.getBeanInfo(source.getClass(), Object.class)
                        .getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(name -> {
                    try {
                        return source.getClass().getMethod("get" + StringUtils.capitalize(name)).invoke(source) == null;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .toArray(String[]::new);
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
