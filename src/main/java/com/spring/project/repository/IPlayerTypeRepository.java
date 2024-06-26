package com.spring.project.repository;

import com.spring.project.model.PlayerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlayerTypeRepository extends JpaRepository<PlayerType,Integer> {
}
