package hello

import org.springframework.data.repository.CrudRepository
import java.util.*

interface CustomerRepository : CrudRepository<Customer, Long> {

	fun findByLastName(lastName: String): List<Customer>

	fun findCustomerById(id: Long): Optional<Customer>
}
