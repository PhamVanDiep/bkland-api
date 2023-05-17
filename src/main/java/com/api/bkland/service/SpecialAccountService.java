package com.api.bkland.service;

import com.api.bkland.entity.SpecialAccount;
import com.api.bkland.entity.SpecialAccountPay;
import com.api.bkland.entity.User;
import com.api.bkland.payload.dto.DistrictDTO;
import com.api.bkland.payload.request.AgencyRegisterRequest;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.repository.SpecialAccountPayRepository;
import com.api.bkland.repository.SpecialAccountRepository;
import com.api.bkland.repository.UserRepository;
import com.api.bkland.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialAccountService {
    @Autowired
    private SpecialAccountRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpecialAccountPayRepository specialAccountPayRepository;

    @Transactional
    public SpecialAccount addSpecialAccount(SpecialAccount specialAccount) {
        return repository.save(specialAccount);
    }

    @Transactional
    public BaseResponse agencyRegister(AgencyRegisterRequest request) {
        try {
            String userId = request.getUserId();

            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                return new BaseResponse(null, "Không tìm thấy thông tin người dùng này.", HttpStatus.NO_CONTENT);
            }
            User user = userOptional.get();
            List<DistrictDTO> districtDTOS = request.getDistricts();

            int totalPaid = Util.agencyMonthlyPaid(districtDTOS);
            if (totalPaid > user.getAccountBalance()) {
                return new BaseResponse(null, "Tài khoản của người dùng không đủ để đăng ký.", HttpStatus.NO_CONTENT);
            }
            userRepository.agencyRegister(request.getUserId());

            SpecialAccount specialAccount = new SpecialAccount();
            specialAccount.setUserId(request.getUserId());
            specialAccount.setAgency(true);
            specialAccount.setLastPaid(Instant.now());
            specialAccount.setMonthlyCharge(totalPaid);
            repository.save(specialAccount);

            SpecialAccountPay specialAccountPay = new SpecialAccountPay();
            specialAccountPay.setUser(user);
            specialAccountPay.setId(0L);
            specialAccountPay.setAmount(totalPaid);
            specialAccountPay.setAccountBalance(user.getAccountBalance() - totalPaid);
            specialAccountPay.setCreateBy(userId);
            specialAccountPay.setCreateAt(Instant.now());
            specialAccountPayRepository.save(specialAccountPay);

            for (DistrictDTO districtDTO: districtDTOS) {
                userRepository.agencyDistrictInsert(userId, districtDTO.getCode());
            }
            return new BaseResponse(null, "Đăng ký tài khoản môi giới thành công.", HttpStatus.OK);
        } catch (Exception e) {
            return new BaseResponse(null,
                    "Đã xảy ra lỗi khi đăng ký tài khoản môi giới. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Cần lên kịch bản cập nhật rõ ràng.
    @Transactional
    public BaseResponse agencyUpdate(AgencyRegisterRequest request) {
        try {
            String userId = request.getUserId();

            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                return new BaseResponse(null, "Không tìm thấy thông tin người dùng này.", HttpStatus.NO_CONTENT);
            }
            User user = userOptional.get();
            List<DistrictDTO> districtDTOS = request.getDistricts();

            int totalPaid = Util.agencyMonthlyPaid(districtDTOS);
            if (totalPaid > user.getAccountBalance()) {
                return new BaseResponse(null, "Tài khoản của người dùng không đủ để đăng ký.", HttpStatus.NO_CONTENT);
            }
            userRepository.agencyRegister(request.getUserId());

            SpecialAccount specialAccount = new SpecialAccount();
            specialAccount.setUserId(request.getUserId());
            specialAccount.setAgency(true);
            specialAccount.setLastPaid(Instant.now());
            specialAccount.setMonthlyCharge(totalPaid);
            repository.save(specialAccount);

            SpecialAccountPay specialAccountPay = new SpecialAccountPay();
            specialAccountPay.setUser(user);
            specialAccountPay.setId(0L);
            specialAccountPay.setAmount(totalPaid);
            specialAccountPay.setAccountBalance(user.getAccountBalance() - totalPaid);
            specialAccountPay.setCreateBy(userId);
            specialAccountPay.setCreateAt(Instant.now());
            specialAccountPayRepository.save(specialAccountPay);

            for (DistrictDTO districtDTO: districtDTOS) {
                userRepository.agencyDistrictInsert(userId, districtDTO.getCode());
            }
            return new BaseResponse(null, "Cập nhật thông tin đăng ký tài khoản môi giới thành công.", HttpStatus.OK);
        } catch (Exception e) {
            return new BaseResponse(null,
                    "Đã xảy ra lỗi khi cập nhật thông tin đăng ký tài khoản môi giới. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
