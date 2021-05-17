package com.springmockitomvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmockitomvc.dao.EmployeeRepository;
import com.springmockitomvc.dao.EmployeeService;
import com.springmockitomvc.model.Employee;
import com.springmockitomvc.model.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Autowired
	private MockMvc mockmvc;

	@MockBean
	EmployeeService employeeService;

	ObjectMapper mapper =  new ObjectMapper();


	@Test
	public void testAddEmployee() throws Exception {
		Employee employee  = new Employee();
		employee.setName("Das Subhasish");
		employee.setDept("DB");
		String jsonRequest =  mapper.writeValueAsString(employee);
		MvcResult result = mockmvc.perform(
								post("/addEmployee")
								.content(jsonRequest)
								.contentType(MediaType.APPLICATION_JSON_VALUE)
							)
							.andExpect(status().isOk())
							.andReturn();
		String resultContent =  result.getResponse().getContentAsString();
		Response actualResponse = mapper.readValue(resultContent, Response.class);
		assertTrue(actualResponse.isSuccess()==Boolean.TRUE);

	}

	@Test
	public void testGetEmployees() throws Exception {
		MvcResult result = mockmvc.perform(get("/employees"))
				.andExpect(status().isOk())
				.andReturn();
		String resultContent =  result.getResponse().getContentAsString();
		Response actualResponse = mapper.readValue(resultContent, Response.class);

		assertTrue(actualResponse.isSuccess()==Boolean.TRUE);
		assertTrue(actualResponse.getMessage().contains("Record counts:"));

	}

	@Test
	public void testGetEmployeeByName() throws  Exception{
		String employeeName = "Alibaba";
		//Create a mock Employee, when call employeeService.getEmployeeById, we don't need to call directly from DB, just return a created mock employee
		Employee mockEmployee =  new Employee();
		mockEmployee.setName(employeeName);
		mockEmployee.setDept("DB");

		when(employeeService.getEmployeeByName(employeeName))
				.thenReturn(mockEmployee);


		RequestBuilder requestBuilder = get("/employee/{name}", employeeName)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MediaType.APPLICATION_JSON_VALUE);
		this.mockmvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(employeeName))
				.andExpect(jsonPath("$.dept").value("DB"));

	}
}
