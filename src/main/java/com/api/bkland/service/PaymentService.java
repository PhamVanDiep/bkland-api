package com.api.bkland.service;

import com.api.bkland.entity.response.IPaymentStatistic;
import com.api.bkland.payload.response.PaymentStatisticResponse;
import com.api.bkland.repository.PostPayRepository;
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currMonth;
        if (calendar.get(Calendar.YEAR) == nam) {
            currMonth = calendar.get(Calendar.MONTH);
            currMonth++;
        } else {
            currMonth = 12;
        }
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currMonth = calendar.get(Calendar.MONTH);
        currMonth++;
        int currYear = calendar.get(Calendar.YEAR);
        int date;
        if (year == currYear && month == currMonth) {
            date = calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                date = 31;
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                date = 30;
            } else {
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    date = 29;
                } else {
                    date = 28;
                }
            }
        }
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currMonth;
        if (calendar.get(Calendar.YEAR) == nam) {
            currMonth = calendar.get(Calendar.MONTH);
            currMonth++;
        } else {
            currMonth = 12;
        }
        PaymentStatisticResponse response = new PaymentStatisticResponse();
        for (int i = 1; i <= currMonth; i++) {
            response.getMonth().add(i);
            Long iPaymentStatistic = postPayRepository.getChargeInYear(i, nam);
            response.getPostPrice().add(iPaymentStatistic);
        }
        return response;
    }

    public PaymentStatisticResponse getChargeByMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currMonth = calendar.get(Calendar.MONTH);
        currMonth++;
        int currYear = calendar.get(Calendar.YEAR);
        int date;
        if (year == currYear && month == currMonth) {
            date = calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                date = 31;
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                date = 30;
            } else {
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    date = 29;
                } else {
                    date = 28;
                }
            }
        }
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
