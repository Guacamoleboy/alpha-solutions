package alpha;

import alpha.config.DotEnv;
import alpha.config.DotEnvLog;
import alpha.config.HibernateConfig;
import alpha.server.Server;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ATest {

    // Attributes
    protected EntityManagerFactory emf;
    protected EntityManager em;
    protected static Server restServer;
    protected static Javalin restApp;
    protected static final Logger LOGGER = LoggerFactory.getLogger(DotEnvLog.class);

    // _________________________________________________________________________________________________________________

    @BeforeAll
    protected void setupAll() {
        System.setProperty("set.env", "test");
        DotEnvLog.logEnvInfo();
        emf = HibernateConfig.getEntityManagerFactoryForTest();
    }

    // _________________________________________________________________________________________________________________

    @BeforeEach
    protected void setup() {
        em = emf.createEntityManager();
        em.clear();
    }

    // _________________________________________________________________________________________________________________

    @AfterEach
    protected void cleanup() {
        rollbackTransactionIfActive();
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    // _________________________________________________________________________________________________________________

    @AfterAll
    protected void closeAll() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    // _________________________________________________________________________________________________________________

    @AfterAll
    protected void stopServer() {
        if (restServer != null) {
            restServer.stop();
            restServer = null;
            restApp = null;
        }
    }

    // _________________________________________________________________________________________________________________

    protected void startServer() {
        if (restServer == null) {
            restServer = new Server();
            restServer.start();
            restApp = restServer.getApp();
            RestAssured.baseURI = DotEnv.getUrlPath();
            RestAssured.port = DotEnv.getServerPort();
            RestAssured.basePath = "/v1";
        }
    }

    // _________________________________________________________________________________________________________________

    protected void startServer(String endpoint) {
        if (restServer == null) {
            restServer = new Server();
            restServer.start();
            restApp = restServer.getApp();
            RestAssured.baseURI = DotEnv.getUrlPath();
            RestAssured.port = DotEnv.getServerPort();
            RestAssured.basePath = endpoint;
        }
    }

    // _________________________________________________________________________________________________________________

    protected void beginTransactionIfNeeded() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    // _________________________________________________________________________________________________________________

    protected void commitTransactionIfActive() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    // _________________________________________________________________________________________________________________

    protected void rollbackTransactionIfActive() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

}