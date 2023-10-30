package com.example.ActualApp.controller;

import com.example.ActualApp.auth.user.User;
import com.example.ActualApp.auth.user.UserRepository;
import com.example.ActualApp.controller.dto.CategoryDto;
import com.example.ActualApp.controller.dto.NewCategoryDto;
import com.example.ActualApp.service.CategoryService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
@WithMockUser
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;
    @MockBean
    private UserRepository userRepository;

    @Test
    void shouldReturnCategoryByGivenId() throws Exception {
        //Given
        UUID id = UUID.randomUUID();
        CategoryDto categoryDto = Instancio.create(CategoryDto.class);
        Mockito.when(categoryService.getCategoryById(id)).thenReturn(categoryDto);

        //When
        var response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/" + id));

        //Then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(categoryDto.id().toString()))
                .andExpect(jsonPath("$.name").value(categoryDto.name()))
                .andExpect(jsonPath("$.priority").value(categoryDto.priority()))
                .andExpect(jsonPath("$.activitiesNumber").value(categoryDto.activitiesNumber()))
                .andExpect(jsonPath("$.timeSpentInMinutes").value(categoryDto.timeSpentInMinutes()));

    }

    @Test
    void shouldReturnAllActivities() throws Exception {
        //Given
        List<CategoryDto> categories = Instancio.ofList(CategoryDto.class)
                .size(1)
                .create();
        CategoryDto categoryDto = categories.get(0);
        Mockito.when(categoryService.getAllCategories()).thenReturn(categories);

        //When
        var response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories"));

        //Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(categoryDto.id().toString()))
                .andExpect(jsonPath("$[0].name").value(categoryDto.name().toString()))
                .andExpect(jsonPath("$[0].priority").value(categoryDto.priority()))
                .andExpect(jsonPath("$[0].activitiesNumber").value(categoryDto.activitiesNumber()))
                .andExpect(jsonPath("$[0].timeSpentInMinutes").value(categoryDto.timeSpentInMinutes()));
    }

    @Test
    void shouldReturnNewCategory() throws Exception {
        //Given
        String id = "13a629b4-0bc2-4437-bc73-5914670c1a24";
        CategoryDto categoryDto = new CategoryDto(UUID.fromString(id), "Test Category", 3, 0, 0 );
        NewCategoryDto newCategoryDto = new NewCategoryDto("Test Category", 3, UUID.fromString(id));
        User user = new User("Adam", "password");
        Mockito.when(categoryService.saveNewCategory(newCategoryDto)).thenReturn(categoryDto);
        Mockito.when(userRepository.findById(UUID.fromString(id))).thenReturn(Optional.of(user));

        //When
        var result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                                "name" : "Test Category",
                                "priority": 3,                      
                                "user_Id": "903f891f-4c2f-4f52-9768-bd499f35b02d"              
                        }
                        """));

        //Then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(categoryDto.name()))
                .andExpect(jsonPath("$.priority").value(categoryDto.priority()))
                .andExpect(jsonPath("$.id").value(categoryDto.id()))
                .andExpect(jsonPath("$.activitiesNumber").value(0))
                .andExpect(jsonPath("$.timeSpentInMinutes").value(0));

    }
}