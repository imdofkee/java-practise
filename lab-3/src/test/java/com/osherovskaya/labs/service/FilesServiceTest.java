package com.osherovskaya.labs.service;

import com.osherovskaya.labs.database.model.File;
import com.osherovskaya.labs.database.repository.FilesEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FilesServiceTest {

    @Mock
    private FilesEventRepository repository;

    @InjectMocks
    private FilesEventService service;

    private File testFile;

    @BeforeEach
    void setUp() {
        testFile = new File(1, "test.txt", "1024");
    }

    @Test
    void save_shouldCallRepositoryAndReturnId() {
        when(repository.save(testFile)).thenReturn(1);

        int id = service.save(testFile);

        assertEquals(1, id);
        verify(repository, times(1)).save(testFile);
    }

    @Test
    void save_withParameters_shouldCreateFileAndReturnId() {
        when(repository.save(any(File.class))).thenReturn(1);

        int id = service.save(1, "приветЯнеПопугай.txt", 1024);

        assertEquals(1, id);
        verify(repository, times(1)).save(any(File.class));
    }

    @ParameterizedTest
    @CsvSource({
            "1, its_a_file, 1024",
            "2, wait_is_it_a_file, 2048",
            "3, oh_no_its_reexam, 512"
    })
    void save_parameterized_shouldReturnId(int id, String name, int size) {
        File file = new File(id, name, String.valueOf(size));
        when(repository.save(file)).thenReturn(id);

        int result = service.save(file);

        assertEquals(id, result);
        verify(repository).save(file);
    }

    @Test
    void save_whenRepositoryThrowsException_shouldReturnMinusOne() {
        when(repository.save(any(File.class))).thenThrow(new RuntimeException("DB error"));

        int result = service.save(testFile);

        assertEquals(-1, result);
        verify(repository).save(testFile);
    }



    @Test
    void findById_whenFileExists_shouldReturnFile() {
        when(repository.findById(1)).thenReturn(testFile);

        File found = service.findById(1);

        assertNotNull(found);
        assertEquals(1, found.getFileID());
        assertEquals("test.txt", found.getFileName());
        verify(repository, times(1)).findById(1);
    }

    @Test
    void findById_whenFileNotFound_shouldThrowRuntimeException() {
        when(repository.findById(99)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.findById(99));
        assertEquals("Файл с ID=99 не найден", exception.getMessage());
        verify(repository, times(1)).findById(99);
    }

    @ParameterizedTest
    @CsvSource({
            "1, existing.txt",
            "2, another.txt"
    })
    void findById_parameterized_shouldReturnFile(int id, String name) {
        File file = new File(id, name, "1024");
        when(repository.findById(id)).thenReturn(file);

        File found = service.findById(id);

        assertNotNull(found);
        assertEquals(id, found.getFileID());
        assertEquals(name, found.getFileName());
    }

    // ==================== FIND BY NAME TESTS ====================

    @Test
    void findByName_whenFileExists_shouldReturnFile() {
        when(repository.findByField("test.txt")).thenReturn(testFile);

        File found = service.findByField("test.txt");

        assertNotNull(found);
        assertEquals("test.txt", found.getFileName());
        verify(repository, times(1)).findByField("test.txt");
    }

    @Test
    void findByName_Ruined() {
        when(repository.findByField("missing.txt")).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.findByField("missing.txt"));
        assertEquals("Файл с именем 'missing.txt' не найден", exception.getMessage());
        verify(repository, times(1)).findByField("missing.txt");
    }

    @Test
    void findAll_shouldReturnListOfFiles() {
        List<File> expected = Arrays.asList(
                testFile,
                new File(2, "another.txt", "512")
        );
        when(repository.findAll()).thenReturn(expected);

        List<File> actual = service.findAll();

        assertEquals(expected, actual);
        assertEquals(2, actual.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findAll_whenRepositoryReturnsEmptyList_shouldReturnEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<File> actual = service.findAll();

        assertTrue(actual.isEmpty());
        verify(repository).findAll();
    }

    // ==================== UPDATE TESTS ====================

    @Test
    void update_whenFileExists_shouldUpdateSuccessfully() {
        File updatedFile = new File(1, "updated.txt", "2048");
        when(repository.findById(1)).thenReturn(testFile);
        when(repository.update(updatedFile)).thenReturn(true);

        assertDoesNotThrow(() -> service.update(updatedFile));
        verify(repository).findById(1);
        verify(repository).update(updatedFile);
    }

    @Test
    void update_whenFileDoesNotExist_shouldThrowRuntimeException() {
        File nonExistentFile = new File(99, "missing.txt", "1024");
        when(repository.findById(99)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.update(nonExistentFile));
        assertEquals("Невозможно обновить: файл с ID=99 не существует", exception.getMessage());
        verify(repository).findById(99);
        verify(repository, never()).update(any());
    }

    @Test
    void update_whenUpdateFails_shouldThrowRuntimeException() {
        File updatedFile = new File(1, "updated.txt", "2048");
        when(repository.findById(1)).thenReturn(testFile);
        when(repository.update(updatedFile)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.update(updatedFile));
        assertEquals("Не удалось обновить файл с ID=1", exception.getMessage());
        verify(repository).findById(1);
        verify(repository).update(updatedFile);
    }

    @Test
    void update_withParameters_shouldWorkCorrectly() {
        when(repository.findById(1)).thenReturn(testFile);
        when(repository.update(any(File.class))).thenReturn(true);

        assertDoesNotThrow(() -> service.update(1, "new name", 4096));
        verify(repository).findById(1);
        verify(repository).update(any(File.class));
    }


    @Test
    void deleteById_shouldCallRepositoryDelete() {
        doNothing().when(repository).deleteById(1);

        assertDoesNotThrow(() -> service.deleteById(1));
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void deleteById_withNonExistentId_shouldStillCallRepository() {
        doNothing().when(repository).deleteById(99);

        assertDoesNotThrow(() -> service.deleteById(99));
        verify(repository).deleteById(99);
    }



    @Test
    void deleteByIdSafe_whenFileExists_shouldDelete() {
        when(repository.findById(1)).thenReturn(testFile);
        doNothing().when(repository).deleteById(1);

        assertDoesNotThrow(() -> service.deleteByIdSafe(1));
        verify(repository).findById(1);
        verify(repository).deleteById(1);
    }

    @Test
    void deleteByIdSafe_whenFileDoesNotExist_shouldThrowException() {
        when(repository.findById(99)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.deleteByIdSafe(99));
        assertEquals("Невозможно удалить: файл с ID=99 не существует", exception.getMessage());
        verify(repository).findById(99);
        verify(repository, never()).deleteById(anyInt());
    }



    @Test
    void exists_byId_whenFileExists_shouldReturnTrue() {
        when(repository.findById(1)).thenReturn(testFile);

        assertTrue(service.exists(1));
        verify(repository).findById(1);
    }

    @Test
    void exists_byId_whenFileDoesNotExist_shouldReturnFalse() {
        when(repository.findById(99)).thenReturn(null);

        assertFalse(service.exists(99));
        verify(repository).findById(99);
    }

    @Test
    void exists_byName_whenFileExists_shouldReturnTrue() {
        when(repository.findByField("test.txt")).thenReturn(testFile);

        assertTrue(service.exists("test.txt"));
        verify(repository).findByField("test.txt");
    }

    @Test
    void exists_byName_whenFileDoesNotExist_shouldReturnFalse() {
        when(repository.findByField("missing.txt")).thenReturn(null);

        assertFalse(service.exists("missing.txt"));
        verify(repository).findByField("missing.txt");
    }
}