package com.example.demo.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.controller.DepartmentController;
import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
class DepartmentRestControllerTest {

    private MockMvc mockMvc;
    
    @Mock
    DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

   

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
    }

    @Test
    public void testSaveDepartment() throws Exception {
        Department department = new Department(1, "HR", null);

        Mockito.when(departmentService.saveDepartment(any(Department.class))).thenReturn(department);

        mockMvc.perform(post("/department/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"HR\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("HR"));

        Mockito.verify(departmentService, Mockito.times(1)).saveDepartment(any(Department.class));
    }
}
