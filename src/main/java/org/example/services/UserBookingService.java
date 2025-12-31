package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Ticket;
import org.example.entities.User;
import org.example.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {

    private User user;
    private List<User> userList;
    private ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static  final String USERS_PATH ="../localDb/users.json";
    public UserBookingService(User user1) throws IOException {
              this.user = user1;
        File users = new File(USERS_PATH);
        userList = OBJECT_MAPPER.readValue(users, new TypeReference<List<User>>() {});
    }


    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equalsIgnoreCase(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(),user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
           userList.add(user1);
           saveUserListToFile();
           return  Boolean.TRUE;
        }catch(IOException ex){
            return Boolean.FALSE;
        }
    }

    public void saveUserListToFile() throws  IOException{
        File usersFile = new File(USERS_PATH);
        OBJECT_MAPPER.writeValue(usersFile,userList);
    }

    public void fetchBooking(){
        user.printTicket();
    }

    public Boolean cancelBooking(String ticketId){
       Optional<Ticket> ticketOpt =  user.getTicketsBooked().stream().filter(ticket1 -> {
            return ticket1.getTicketId().equalsIgnoreCase(ticketId);
        }).findFirst();

      if(ticketOpt.isPresent()){
          user.getTicketsBooked().remove(ticketOpt.get());
          return true;
      }
        return false;
    }



}
