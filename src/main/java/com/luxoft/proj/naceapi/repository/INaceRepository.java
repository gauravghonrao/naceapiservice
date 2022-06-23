package com.luxoft.proj.naceapi.repository;

import com.luxoft.proj.naceapi.entity.NaceEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface INaceRepository extends JpaRepository<NaceEntity, Integer>
{
}
