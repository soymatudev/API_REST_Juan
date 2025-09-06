package com.juan.apirestjuan.service;

import com.juan.apirestjuan.model.User;
import com.juan.apirestjuan.model.Address;
import com.juan.apirestjuan.util.TimeUtil;
import com.juan.apirestjuan.util.ValidatorUtil;
import com.juan.apirestjuan.util.AESUtil;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import java.util.*;

@Service
public class UserService {
    private final List<User> users =  new ArrayList<>();
    private Long idCounter = 1L;
    private SecretKey key = null;
    private IvParameterSpec iv = null;
    private AESUtil aesUtil = null;

    public UserService() throws Exception {
        this.aesUtil = new AESUtil();

        String passwordEncrypt = this.aesUtil.getEncrypt("admin");
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address(1L, "Casa", "Calle 123", "UK"));
        users.add(new User(idCounter++, "Admin", passwordEncrypt, "GAMJ0501147Z4", "admin@gmail.com", "+52 1122 3344", addresses, TimeUtil.ValidCreated_at()));
        users.add(new User(idCounter++, "Juan", passwordEncrypt, "", "juan@gmail.com", "+52 1122 3344", addresses, TimeUtil.ValidCreated_at()));
        users.add(new User(idCounter++, "Zian", passwordEncrypt, "APMJ0501147Z4", "zian@gmail.com", "+52 1122 3344", addresses, TimeUtil.ValidCreated_at()));
    }

    public List<User> getAllUsers(){
        return users;
    }

    public Optional<User> getUserById(Long id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public User createUser(User user) throws Exception {
        if (!ValidatorUtil.isValidFormatTax_id(user.getTax_id())){
            //throw new IllegalArgumentException("Tax_id no cumple con el formato RFC");
            user.setTax_id("AARR990101XXX");
        }
        if (ValidatorUtil.isValidUniqueTax_id(users,  user.getTax_id())) {
            //throw new IllegalArgumentException("El Tax_Id ya existe en la Base de Datos");
            user.setTax_id("AARR990101XXX");
        }
        if (!ValidatorUtil.isValidFormatPhone(user.getPhone())) {
            user.setPhone("+");
        }

        String passwordEncrypt = this.aesUtil.getEncrypt(user.getPassword());
        user.setId(idCounter++);
        user.setCreated_at(TimeUtil.ValidCreated_at());
        user.setPassword(passwordEncrypt);
        users.add(user);
        return user;
    }

    public Optional<User> updateUser(Long id, User updateUser) throws Exception {
        String passwordEncrypt = this.aesUtil.getEncrypt(updateUser.getPassword());
        return getUserById(id).map(user -> {
            user.setName(updateUser.getName());
            user.setPassword(passwordEncrypt);
            user.setEmail(updateUser.getEmail());
            user.setPhone(updateUser.getPhone());
            user.setAddress(updateUser.getAddress());
            return user;
        });
    }

    public boolean deleteUser(Long id) {
        return users.removeIf(u -> u.getId().equals(id));
    }

    public Optional<User> login(String name, String password) {
        return users.stream()
                .filter(u -> u.getName().equals(name) && u.getPassword().equals(password))
                .findFirst();
    }

    public List<User> getUsersBySorting(String sortedBy) {
        if(sortedBy != null && !sortedBy.isEmpty()) {
            switch (sortedBy.toLowerCase()) {
                case "email":
                    return sortByEmail(getAllUsers());
                case "id":
                    return sortById(getAllUsers());
                case "name":
                    return sortByName(getAllUsers());
                case "phone":
                    return sortByPhone(getAllUsers());
                case "tax_id":
                    return sortBytTax_id(getAllUsers());
                case "created_at":
                    return sortByCreated_at(getAllUsers());
                default:
                    return getAllUsers();
            }
        }
        return getAllUsers();
    }

    private List<User> sortByEmail(List<User> usersList) {
        usersList.sort(Comparator.comparing(User::getEmail));
        return usersList;
    }

    private List<User> sortById(List<User> usersList) {
        usersList.sort(Comparator.comparing(User::getId));
        return usersList;
    }

    private List<User> sortByName(List<User>  usersList) {
        usersList.sort(Comparator.comparing(User::getName));
        return usersList;
    }

    private List<User> sortByPhone(List<User> usersList) {
        usersList.sort(Comparator.comparing(User::getPhone));
        return usersList;
    }

    private List<User> sortBytTax_id(List<User>  usersList) {
        usersList.sort(Comparator.comparing(User::getTax_id));
        return usersList;
    }

    private List<User> sortByCreated_at(List<User> usersList) {
        usersList.sort(Comparator.comparing(User::getCreated_at));
        return usersList;
    }

}