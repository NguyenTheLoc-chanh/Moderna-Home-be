package com.modernadev.Moderna.Home.service.impl;

import com.modernadev.Moderna.Home.dto.AddressDto;
import com.modernadev.Moderna.Home.dto.Response;
import com.modernadev.Moderna.Home.entity.Address;
import com.modernadev.Moderna.Home.entity.User;
import com.modernadev.Moderna.Home.repository.AddressRepo;
import com.modernadev.Moderna.Home.service.interf.AddressService;
import com.modernadev.Moderna.Home.service.interf.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;
    private final UserService userService;


    @Override
    @Transactional
    public Response saveAndUpdateAddress(AddressDto addressDto) {
        User user = userService.getLoginUser();
        Address address = user.getAddress();

        boolean isNewAddress = (address == null);

        if (isNewAddress) {
            address = new Address();
            address.setUser(user);
            user.setAddress(address); // Gán lại để đảm bảo entity quản lý 2 chiều
        }
        if(addressDto.getStreet() != null) address.setStreet(addressDto.getStreet());
        if(addressDto.getCity() != null) address.setCity(addressDto.getCity());
        if(addressDto.getState() != null) address.setState(addressDto.getState());
        if(addressDto.getZipCode() != null) address.setZipCode(addressDto.getZipCode());
        if(addressDto.getCountry() != null) address.setCountry(addressDto.getCountry());

        addressRepo.save(address);

        return Response.builder()
                .status(200)
                .message(isNewAddress ? "Address successfully created" : "Address successfully updated")
                .build();
    }
}
