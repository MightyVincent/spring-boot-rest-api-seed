package genner.demo.service

import genner.demo.model.Demo

/**
 * @author VincentLee
 */
interface DemoSrv {
    fun insert(demo: Demo): Demo?
    fun deleteById(id: Long): Int?
    fun update(demo: Demo): Int?
}