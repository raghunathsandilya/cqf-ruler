package org.opencds.cqf.ruler.test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.opencds.cqf.ruler.Application;
import org.opencds.cqf.ruler.behavior.IdCreator;
import org.opencds.cqf.ruler.behavior.ResourceCreator;
import org.opencds.cqf.ruler.external.AppProperties;
import org.opencds.cqf.ruler.test.behavior.ResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.jpa.api.dao.DaoRegistry;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.api.ServerValidationModeEnum;

@Import(Application.class)
@TestPropertySource(properties = {
		"scheduling_disabled=true",
		"spring.main.allow-bean-definition-overriding=true",
		"spring.batch.job.enabled=false",
		"hapi.fhir.allow_external_references=true",
		"hapi.fhir.enforce_referential_integrity_on_write=false",
		"hapi.fhir.auto_create_placeholder_reference_targets=true",
		"hapi.fhir.client_id_strategy=ANY",
		"spring.datasource.url=jdbc:h2:mem:db",
		"spring.main.lazy-initialization=true" })
@TestInstance(Lifecycle.PER_CLASS)
public class RestIntegrationTest implements ResourceLoader, ResourceCreator, IdCreator {

	@Autowired
	AppProperties myAppProperties;

	@Autowired
	TestDbService myDbService;

	@Autowired
	private FhirContext myCtx;

	@Autowired
	DaoRegistry myDaoRegistry;

	@LocalServerPort
	private int myPort;

	private IGenericClient myClient;

	private String myServerBase;

	@Override
	public FhirContext getFhirContext() {
		return myCtx;
	}

	@Override
	public DaoRegistry getDaoRegistry() {
		return myDaoRegistry;
	}

	protected String getServerBase() {
		return myServerBase;
	}

	protected IGenericClient getClient() {
		return myClient;
	}

	protected int getPort() {
		return myPort;
	}

	protected AppProperties getAppProperties() {
		return myAppProperties;
	}

	protected void stubResourceForUrl(String location, String url) throws IOException {
		String resourceString = stringFromResource(location);
		stubFor(
				get(urlEqualTo(url))
						.willReturn(
								aResponse()
										.withStatus(200)
										.withBody(resourceString)
										.withHeader("Content-Type", "application/fhir+json; charset=utf-8")));
	}

	@BeforeEach
	void baseBeforeEach() {
		myCtx.getRestfulClientFactory().setServerValidationMode(ServerValidationModeEnum.NEVER);
		myCtx.getRestfulClientFactory().setSocketTimeout(1200 * 1000);
		myServerBase = "http://localhost:" + myPort + "/fhir/";
		myAppProperties.setServer_address(myServerBase);
		myClient = myCtx.newRestfulGenericClient(myServerBase);
	}

	@AfterAll
	void baseAfterAll() {
		myDbService.resetDatabase();
	}
}
