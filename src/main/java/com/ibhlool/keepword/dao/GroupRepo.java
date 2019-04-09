package com.ibhlool.keepword.dao;

import com.ibhlool.keepword.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepo extends JpaRepository<Group,Integer> {
}
