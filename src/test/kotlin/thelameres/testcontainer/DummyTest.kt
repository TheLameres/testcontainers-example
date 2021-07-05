package thelameres.testcontainer

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.sql.DriverManager
import kotlin.test.assertEquals

@Testcontainers
class DummyTest {

    companion object {
        @Container
        val postgres = PostgreSQLContainerKt(DockerImageName.parse("postgres")).apply {
            withDatabaseName("test")
            withUsername("postgres")
            withPassword("postgres")
            withExposedPorts(5432)
        }
    }

    @BeforeEach
    fun setUp() {
        Class.forName(postgres.driverClassName)
    }

    @Test
    fun `🤔`() {
        val connection = DriverManager.getConnection(postgres.jdbcUrl, postgres.username, postgres.password)
        val createStatement = connection.createStatement()
        createStatement.execute(
            """
            create table users(id integer not null,
                                username varchar(20) not null,
                                password varchar(20) not null
            );
            insert into users(id, username, password) values (1, 'test', 'test');
        """.trimIndent()
        )

        val execute = createStatement.executeQuery("select * from users where id = 1;")
        while (execute.next()) {
            assertEquals("test", execute.getString("username"))
            assertEquals("test", execute.getString("password"))
        }
    }

}

class PostgreSQLContainerKt(image:  DockerImageName) : PostgreSQLContainer<PostgreSQLContainerKt>(image)