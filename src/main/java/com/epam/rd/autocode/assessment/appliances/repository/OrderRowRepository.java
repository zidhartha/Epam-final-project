package com.epam.rd.autocode.assessment.appliances.repository;
import com.epam.rd.autocode.assessment.appliances.model.OrderRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRowRepository extends JpaRepository<OrderRow, Long> {
    void deleteByAppliance_Id(Long id);
    List<OrderRow> findByAppliance_Id(Long id);
    @Modifying
    @Query(
            value = "DELETE FROM orders_order_row_set WHERE order_row_set_id = :rowId",
            nativeQuery = true
    )
    void removeOrderRowFromOrders(Long rowId);
}
