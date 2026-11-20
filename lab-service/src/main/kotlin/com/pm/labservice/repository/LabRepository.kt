package com.pm.labservice.repository

import com.pm.labservice.model.LabTest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface LabRepository : JpaRepository<LabTest, UUID> {

	fun existsByRequestId(requestId: String): Boolean;
}
