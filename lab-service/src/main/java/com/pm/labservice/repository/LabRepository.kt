package com.pm.labservice.repository;

import com.pm.labservice.model.LabTest;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabRepository extends JpaRepository<LabTest, UUID> {

	boolean existsByRequestId(String requestId);
}
