package com.wipro.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wipro.entity.Progress;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProgressRepositoryTest {


    @Autowired
    private ProgressRepository progressRepository;



    @Test
    void saveProgress_ShouldReturnSavedProgress() {


        Progress progress = new Progress();

        progress.setUserId(1L);
        progress.setWeight(75.5);
        progress.setBodyFat(18.0);
        progress.setRecordedDate(
                LocalDate.now()
        );


        Progress saved =
                progressRepository.save(progress);



        assertEquals(
                75.5,
                saved.getWeight()
        );

        assertEquals(
                18.0,
                saved.getBodyFat()
        );
    }




    @Test
    void findById_ShouldReturnProgress() {


        Progress progress = new Progress();

        progress.setUserId(2L);
        progress.setWeight(80.0);
        progress.setBodyFat(20.0);
        progress.setRecordedDate(
                LocalDate.now()
        );


        Progress saved =
                progressRepository.save(progress);



        Optional<Progress> result =
                progressRepository
                        .findById(saved.getId());



        assertTrue(
                result.isPresent()
        );


        assertEquals(
                80.0,
                result.get().getWeight()
        );
    }





    @Test
    void findByUserId_ShouldReturnProgressList() {


        Progress progress1 = new Progress();

        progress1.setUserId(10L);
        progress1.setWeight(70.0);
        progress1.setBodyFat(15.0);
        progress1.setRecordedDate(
                LocalDate.now()
        );



        Progress progress2 = new Progress();

        progress2.setUserId(10L);
        progress2.setWeight(68.0);
        progress2.setBodyFat(14.0);
        progress2.setRecordedDate(
                LocalDate.now()
        );



        progressRepository.save(progress1);
        progressRepository.save(progress2);



        List<Progress> result =
                progressRepository
                        .findByUserId(10L);



        assertEquals(
                2,
                result.size()
        );


        assertEquals(
                70.0,
                result.get(0).getWeight()
        );
    }





    @Test
    void findByUserId_WhenNoProgress_ShouldReturnEmptyList() {


        List<Progress> result =
                progressRepository
                        .findByUserId(999L);



        assertTrue(
                result.isEmpty()
        );
    }





    @Test
    void deleteProgress_ShouldRemoveEntity() {


        Progress progress = new Progress();


        progress.setUserId(5L);
        progress.setWeight(90.0);
        progress.setBodyFat(25.0);
        progress.setRecordedDate(
                LocalDate.now()
        );



        Progress saved =
                progressRepository.save(progress);



        progressRepository.delete(saved);



        Optional<Progress> result =
                progressRepository
                        .findById(saved.getId());



        assertFalse(
                result.isPresent()
        );
    }

}