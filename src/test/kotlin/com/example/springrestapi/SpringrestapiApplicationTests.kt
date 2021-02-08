package com.example.springrestapi

import com.example.springrestapi.interfaces.EmailPermissionRepository
import com.example.springrestapi.interfaces.UserRepository
import com.example.springrestapi.data.EmailPermission
import com.example.springrestapi.data.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

//region DataJPATest
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class RepositoriesTests @Autowired constructor(
    val entityManager: TestEntityManager,
    val userRepository: UserRepository,
    val emailPermissionRepository: EmailPermissionRepository) {

    private final val alice = User("alice@example.com", "alice")
    private final val bob = User("bob@example.com", "bob")
    private final val cindy = User("cindy@example.com", "cindy")
    val aliceBobEmailPermission = EmailPermission(alice, bob)
    val cindyAliceEmailPermission = EmailPermission(cindy, alice)
    val cindyBobEmailPermission = EmailPermission(cindy, bob)

    @Test
    fun `When findBySenderId then return List of Receivers`() {
        if(userRepository.findAll().count() == 0 && emailPermissionRepository.findAll().count() == 0) {
            entityManager.persist(alice)
            entityManager.persist(bob)
            entityManager.persist(cindy)
            entityManager.persist(aliceBobEmailPermission)
            entityManager.persist(cindyAliceEmailPermission)
            entityManager.persist(cindyBobEmailPermission)
            entityManager.flush()
        }

        val users = userRepository.findAll().toMutableList()
        val found = emailPermissionRepository.findAllBySenderId(1)

        assertThat(found.first().receiver.id).isEqualTo(users[1].id)
    }

    @Test
    fun `When findByAccountId then return User`() {
        val found = userRepository.findById(1).get()
        assertThat(found.name).isEqualTo(alice.name)
    }
}
//endregion

