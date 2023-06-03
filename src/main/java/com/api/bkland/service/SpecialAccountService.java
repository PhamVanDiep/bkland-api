package com.api.bkland.service;

import com.api.bkland.constant.PayContent;
import com.api.bkland.constant.enumeric.ERole;
import com.api.bkland.entity.*;
import com.api.bkland.entity.response.IDistrict;
import com.api.bkland.payload.dto.DistrictDTO;
import com.api.bkland.payload.dto.SpecialAccountDTO;
import com.api.bkland.payload.request.AgencyRegisterRequest;
import com.api.bkland.payload.response.AgencyInfoResponse;
import com.api.bkland.payload.response.BaseResponse;
import com.api.bkland.repository.SpecialAccountPayRepository;
import com.api.bkland.repository.SpecialAccountRepository;
import com.api.bkland.repository.UserRepository;
import com.api.bkland.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpecialAccountService {
    @Autowired
    private SpecialAccountRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpecialAccountPayRepository specialAccountPayRepository;

    @Autowired
    private ModelMapper modelMapper;

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
            specialAccountPay.setContent(PayContent.MONTHLY_CHARGE);
            specialAccountPay.setMonthlyPay(true);
            specialAccountPay.setCreateBy(userId);
            specialAccountPay.setCreateAt(Instant.now());
            specialAccountPayRepository.save(specialAccountPay);

            for (DistrictDTO districtDTO: districtDTOS) {
                userRepository.agencyDistrictInsert(userId, districtDTO.getCode());
            }

            user.setAccountBalance(user.getAccountBalance() - totalPaid);
            user.setUpdateAt(Instant.now());
            user.setUpdateBy(user.getId());
            userRepository.save(user);

            return new BaseResponse(null, "Đăng ký tài khoản môi giới thành công.", HttpStatus.OK);
        } catch (Exception e) {
            return new BaseResponse(null,
                    "Đã xảy ra lỗi khi đăng ký tài khoản môi giới. " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

            Optional<SpecialAccount> specialAccountOptional = repository.findByUserId(request.getUserId());
            if (specialAccountOptional.isEmpty()) {
                return new BaseResponse(null, "Không tìm thấy thông tin người dùng này.", HttpStatus.NO_CONTENT);
            }
            SpecialAccount specialAccount = specialAccountOptional.get();
            if (totalPaid - specialAccount.getMonthlyCharge() > user.getAccountBalance()) {
                return new BaseResponse(null, "Tài khoản của người dùng không đủ để đăng ký.", HttpStatus.NO_CONTENT);
            }

            if (totalPaid > specialAccount.getMonthlyCharge()) {
                int delta = totalPaid - specialAccount.getMonthlyCharge();
                SpecialAccountPay specialAccountPay = new SpecialAccountPay();
                specialAccountPay.setUser(user);
                specialAccountPay.setId(0L);
                specialAccountPay.setAmount(delta);
                specialAccountPay.setAccountBalance(user.getAccountBalance() - delta);
                specialAccountPay.setContent(PayContent.EXTRA_CHARGE);
                specialAccountPay.setMonthlyPay(false);
                specialAccountPay.setCreateBy(userId);
                specialAccountPay.setCreateAt(Instant.now());
                specialAccountPayRepository.save(specialAccountPay);

                user.setAccountBalance(user.getAccountBalance() - delta);
                user.setUpdateAt(Instant.now());
                user.setUpdateBy(user.getId());
                userRepository.save(user);
            }

            specialAccount.setMonthlyCharge(totalPaid);
            repository.save(specialAccount);

            repository.agencyDistrictDeleteByUserId(userId);
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

    @Transactional
    public void deleteByUserId(String userId) throws Exception {
        repository.deleteByUserId(userId);
    }

    @Transactional
    public void agencyDistrictDeleteByUserId(String userId) throws Exception {
        repository.agencyDistrictDeleteByUserId(userId);
    }

    @Transactional
    public void userRoleDeleteByUserId(String userId) throws Exception {
        repository.userRoleDeleteByUserId(userId);
    }

    public AgencyInfoResponse findAgencyInfo(String userId) {
        Optional<SpecialAccount> specialAccountOptional = repository.findByUserId(userId);
        if (specialAccountOptional.isEmpty()) {
            return null;
        }
        List<IDistrict> districts = repository.findAllDistrictsAgency(userId);
        if (districts.isEmpty()) {
            return null;
        }
        List<DistrictDTO> districtDTOS = new ArrayList<>();
        for (IDistrict iDistrict: districts) {
            DistrictDTO districtDTO = new DistrictDTO();
            districtDTO.setCode(iDistrict.getCode());
            districtDTO.setCodeName(iDistrict.getCode_name());
            districtDTO.setName(iDistrict.getName());
            districtDTO.setFullName(iDistrict.getFull_name());
            districtDTO.setFullNameEn(iDistrict.getFull_name_en());
            districtDTO.setProvinceCode(iDistrict.getProvince_code());
            districtDTO.setAdministrativeUnitId(iDistrict.getAdministrative_unit_id());
            districtDTO.setNameEn(iDistrict.getName_en());
            districtDTOS.add(districtDTO);
        }
        AgencyInfoResponse agencyInfoResponse = new AgencyInfoResponse();
        agencyInfoResponse.setSpecialAccount(modelMapper.map(specialAccountOptional.get(), SpecialAccountDTO.class));
        agencyInfoResponse.setDistricts(districtDTOS);
        return agencyInfoResponse;
    }

    public boolean isAgency(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return false;
        }
        Role roleAgency = new Role();
        roleAgency.setId(3);
        roleAgency.setName(ERole.ROLE_AGENCY);
        if (userOptional.get().getRoles().contains(roleAgency)) {
            return true;
        }
        return false;
    }

    public List<String> getAllDistrictCodeOfAgency(String userId) {
        return repository.getAllDistrictCodeOfAgency(userId);
    }

    public SpecialAccount findById(String userId) {
        Optional<SpecialAccount> specialAccount = repository.findByUserId(userId);
        if (specialAccount.isEmpty()) {
            return null;
        }
        return specialAccount.get();
    }

    @Transactional
    public void update(SpecialAccount specialAccount) {
        repository.save(specialAccount);
    }
}
