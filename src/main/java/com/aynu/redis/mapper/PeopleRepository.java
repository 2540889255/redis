package com.aynu.redis.mapper;

import com.aynu.redis.pojo.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<People, Integer> {
}
