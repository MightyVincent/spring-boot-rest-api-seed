package genner.demo.service

import genner.demo.model.Demo
import genner.demo.repository.DemoRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * @author VincentLee
 */
@Service
@Transactional
class DemoSrvImpl : DemoSrv {

    @Autowired
    private lateinit var demoRepo: DemoRepo

    override fun insert(demo: Demo): Demo? {
        return demoRepo.save(demo)
    }

    override fun deleteById(id: Long) : Int?{
        demoRepo.deleteById(id)
        return 1
    }

    override fun update(demo: Demo): Int? {
        demoRepo.save(demo)
        return 1
    }
}