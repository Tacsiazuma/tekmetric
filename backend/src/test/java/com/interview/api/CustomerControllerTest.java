package com.interview.api;

import com.interview.application.CustomerRepository;
import com.interview.application.VehicleRepository;
import com.interview.application.WorkOrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    private static final String REQUEST_STUB_BASE = "api/customer/request/stub/";
    private static final String RESPONSE_EXPECTED_BASE = "api/customer/response/expected/";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    WorkOrderRepository workOrderRepository;

    @AfterEach
    void tearDown() {
        workOrderRepository.findAll(Optional.empty()).forEach(wo -> workOrderRepository.deleteById(wo.getId()));
        vehicleRepository.findAll().forEach(v -> vehicleRepository.deleteById(v.getId()));
        customerRepository.findAll().forEach(c -> customerRepository.deleteById(c.getId()));
    }

    private String loadJson(String path) {
        try (var in = getClass().getClassLoader().getResourceAsStream(path)) {
            if (in == null) {
                throw new IllegalArgumentException("Resource not found: " + path);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON: " + path, e);
        }
    }

    private static String extractIdFromResponse(String responseBody) {
        return responseBody.replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");
    }

    private String loadRequest(String name) {
        return loadJson(REQUEST_STUB_BASE + name);
    }

    private String loadExpected(String name) {
        return loadJson(RESPONSE_EXPECTED_BASE + name);
    }

    private ResultActions post(String uri, String body) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(body));
    }

    private ResultActions get(String uri) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(uri));
    }

    private ResultActions put(String uri, String body) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON).content(body));
    }

    private ResultActions delete(String uri) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(uri));
    }

    private String createCustomerAndGetId() throws Exception {
        String body = loadRequest("create-valid.json");
        ResultActions result = post("/customers", body);
        return assertCreatedAndGetId(result);
    }

    private String assertCreatedAndGetId(ResultActions result) throws Exception {
        String responseBody = result.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        return extractIdFromResponse(responseBody);
    }

    private void assertResponse(ResultActions result, ResultMatcher statusMatcher, String expectedJson) throws Exception {
        result.andExpect(statusMatcher);
        if (expectedJson != null) {
            result.andExpect(content().json(expectedJson, false));
        }
    }

    @Test
    @DisplayName("Create customer should return 201 and body with id, name, email when request is valid")
    void create_valid_201() throws Exception {
        // GIVEN
        String body = loadRequest("create-valid.json");
        // WHEN
        ResultActions result = post("/customers", body);
        // THEN
        String id = assertCreatedAndGetId(result);
        String expected = loadExpected("create-201.json").replace("__ID__", id);
        // WHEN
        ResultActions getResult = get("/customers/" + id);
        // THEN
        assertResponse(getResult, status().isOk(), expected);
    }

    @Test
    @DisplayName("Create customer should return 400 and validation errors when name is blank")
    void create_blank_name_400() throws Exception {
        // GIVEN invalid request (blank name)
        String body = loadRequest("create-blank-name.json");
        String expected = loadExpected("error-400-validation-name.json");
        // WHEN
        ResultActions result = post("/customers", body);
        // THEN
        assertResponse(result, status().isBadRequest(), expected);
    }

    @Test
    @DisplayName("Create customer should return 400 and validation errors when email is invalid")
    void create_invalid_email_400() throws Exception {
        // GIVEN invalid request (invalid email)
        String body = loadRequest("create-invalid-email.json");
        String expected = loadExpected("error-400-validation-email.json");
        // WHEN
        ResultActions result = post("/customers", body);
        // THEN
        assertResponse(result, status().isBadRequest(), expected);
    }

    @Test
    @DisplayName("Get customer by id should return 200 and body when customer exists")
    void getById_exists_200() throws Exception {
        // GIVEN one customer
        String id = createCustomerAndGetId();
        String expected = loadExpected("get-200.json").replace("__ID__", id);
        // WHEN
        ResultActions result = get("/customers/" + id);
        // THEN
        assertResponse(result, status().isOk(), expected);
    }

    @Test
    @DisplayName("Get customer by id should return 404 and error message when customer does not exist")
    void getById_notFound_404() throws Exception {
        // GIVEN nonexistent id
        UUID id = UUID.randomUUID();
        String expected = "{\"error\":\"Customer not found: " + id + "\"}";
        // WHEN
        ResultActions result = get("/customers/" + id);
        // THEN
        assertResponse(result, status().isNotFound(), expected);
    }

    @Test
    @DisplayName("List customers should return 200 and empty array when no customers exist")
    void list_empty_200() throws Exception {
        // GIVEN empty database
        String expected = loadExpected("list-empty.json");
        // WHEN
        ResultActions result = get("/customers");
        // THEN
        assertResponse(result, status().isOk(), expected);
    }

    @Test
    @DisplayName("List customers should return 200 and non-empty array when customers exist")
    void list_hasItems_200() throws Exception {
        // GIVEN one customer
        String id = createCustomerAndGetId();
        String expected = loadExpected("list-one-item.json").replace("__ID__", id);
        // WHEN
        ResultActions result = get("/customers");
        // THEN
        assertResponse(result, status().isOk(), expected);
    }

    @Test
    @DisplayName("Update customer should return 200 and updated body when customer exists and request is valid")
    void update_valid_200() throws Exception {
        // GIVEN one customer
        String id = createCustomerAndGetId();
        String updateBody = loadRequest("update-valid.json");
        String expected = loadExpected("update-200.json").replace("__ID__", id);
        // WHEN
        ResultActions result = put("/customers/" + id, updateBody);
        // THEN
        assertResponse(result, status().isOk(), expected);
    }

    @Test
    @DisplayName("Update customer should return 404 when customer does not exist")
    void update_notFound_404() throws Exception {
        // GIVEN nonexistent id
        UUID id = UUID.randomUUID();
        String body = loadRequest("update-valid.json");
        String expected = "{\"error\":\"Customer not found: " + id + "\"}";
        // WHEN
        ResultActions result = put("/customers/" + id, body);
        // THEN
        assertResponse(result, status().isNotFound(), expected);
    }

    @Test
    @DisplayName("Delete customer should return 204 when customer exists")
    void delete_exists_204() throws Exception {
        // GIVEN one customer
        String id = createCustomerAndGetId();
        // WHEN
        ResultActions result = delete("/customers/" + id);
        // THEN
        assertResponse(result, status().isNoContent(), null);
    }

    @Test
    @DisplayName("Delete customer should return 204 when customer does not exist (idempotent)")
    void delete_notExists_204_idempotent() throws Exception {
        // GIVEN nonexistent id
        UUID id = UUID.randomUUID();
        // WHEN
        ResultActions result = delete("/customers/" + id);
        // THEN
        assertResponse(result, status().isNoContent(), null);
    }
}
