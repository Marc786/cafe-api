package ca.ulaval.glo4002.cafe.usecase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.usecase.assembler.CafeDTOAssembler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FindCafeTest {

    private final CafeRepository cafeRepositoryMock = mock(CafeRepository.class);
    private final CafeDTOAssembler cafeAssemblerMock = mock(CafeDTOAssembler.class);
    private final Cafe cafeMock = mock(Cafe.class);
    private FindCafe findCafe;

    @BeforeEach
    public void createFindCafe() {
        findCafe = new FindCafe(cafeRepositoryMock, cafeAssemblerMock);
    }

    @Test
    void findCafe_verifiesToCafeDTOIsCalled() {
        when(cafeRepositoryMock.fetchCafe()).thenReturn(cafeMock);

        findCafe.find();

        verify(cafeAssemblerMock).cafeToCafeDTO(cafeMock);
    }
}
