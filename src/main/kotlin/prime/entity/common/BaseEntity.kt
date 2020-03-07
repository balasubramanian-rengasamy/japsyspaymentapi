package prime.entity.common

import java.io.Serializable
import javax.persistence.Cacheable
import javax.persistence.MappedSuperclass

@MappedSuperclass
@Cacheable(false)
open class BaseEntity : Serializable {
}
