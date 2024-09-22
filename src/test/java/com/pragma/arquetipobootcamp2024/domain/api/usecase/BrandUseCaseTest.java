package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.BrandRequest;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.pragma.arquetipobootcamp2024.domain.model.Brand;
import com.pragma.arquetipobootcamp2024.domain.spi.IBrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.pragma.arquetipobootcamp2024.domain.util.DomainConstants.ERROR_BRAND_NAME_EXISTS;



class BrandUseCaseTest {

    @Mock
    private IBrandRepository brandRepository; // Simulamos el repositorio

    @Mock
    private IBrandEntityMapper brandEntityMapper; // Simulamos el mapper

    @InjectMocks
    private BrandUseCase brandUseCase; // Inyectamos el use case que vamos a probar

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializamos los mocks
    }

    @Test
    void shouldThrowExceptionIfBrandExists() {
        // Arrange: Creamos un BrandRequest y simulamos la conversión a Brand
        BrandRequest brandRequest = new BrandRequest("Electronics", "Description");
        Brand brand = new Brand(null, "Electronics", "Description");

        // Simulamos el mapeo del DTO a la entidad de dominio
        when(brandEntityMapper.toDomain(brandRequest)).thenReturn(brand);

        // Simulamos que ya existe una marca con ese nombre
        when(brandRepository.findByName("Electronics")).thenReturn(Optional.of(brand));

        // Act & Assert: Verificamos que se lance la excepción esperada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {

        });

        assertEquals(ERROR_BRAND_NAME_EXISTS, exception.getMessage());
    }

    @Test
    void shouldSaveBrandIfNew() {
        // Arrange: Creamos un BrandRequest y simulamos la conversión a Brand
        BrandRequest brandRequest = new BrandRequest("NewBrand", "Description");
        Brand brand = new Brand(null, "NewBrand", "Description");

        // Simulamos el mapeo del DTO a la entidad de dominio
        when(brandEntityMapper.toDomain(brandRequest)).thenReturn(brand);

        // Simulamos que no existe una marca con ese nombre
        when(brandRepository.findByName("NewBrand")).thenReturn(Optional.empty());


        // Assert: Verificamos que se haya llamado al método save del repositorio
        verify(brandRepository, times(1)).save(brand);
    }

    @Test
    void testListBrandsPaginatedAndSortedAscending() {
        // Datos de prueba
        Brand brand1 = new Brand(1L, "Brand A", "Description A");
        Brand brand2 = new Brand(2L, "Brand B", "Description B");

        List<Brand> brands = Arrays.asList(brand1, brand2);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));
        Page<Brand> brandPage = new PageImpl<>(brands, pageable, brands.size());

        // Simulamos la llamada al repositorio
        when(brandRepository.findAll(pageable)).thenReturn(brandPage);

        // Ejecutamos el método bajo prueba
        Page<Brand> result = brandUseCase.listBrands(0, 10, "ASC");

        // Verificamos que los resultados son los esperados
        assertEquals(2, result.getTotalElements());
        assertEquals("Brand A", result.getContent().get(0).getName());
        assertEquals("Brand B", result.getContent().get(1).getName());

        verify(brandRepository, times(1)).findAll(pageable);
    }

    @Test
    void testListBrandsPaginatedAndSortedDescending() {
        // Datos de prueba
        Brand brand1 = new Brand(1L, "Brand A", "Description A");
        Brand brand2 = new Brand(2L, "Brand B", "Description B");

        List<Brand> brands = Arrays.asList(brand2, brand1); // Orden invertido para la prueba
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "name"));
        Page<Brand> brandPage = new PageImpl<>(brands, pageable, brands.size());

        // Simulamos la llamada al repositorio
        when(brandRepository.findAll(pageable)).thenReturn(brandPage);

        // Ejecutamos el método bajo prueba
        Page<Brand> result = brandUseCase.listBrands(0, 10, "DESC");

        // Verificamos que los resultados son los esperados
        assertEquals(2, result.getTotalElements());
        assertEquals("Brand B", result.getContent().get(0).getName());
        assertEquals("Brand A", result.getContent().get(1).getName());

        verify(brandRepository, times(1)).findAll(pageable);
    }
}