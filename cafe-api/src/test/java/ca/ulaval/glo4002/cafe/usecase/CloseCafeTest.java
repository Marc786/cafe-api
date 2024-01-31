package ca.ulaval.glo4002.cafe.usecase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CloseCafeTest {

    private final CafeRepository cafeRepositoryMock = mock(CafeRepository.class);
    private final Cafe cafeMock = mock(Cafe.class);
    private CloseCafe closeCafe;

    @BeforeEach
    void createCloseCafe() {
        closeCafe = new CloseCafe(cafeRepositoryMock);
    }

    @Test
    void closeCafe_closeCafeAndSave() {
        when(cafeRepositoryMock.fetchCafe()).thenReturn(cafeMock);

        closeCafe.close();

        verify(cafeMock).close();
        verify(cafeRepositoryMock).save(cafeMock);
    }
}
