package org.example;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.services.UserBookingService;
import org.example.util.UserServiceUtil;

import java.util.*;
import javax.imageio.IIOException;
import java.io.IOException;
import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {
        System.out.println("running train booking system");
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;
        try{
            userBookingService = new UserBookingService();
        }catch(IOException ex){
            System.out.println("There is something wrong");
            return;
        }

        while (option != 7){
            System.out.println("choose option");
            System.out.println("1 . sign up");
            System.out.println("2.login");
            System.out.println("3. fetch booking");
            System.out.println("4. search trains");
            System.out.println("5. book seat");
            System.out.println("6. cancel my booking");
            System.out.println("7. exit the app");
            option = scanner.nextInt();
            Train trainSelectedForBooking = new Train();
            switch(option){
                case 1:
                    System.out.println("Enter the username to signup");
                    String nameToSignup = scanner.next();
                    System.out.println("enter the password to signup");
                    String passwordToSignup =scanner.next();
                        User userToSignup =  new User(
                            nameToSignup,passwordToSignup, UserServiceUtil.hashPassword(passwordToSignup),new ArrayList<>(),
                                UUID.randomUUID().toString()
                    );
                        userBookingService.signUp(userToSignup);
                        break;

                case 2:
                    System.out.println("enter username to login");
                    String nameToLogin = scanner.next();
                    System.out.println("enter the password to signin");
                    String passwordToLogin = scanner.next();
                    User userToLogin = new User(
                            nameToLogin , passwordToLogin ,UserServiceUtil.hashPassword(passwordToLogin),
                            new ArrayList<>() , UUID.randomUUID().toString()
                    );
                    try{
                        userBookingService = new UserBookingService(userToLogin);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 3:
                    System.out.println("fetching your bookings");
                    userBookingService.fetchBooking();
                    break;

                case 4:
                    System.out.println("enter source");
                    String source = scanner.next();
                    String destination = scanner.next();
                    try{
                       List<Train> trains = userBookingService.getTrains(source,destination);

                      int index =1;
                      for(Train t : trains){
                          System.out.println(index + " Train ID : " + t.getTrainId());
                          for(Map.Entry<String,String> entry: t.getStationTimes().entrySet()){
                              System.out.println("station "+entry.getKey()+" time: "+entry.getValue());
                          }
                          index++;
                      }
                        System.out.println("Select a train by typing 1,2,3...");
                        trainSelectedForBooking = trains.get(scanner.nextInt());
                    } catch(IOException e){
                        throw  new RuntimeException(e);
                    }
                    break;

                case 5:


            }
        }
    }
}