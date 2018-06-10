package genner.demo.model

import java.time.LocalDateTime
import javax.persistence.*

/**
 * @author VincentLee
 */
@Entity
data class Demo(
        /**
         * 主键
         */
        @Id
        @Column(columnDefinition = "bigint unsigned")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        /**
         * 创建时间
         */
        @Column(columnDefinition = "datetime")
        var gmtCreate: LocalDateTime? = null,

        /**
         * 更新时间
         */
        @Column(columnDefinition = "datetime")
        var gmtModified: LocalDateTime? = null

)
