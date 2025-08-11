package com.modernadev.Moderna.Home.service.interf;

import com.modernadev.Moderna.Home.dto.AddressDto;
import com.modernadev.Moderna.Home.dto.Response;

public interface AddressService {
    Response saveAndUpdateAddress(AddressDto addressDto);
}
