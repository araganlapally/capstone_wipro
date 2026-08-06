package com.wipro.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.modelmapper.ModelMapper;


import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import com.wipro.dto.ProgressRequest;
import com.wipro.dto.ProgressResponse;
import com.wipro.dto.UserResponse;
import com.wipro.entity.Progress;
import com.wipro.exception.ResourceNotFoundException;
import com.wipro.repository.ProgressRepository;



class ProgressServiceImplTest {


    @Mock
    private ProgressRepository progressRepository;


    @Mock
    private UserServiceClient userServiceClient;


    private ProgressServiceImpl progressService;


    private ModelMapper modelMapper;



    @BeforeEach
    void setup() {


        MockitoAnnotations.openMocks(this);


        modelMapper =
                new ModelMapper();


        progressService =
                new ProgressServiceImpl(
                        progressRepository,
                        modelMapper,
                        userServiceClient
                );
    }





    @Test
    void saveProgress_ShouldSaveSuccessfully() {


        ProgressRequest request =
                new ProgressRequest();


        request.setUserId(1L);
        request.setWeight(75.0);
        request.setBodyFat(18.5);
        request.setRecordedDate(
                LocalDate.now()
        );


        UserResponse user =
                new UserResponse();


        user.setId(1L);



        when(userServiceClient
                .getUserById(1L))
                .thenReturn(user);



        Progress progress =
                new Progress();


        progress.setId(10L);
        progress.setUserId(1L);
        progress.setWeight(75.0);
        progress.setBodyFat(18.5);
        progress.setRecordedDate(
                LocalDate.now()
        );



        when(progressRepository
                .save(
                org.mockito.ArgumentMatchers.any()
                ))
                .thenReturn(progress);



        ProgressResponse response =
                progressService
                .saveProgress(request);



        assertEquals(
                75.0,
                response.getWeight()
        );
    }







    @Test
    void saveProgress_WhenUserNotFound_ShouldThrowException() {


        ProgressRequest request =
                new ProgressRequest();


        request.setUserId(100L);



        when(userServiceClient
                .getUserById(100L))
                .thenReturn(null);



        assertThrows(
                ResourceNotFoundException.class,
                () ->
                progressService
                .saveProgress(request)
        );
    }







    @Test
    void getProgressByUserId_ShouldReturnList() {


        Progress progress =
                new Progress();


        progress.setId(1L);
        progress.setUserId(1L);
        progress.setWeight(80.0);



        when(progressRepository
                .findByUserId(1L))
                .thenReturn(
                        List.of(progress)
                );



        List<ProgressResponse> result =
                progressService
                .getProgressByUserId(1L);



        assertEquals(
                1,
                result.size()
        );


        assertEquals(
                80.0,
                result.get(0).getWeight()
        );
    }







    @Test
    void updateProgress_ShouldUpdateSuccessfully() {


        Progress existing =
                new Progress();


        existing.setId(1L);
        existing.setUserId(1L);
        existing.setWeight(80.0);



        when(progressRepository
                .findById(1L))
                .thenReturn(
                        Optional.of(existing)
                );



        ProgressRequest request =
                new ProgressRequest();


        request.setUserId(1L);
        request.setWeight(75.0);
        request.setBodyFat(15.0);
        request.setRecordedDate(
                LocalDate.now()
        );



        when(progressRepository
                .save(existing))
                .thenReturn(existing);



        ProgressResponse response =
                progressService
                .updateProgress(
                        1L,
                        request
                );



        assertEquals(
                75.0,
                response.getWeight()
        );


        verify(
                progressRepository
        )
        .save(existing);
    }







    @Test
    void updateProgress_WhenNotFound_ShouldThrowException() {


        when(progressRepository
                .findById(99L))
                .thenReturn(
                        Optional.empty()
                );



        ProgressRequest request =
                new ProgressRequest();



        assertThrows(
                ResourceNotFoundException.class,
                () ->
                progressService
                .updateProgress(
                        99L,
                        request
                )
        );
    }

}