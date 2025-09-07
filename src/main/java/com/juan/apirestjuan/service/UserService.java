package com.juan.apirestjuan.service;

import com.juan.apirestjuan.model.User;
import com.juan.apirestjuan.model.Address;
import com.juan.apirestjuan.util.TimeUtil;
import com.juan.apirestjuan.util.ValidatorUtil;
import com.juan.apirestjuan.util.AESUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.*;

@Service
public class UserService {
    private static final Logger printLog = LoggerFactory.getLogger(UserService.class);
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
        users.add(new User(idCounter++, "Admin", passwordEncrypt, "GAFA980602F82", "admin@gmail.com", "+52 1122 3344", addresses, TimeUtil.ValidCreated_at()));
        users.add(new User(idCounter++, "Juan", passwordEncrypt, "", "juan@gmail.com", "+52 1122 3344", addresses, TimeUtil.ValidCreated_at()));
        users.add(new User(idCounter++, "Zian", passwordEncrypt, "APMJ0300547Z4", "zian@gmail.com", "+52 1122 3344", addresses, TimeUtil.ValidCreated_at()));
    }

    public List<User> getAllUsers(){
        printLog.info("Get all users => {}", users);
        return users;
    }

    public Optional<User> getUserById(Long id) {
        printLog.info("Getting Users");
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public User createUser(User user) throws Exception {
        validatorUser(user);

        String passwordEncrypt = this.aesUtil.getEncrypt(user.getPassword());
        user.setId(idCounter++);
        user.setCreated_at(TimeUtil.ValidCreated_at());
        user.setPassword(passwordEncrypt);
        users.add(user);
        printLog.info("Create user correct => {}", user.toString());
        return user;
    }

    public Optional<User> updateUser(Long id, User updateUser) throws Exception {
        validatorUser(updateUser);

        String passwordEncrypt = this.aesUtil.getEncrypt(updateUser.getPassword());
        return getUserById(id).map(user -> {
            user.setName(updateUser.getName());
            user.setPassword(passwordEncrypt);
            user.setEmail(updateUser.getEmail());
            user.setPhone(updateUser.getPhone());
            user.setAddress(updateUser.getAddress());
            printLog.info("Successfully created user => {}", user.toString());
            return user;
        });
    }

    public void validatorUser(User user) {
        if (!ValidatorUtil.isValidFormatTax_id(user.getTax_id())){
            printLog.info("Tax_id does not comply with the RFC format, Set Gen => {} - AARR990101XXX", user.getTax_id());
            user.setTax_id("AARR990101XXX");
            throw new IllegalArgumentException("Tax_id does not comply with the RFC format, Set Gen");
        }
        if (ValidatorUtil.isValidUniqueTax_id(users,  user.getTax_id())) {
            printLog.info("The Tax_Id already exists in the Database, set Gen => {} - AARR990101XXX", user.getTax_id());
            user.setTax_id("AARR990101XXX");
            throw new IllegalArgumentException("The Tax_Id already exists in the Database");
        }
        if (!ValidatorUtil.isValidFormatPhone(user.getPhone())) {
            printLog.info("Phone does not comply with the format, set Gen => {} - +1212555123", user.getPhone());
            user.setPhone("+1212555123");
            throw new IllegalArgumentException("Phone does not comply with the format");
        }
    }

    public boolean deleteUser(Long id) {
        printLog.info("Delete user by id => {}", id);
        return users.removeIf(u -> u.getId().equals(id));
    }

    public Optional<User> login(String username, String password) {
        return users.stream()
                .filter(u -> {
                    try {
                        printLog.info("Checking user access => {}, passReady = {}, passset = {}", username, this.aesUtil.getDecrypt(u.getPassword()), password);
                        return u.getTax_id().equals(username) && this.aesUtil.getDecrypt(u.getPassword()).equals(password);
                    } catch (Exception e) {
                        printLog.info("Error => {}", e.getMessage());
                        throw new RuntimeException(e);
                    }
                })
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

    public List<User> getUsersByFilter(String keyValue) throws NoSuchMethodException {
        if(!ValidatorUtil.isValidFormatKeyFilter(keyValue)){
            printLog.error("Not Format Filter - Ex: filter+co+value", keyValue);
            throw new IllegalArgumentException("The filter is not in the correct format");
        }

        List<String> filterValues = Arrays.asList(keyValue.split(" "));
        String filterBy = filterValues.get(0);
        String filterQuery = filterValues.get(1);
        String filterValue = filterValues.get(2);
        Class<String> stringClass = String.class;
        Method methodFilter;

        String fieldName = filterBy.substring(0, 1).toUpperCase() + filterBy.substring(1); // Ex: email -> Email
        Method getter = User.class.getMethod("get" + fieldName);

        switch (filterQuery.toLowerCase()) {
            case "co":
                methodFilter = stringClass.getMethod("contains", CharSequence.class);
                break;
            case "eq":
                methodFilter = stringClass.getMethod("equals", Object.class);
                break;
            case "sw":
                methodFilter = stringClass.getMethod("startsWith", String.class);
                break;
            case "ew":
                methodFilter = stringClass.getMethod("endsWith", String.class);
                break;
            default:
                methodFilter = stringClass.getMethod("contains", CharSequence.class);
                break;
        }
        return users.stream().filter(u -> {
                    try {
                        Object fieldValue = getter.invoke(u);
                        if (fieldValue == null) return false;
                        return (boolean) methodFilter.invoke(fieldValue, filterValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }).collect(Collectors.toList());
    }

}