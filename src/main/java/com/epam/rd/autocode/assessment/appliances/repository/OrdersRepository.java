package com.epam.rd.autocode.assessment.appliances.repository;

import com.epam.rd.autocode.assessment.appliances.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByClientEmail(String email);

    List<Orders> findByEmployeeId(Long employeeId);
    @Modifying
    @Query(value = "DELETE FROM orders_order_row_set WHERE order_row_set_id = :rowId", nativeQuery = true)
    void removeOrderRowFromOrders(Long rowId);
}
