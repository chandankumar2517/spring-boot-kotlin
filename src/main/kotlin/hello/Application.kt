package hello

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

    private val log = LoggerFactory.getLogger(Application::class.java)

    @Bean
    fun init(repository: CustomerRepository) = CommandLineRunner {
        // Save a couple of customers
        saveCustomers(repository)

        // Fetch and log all customers
        logCustomers(repository.findAll(), "All Customers")

        // Fetch and log customer by ID
        logCustomerById(repository, 1L)

        // Fetch and log customers by last name
        logCustomers(repository.findByLastName("Bauer"), "Customers with Last Name 'Bauer'")
    }

    private fun saveCustomers(repository: CustomerRepository) {
        val customers = listOf(
            Customer("Jack", "Bauer"),
            Customer("Chloe", "O'Brian"),
            Customer("Kim", "Bauer"),
            Customer("David", "Palmer"),
            Customer("Michelle", "Dessler")
        )
        repository.saveAll(customers)
        log.info("Saved customers to the repository.")
    }

    private fun logCustomers(customers: Iterable<Customer>, message: String) {
        log.info("$message found:")
        log.info("-------------------------------")
        customers.forEach { log.info(it.toString()) }
        log.info("")
    }

    private fun logCustomerById(repository: CustomerRepository, id: Long) {
        val customer = repository.findById(id)
        log.info("Customer found with ID $id:")
        log.info("--------------------------------")
        if (customer.isPresent) {
            log.info(customer.get().toString())
        } else {
            log.info("No customer found with ID $id")
        }
        log.info("")
    }
}

fun main() {
    runApplication<Application>()
}
