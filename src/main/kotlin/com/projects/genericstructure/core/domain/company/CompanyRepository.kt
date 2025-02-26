package com.projects.genericstructure.core.domain.company

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CompanyRepository : CrudRepository<Company, UUID>
