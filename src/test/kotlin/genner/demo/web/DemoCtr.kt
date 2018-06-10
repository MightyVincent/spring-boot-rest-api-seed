package genner.demo.web

import com.company.project.core.RR
import genner.demo.model.Demo
import genner.demo.repository.DemoRepo
import genner.demo.service.DemoSrv
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


/**
 * @author VincentLee
 */
@RestController
@RequestMapping("/demos")
class DemoCtr {
    @Autowired
    private lateinit var demoRepo: DemoRepo
    @Autowired
    private lateinit var demoSrv: DemoSrv

    @PostMapping
    fun add(@RequestBody demo: Demo): RR<Demo?> {
        return RR.ok(demoSrv.insert(demo))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): RR<Int?> {
        return RR.ok(demoSrv.deleteById(id))
    }

    @PutMapping
    fun update(@RequestBody demo: Demo): RR<Demo?> {
        return RR.ok(demoRepo.save(demo))
    }

    @GetMapping("/{id}")
    fun detail(@PathVariable id: Long): RR<Demo?> {
        return RR.ok(demoRepo.findById(id).orElse(null))
    }

    @GetMapping
    fun list(): RR<List<Demo>> {
        return RR.ok(demoRepo.findAll())
    }
}