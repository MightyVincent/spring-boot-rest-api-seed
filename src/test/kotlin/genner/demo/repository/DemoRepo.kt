package genner.demo.repository

import genner.demo.model.Demo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author VincentLee
 */
@Repository
interface DemoRepo : JpaRepository<Demo, Long>
