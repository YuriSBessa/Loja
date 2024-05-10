package com.loja.loja.Repositories;

import com.loja.loja.Models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom{
    @Autowired
    EntityManager entityManager;

    @Override
    public List<User> getListOfUsers() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery.multiselect(
                root.get("firstName"),
                root.get("lastName"),
                root.get("cpf"),
                root.get("email"),
                root.get("dateOfBirth")
        );

        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        List<User> listOfUser = typedQuery.getResultList();
        return listOfUser;
    }
    @Override
    public User getUserById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery.multiselect(
                root.get("firstName"),
                root.get("lastName"),
                root.get("cpf"),
                root.get("email"),
                root.get("dateOfBirth")
        );

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        List<User> resultList = typedQuery.getResultList();

        if(resultList.isEmpty()){
            return null;
        }else {
            return resultList.get(0);
        }
    }
}
