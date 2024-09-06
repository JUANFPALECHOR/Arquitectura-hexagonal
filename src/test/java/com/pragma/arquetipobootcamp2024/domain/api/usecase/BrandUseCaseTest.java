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

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.pragma.arquetipobootcamp2024.domain.util.DomainConstants.ERROR_BRAND_NAME_EXISTS;

class BrandUseCaseTest {

    @Mock
    private IBrandRepository brandRepository;

    @Mock
    private IBrandEntityMapper brandEntityMapper;

    @InjectMocks
    private BrandUseCase brandUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializamos los mocks
    }

    @Test
    void shouldThrowExceptionIfBrandExists() {
        // Datos de prueba
        BrandRequest brandRequest = new BrandRequest("Electronics", "Description");
        Brand brand = new Brand("Electronics", "Description");

        // Simulamos el mapeo del DTO a la entidad de dominio
        when(brandEntityMapper.toDomain(brandRequest)).thenReturn(brand);

        // Simulamos que ya existe una marca con ese nombre
        when(brandRepository.findByName("Electronics")).thenReturn(Optional.of(brand));

        // Verificamos que se lance la excepción esperada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            brandUseCase.createBrand(brandRequest);
        });

        assertEquals(ERROR_BRAND_NAME_EXISTS, exception.getMessage());
    }

    @Test
    void shouldSaveBrandIfNew() {
        // Datos de prueba
        BrandRequest brandRequest = new BrandRequest("NewBrand", "Description");
        Brand brand = new Brand("NewBrand", "Description");

        // Simulamos el mapeo del DTO a la entidad de dominio
        when(brandEntityMapper.toDomain(brandRequest)).thenReturn(brand);

        // Simulamos que no existe una marca con ese nombre
        when(brandRepository.findByName("NewBrand")).thenReturn(Optional.empty());

        // Ejecutamos el método que estamos probando
        brandUseCase.createBrand(brandRequest);

        // Verificamos que se haya llamado al método save del repositorio
        verify(brandRepository, times(1)).save(brand);
    }
}