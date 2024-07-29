package hello

import hello.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/customers")
class CustomerController(private val repository: CustomerRepository) {

	@GetMapping
	fun findAll(): ResponseEntity<ApiResponse<List<Customer>>> {
		// Ensure return List
		val customers = repository.findAll().toList()
		return if (customers.isNotEmpty()) {
			ResponseEntity.ok(ApiResponse(status = "success", data = customers))
		} else {
			ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiResponse(status = "error", message = "No customers found"))
		}
	}

	@GetMapping("/lastname/{lastName}")
	fun findByLastName(@PathVariable lastName: String): ResponseEntity<ApiResponse<List<Customer>>> {
		val customers = repository.findByLastName(lastName)
		return if (customers.isNotEmpty()) {
			ResponseEntity.ok(ApiResponse(status = "success", data = customers))
		} else {
			ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiResponse(status = "error", message = "No customers found with last name: $lastName"))
		}
	}

	@GetMapping("/id/{id}")
	fun findCustomerById(@PathVariable id: Long): ResponseEntity<ApiResponse<Customer>> {
		val customer = repository.findById(id)
		return if (customer.isPresent) {
			ResponseEntity.ok(ApiResponse(status = "success", data = customer.get()))
		} else {
			ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiResponse(status = "error", message = "Customer not found with ID: $id"))
		}
	}
}
