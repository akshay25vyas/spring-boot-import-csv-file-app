package com.pixeltrice.springbootimportcsvfileapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperTutorialRepository extends JpaRepository<DeveloperTutorial, Integer>{

    @Query(value = "SELECT u FROM DeveloperTutorial u WHERE u.email = ?1")
    DeveloperTutorial findUserByEmail(String email);
}
