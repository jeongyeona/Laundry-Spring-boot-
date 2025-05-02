package com.example.Laundry.repository;

import com.example.Laundry.domain.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long> {

}
