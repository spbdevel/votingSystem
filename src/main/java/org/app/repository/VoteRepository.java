package org.app.repository;


import org.app.entity.User;
import org.app.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;


@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findByUser(User user);

    Vote findByUserAndModified(User user, Date date);

    @Query("SELECT v FROM Vote v WHERE v.user = :user and v.modified <= :date AND v.modified = CURRENT_DATE")
    Vote findByUserTodayBeforeTime(@Param("user") User user, @Param("date") Date date);

    @Query("SELECT v FROM Vote v WHERE v.user = :user AND v.modified BETWEEN :startDate AND :endDate")
    Vote findByUserToday(@Param("user") User user, @Param("startDate")Date startDate, @Param("endDate")Date endDate);

    @Query("SELECT v FROM Vote v WHERE v.modified BETWEEN :startDate AND :endDate")
    List<Vote> findToday(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

}
