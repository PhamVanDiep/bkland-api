package com.api.bkland.service;

import com.api.bkland.entity.response.IPaymentStatistic;
import com.api.bkland.payload.response.PaymentStatisticResponse;
import com.api.bkland.repository.PostPayRepository;
import com.api.bkland.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PostPayRepository postPayRepository;

    public PaymentStatisticResponse getPaymentStatistic(Integer nam) {
        int currMonth = Util.getCurrMonth(nam);
        PaymentStatisticResponse response = new PaymentStatisticResponse();
        for (int i = 1; i <= currMonth; i++) {
            response.getMonth().add(i);
            Optional<IPaymentStatistic> iPaymentStatistic = postPayRepository.getPaymentStatistic(i, nam);
            if (!iPaymentStatistic.isEmpty()) {
                response.getPostPrice().add(iPaymentStatistic.get().getPostPrice() != null ? iPaymentStatistic.get().getPostPrice() : 0L);
                response.getSpecialAccountPay().add(iPaymentStatistic.get().getSpecialAccountPay() != null ? iPaymentStatistic.get().getSpecialAccountPay() : 0L);
            }
        }
        return response;
    }

    public PaymentStatisticResponse getPaymentStatisticMonth(Integer year, Integer month) {
        int date = Util.getDayOfMonth(month, year);
        PaymentStatisticResponse response = new PaymentStatisticResponse();
        for (int i = 1; i <= date; i++) {
            response.getNgay().add(i);
            response.getMonth().add(month);
            String dateReq = year + "-" + month + "-" + i;
            Optional<IPaymentStatistic> iPaymentStatistic = postPayRepository.getPaymentStatisticMonth(dateReq);
            if (!iPaymentStatistic.isEmpty()) {
                response.getPostPrice().add(iPaymentStatistic.get().getPostPrice() != null ? iPaymentStatistic.get().getPostPrice() : 0L);
                response.getSpecialAccountPay().add(iPaymentStatistic.get().getSpecialAccountPay() != null ? iPaymentStatistic.get().getSpecialAccountPay() : 0L);
            }
        }
        return response;
    }

    public PaymentStatisticResponse getChargeInYear(Integer nam) {
        int currMonth = Util.getCurrMonth(nam);
        PaymentStatisticResponse response = new PaymentStatisticResponse();
        for (int i = 1; i <= currMonth; i++) {
            response.getMonth().add(i);
            Long iPaymentStatistic = postPayRepository.getChargeInYear(i, nam);
            response.getPostPrice().add(iPaymentStatistic);
        }
        return response;
    }

    public PaymentStatisticResponse getChargeByMonth(Integer year, Integer month) {
        int date = Util.getDayOfMonth(month, year);
        PaymentStatisticResponse response = new PaymentStatisticResponse();
        for (int i = 1; i <= date; i++) {
            response.getNgay().add(i);
            response.getMonth().add(month);
            String dateReq = year + "-" + month + "-" + i;
            Long iPaymentStatistic = postPayRepository.getChargeInMonth(dateReq);
            response.getPostPrice().add(iPaymentStatistic);
        }
        return response;
    }
}
