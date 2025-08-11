package com.modernadev.Moderna.Home.repository;

import com.modernadev.Moderna.Home.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address,Long> {
}
