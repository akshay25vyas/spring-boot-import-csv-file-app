package com.pixeltrice.springbootimportcsvfileapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStocksRepository extends JpaRepository<UserStocks, Integer> {

    @Query(value = "SELECT s from UserStocks s WHERE s.userid=?1 AND s.stockid=?2")
    UserStocks findUserStocksFromUserIdAndStockId(Integer userId, Integer stockId);
}
