package com.example.consumer.repository;

import com.example.consumer.entity.StringValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StringValueRepository extends JpaRepository<StringValueEntity, Long> {

}
