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

    val alice = User(23, "alice@example.com", "alice")
    val bob = User(42, "bob@example.com", "bob")
    val cindy = User(1337, "cindy@example.com", "cindy")
    val aliceEmailPermission = EmailPermission(23, 42)
    val cindyEmailPermission = EmailPermission(1337, 23)

    @Test
    fun `When findBySenderId then return List of Receivers`() {
        if(userRepository.findAll().count() == 0 && emailPermissionRepository.findAll().count() == 0) {
            entityManager.persist(alice)
            entityManager.persist(bob)
            entityManager.persist(cindy)
            entityManager.persist(aliceEmailPermission)
            entityManager.persist(cindyEmailPermission)
            entityManager.flush()
        }

        val found = emailPermissionRepository.findAllBySenderId(alice.accountId)
        assertThat(found.first().receiverId).isEqualTo(bob.accountId)
    }

    @Test
    fun `When findByAccountId then return User`() {
        val found = userRepository.findByAccountId(alice.accountId)
        assertThat(found.name).isEqualTo(alice.name)
    }
}
//endregion

