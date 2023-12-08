package com.example.ActualApp.controller;

import com.example.ActualApp.auth.user.User;
import com.example.ActualApp.auth.user.UserRepository;
import com.example.ActualApp.controller.dto.CategoryDto;
import com.example.ActualApp.controller.dto.NameAndCountDto;
import com.example.ActualApp.controller.dto.NewCategoryDto;
import com.example.ActualApp.repository.CategoryRepository;
import com.example.ActualApp.repository.entity.Category;
import com.example.ActualApp.repository.entity.CategoryType;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.instancio.Select.field;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    private CategoryRepository categoryRepository;
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
    void shouldReturnAllCategories() throws Exception {
        //Given
        List<CategoryDto> categories = Instancio.ofList(CategoryDto.class)
                .size(1)
                .create();
        CategoryDto categoryDto = categories.get(0);

        Mockito.when(categoryService.getAllCategoriesByTypeAndUserId(CategoryType.REGULAR, UUID.fromString("2f85b8fe-2888-4afb-b022-3d34ee604192"))).thenReturn(categories);
        //When
        var response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories?type=regular&userId=2f85b8fe-2888-4afb-b022-3d34ee604192"));

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
        String id = "fd70909e-fc1b-4313-95d7-d07da61d90d0";
        CategoryDto categoryDto = new CategoryDto(UUID.fromString(id), "Test Category", 3, 0, 0 );
        NewCategoryDto newCategoryDto = new NewCategoryDto("Test Category", 3, UUID.fromString(id), CategoryType.REGULAR);
        Mockito.when(categoryService.saveNewCategory(newCategoryDto)).thenReturn(categoryDto);

        //When
        var result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                                "name" : "Test Category",
                                "priority" : 3,                     
                                "userId" : "fd70909e-fc1b-4313-95d7-d07da61d90d0",
                                "categoryType" : "REGULAR"             
                        }
                        """));

        //Then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoryDto.id().toString()))
                .andExpect(jsonPath("$.name").value(categoryDto.name()))
                .andExpect(jsonPath("$.priority").value(categoryDto.priority()))
                .andExpect(jsonPath("$.activitiesNumber").value(0))
                .andExpect(jsonPath("$.timeSpentInMinutes").value(0));
    }

    @Test
    void shouldReturnCategoriesWithTimeSpentByMonth() throws Exception {
        //Given
        String userId = "2f85b8fe-2888-4afb-b022-3d34ee604192";
        List<NameAndCountDto> expected = new ArrayList<>();
        expected.add(new NameAndCountDto("Test Category", 120L));

        Mockito.when(categoryService.getCategoriesWithTimeByMonth(9, UUID.fromString(userId))).thenReturn(expected);

        //When
        var response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories?byTimeSpent=true&userId=2f85b8fe-2888-4afb-b022-3d34ee604192&month=9"));

        //Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].description").value("Test Category"))
                .andExpect(jsonPath("$[0].timeSpentInMinutes").value(120L));
    }

    @Test
    void shouldReturnCategoriesWithTimeSpent() throws Exception {
        //Given
        String userId = "2f85b8fe-2888-4afb-b022-3d34ee604192";
        List<NameAndCountDto> expected = new ArrayList<>();
        expected.add(new NameAndCountDto("Test Category", 140L));

        Mockito.when(categoryService.getCategoriesWithTimeSpent()).thenReturn(expected);

        //When
        var response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories?byTimeSpent=true&userId=2f85b8fe-2888-4afb-b022-3d34ee604192&month=-1"));

        //Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].description").value("Test Category"))
                .andExpect(jsonPath("$[0].timeSpentInMinutes").value(140L));
    }

    @Test
    void shouldUpdateCategory() throws Exception {
        //Given
        UUID userId = UUID.fromString("2f85b8fe-2888-4afb-b022-3d34ee604192");
        UUID categoryId = UUID.randomUUID();
        NewCategoryDto newCategoryDto = new NewCategoryDto("Test Category",
                4,
                userId,
                CategoryType.REGULAR);

        CategoryDto categoryDto = new CategoryDto(categoryId,
                "Test Category",
                4,
                0,
                0);

        Mockito.when(categoryService.updateCategory(categoryId, newCategoryDto)).thenReturn(categoryDto);

        //When
        var response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/categories/" + categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                                "name" : "Test Category",
                                "priority" : 4,                     
                                "userId" : "2f85b8fe-2888-4afb-b022-3d34ee604192",
                                "categoryType" : "REGULAR"             
                        }
                        """));

        //Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(categoryDto.name()))
                .andExpect(jsonPath("$.priority").value(categoryDto.priority()))
                .andExpect(jsonPath("$.activitiesNumber").value(0))
                .andExpect(jsonPath("$.timeSpentInMinutes").value(0));
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        //Given
        UUID categoryId = UUID.randomUUID();
        Category category = Instancio.of(Category.class)
                .set(field(Category.class, "id"), categoryId)
                        .create();

        //When
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/categories/" + categoryId));

        //Then
        verify(categoryService, times(1)).deleteCategory(categoryId);
    }
}