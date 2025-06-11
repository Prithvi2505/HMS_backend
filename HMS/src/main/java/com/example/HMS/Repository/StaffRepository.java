package com.example.HMS.Repository;

import com.example.HMS.Entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff,Integer> {
}
